package com.anandgaur.hcltask.ui.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.anandgaur.hcltask.R
import com.anandgaur.hcltask.data.local.entity.GenresEntity
import com.anandgaur.hcltask.data.local.entity.MoviesEntity
import com.anandgaur.hcltask.databinding.ActivityDetailMoviesBinding
import com.anandgaur.hcltask.databinding.ContentDetailMoviesBinding
import com.anandgaur.hcltask.utils.FormatedMethod
import com.anandgaur.hcltask.utils.POSTER_URL
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.anandgaur.hcltask.valueobject.Status
import com.anandgaur.hcltask.viewmodel.ViewModelFactory

class DetailMoviesActivity : AppCompatActivity() {

    companion object {
        const val TAG = "DetailMoviesActivity"
        const val EXTRA_MOVIES = "extra_movies"
        const val EXTRA_STATUS = "extra_status"
        const val EXTRA_TITLE_NAV = "movies"
    }

    private lateinit var contentDetailMoviesBinding: ContentDetailMoviesBinding
    private lateinit var activityDetailMoviesBinding: ActivityDetailMoviesBinding
    private lateinit var viewModel: DetailMoviesViewModel

    private lateinit var genresAdapterPage: GenresAdapterPage


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailMoviesBinding = ActivityDetailMoviesBinding.inflate(layoutInflater)
        contentDetailMoviesBinding = activityDetailMoviesBinding.moviesContent
        setContentView(activityDetailMoviesBinding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(
            DetailMoviesViewModel::class.java
        )

        //List adapter genre
        genresAdapterPage = GenresAdapterPage()

        //get inten extras
        val extras = intent.extras
        if (extras != null) {
            val movieId = extras.getInt(EXTRA_MOVIES)
            val movieStatus = extras.getString(EXTRA_STATUS)

            Log.d(TAG, "Movies id is: $movieId")

            viewModel.setSelectedMovies(movieId)

            if (movieStatus == EXTRA_TITLE_NAV) {
                showDetailMovies()
            }

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    // to set progress bar visible and content hide
    private fun progressAndContentNotVisible() {
        activityDetailMoviesBinding.progressBar.visibility = View.VISIBLE
        contentDetailMoviesBinding.clContentMovies.visibility = View.GONE
    }

    // to set progress bar hide and content visible
    private fun progressAndContentVisible() {
        activityDetailMoviesBinding.progressBar.visibility = View.GONE
        contentDetailMoviesBinding.clContentMovies.visibility = View.VISIBLE
    }

    private fun showDetailMovies() {
        supportActionBar?.title = getString(R.string.title_detail_movies)
        // load detai movies
        viewModel.moviesDetail.observe(
            this@DetailMoviesActivity,
            Observer { moviesWithGenres ->
                Log.d(TAG, "cek mMovies: " + moviesWithGenres.data?.mMovies)
                Log.d(TAG, "cek mGenres: " + moviesWithGenres.data?.mGenres)
                Log.d(TAG, "cek status: " + moviesWithGenres.status + hashCode())

                if (moviesWithGenres != null) {
                    when (moviesWithGenres.status) {
                        Status.LOADING -> progressAndContentNotVisible()
                        Status.SUCCESS -> {
                            if (moviesWithGenres.data != null) {
                                progressAndContentVisible()

                                // set detail
                                populateMoviesDetail(moviesWithGenres.data.mMovies)

                                // set genre movies
                                getGenresMovies(moviesWithGenres.data.mGenres)
                            }
                        }
                        Status.ERROR -> {
                            progressAndContentVisible()
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.msg_error_status),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

            })
    }

    // get genres movies
    private fun getGenresMovies(mGenres: List<GenresEntity>) {
        if (mGenres.isNotEmpty()) {
            genresAdapterPage.setGenres(mGenres)
            genresAdapterPage.notifyDataSetChanged()
        }

        with(contentDetailMoviesBinding.rvGenre) {
            layoutManager =
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            setHasFixedSize(true)
            adapter = genresAdapterPage
        }
    }

    // get director movies
    private fun getDirectorMovies() {
        with(contentDetailMoviesBinding) {
            viewModel.moviesDirector.observe(
                this@DetailMoviesActivity,
                Observer { directorMovies ->
                    Log.d(TAG, "cek director " + directorMovies.status + hashCode())
                    Log.d(TAG, "cek data director: " + directorMovies.data?.mDirector)

                    if (directorMovies != null) {
                        when (directorMovies.status) {
                            Status.LOADING -> tvDirector.text = getString(R.string.loading_text)
                            Status.SUCCESS -> {
                                if (directorMovies.data?.mDirector.isNullOrEmpty()) {
                                    tvDirector.text = "-"
                                } else {
                                    tvDirector.text =
                                        directorMovies.data?.mDirector?.get(0)?.name
                                }
                            }
                            Status.ERROR -> {
                                tvDirector.text = "-"
                                Toast.makeText(
                                    applicationContext,
                                    getString(R.string.msg_error_status),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                })
        }

    }


    private fun populateMoviesDetail(moviesEntity: MoviesEntity) {
        moviesEntity.apply {
            with(contentDetailMoviesBinding) {
                val yearRelease =
                    if (releaseDate?.isEmpty()!!) "-" else releaseDate?.let {
                        FormatedMethod.getYearRelease(
                            it
                        )
                    }

                tvTitle.text = StringBuilder("$title ($yearRelease)")
                tvDescription.text = overview
                tvStatus.text = status
                tvPopularity.text = popularity?.let { FormatedMethod.roundOffDecimal(it) }
                tvVoteAverage.text = voteAverage?.let { FormatedMethod.roundOffDecimal(it) }
                tvVoteCount.text = voteCount.toString()
                tvLanguage.text = language
                tvDaterelease.text = if (releaseDate?.isEmpty()!!) "-" else releaseDate?.let {
                    FormatedMethod.getDateFormat(it)
                }

                if (tagline != null) {
                    if (tagline?.isNotEmpty()!!) {
                        tvTagline.text = tagline
                    } else {
                        tvTagline.isVisible = false
                    }
                }

                //set director
                getDirectorMovies()

                // set image poster
                val imagePath = StringBuilder("${POSTER_URL}${posterPath}").toString()
                Glide.with(this@DetailMoviesActivity)
                    .load(imagePath)
                    .transform(RoundedCorners(20))
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgPoster)

                // set visibility textview
                tvFirstairDate.isVisible = false
                tvFirstairdateTitle.isVisible = false
                tvLastairDate.isVisible = false
                tvLastairdateTitle.isVisible = false
                tvCreator.isVisible = false
                tvCreatorTitle.isVisible = false
                tvType.isVisible = false
                tvTypeTitle.isVisible = false


                // btn favourites
                favouriteBtn(moviesEntity)
            }
        }
    }


    private fun favouriteBtn(moviesEntity: MoviesEntity) {
        var statusFavorite = moviesEntity.isFavorite
        setStatusFavorites(statusFavorite)
        contentDetailMoviesBinding.imgBtnfavourite.setOnClickListener {
            statusFavorite = !statusFavorite
            viewModel.setFavoriteMovie(moviesEntity, statusFavorite)

            if (statusFavorite) {
                showSnackbarMessage(getString(R.string.text_add_favorite))
            } else {
                showSnackbarMessage(getString(R.string.text_delete_favorite))
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setStatusFavorites(statusFavorites: Boolean) {
        if (statusFavorites) {
            contentDetailMoviesBinding.imgBtnfavourite.setImageDrawable(
                resources.getDrawable(
                    R.drawable.ic_nav_favourites,
                    theme
                )
            )

        } else {
            contentDetailMoviesBinding.imgBtnfavourite.setImageDrawable(
                resources.getDrawable(
                    R.drawable.ic_favourites_full,
                    theme
                )
            )
        }
    }

    // show snack bar message
    private fun showSnackbarMessage(message: String) {
        Snackbar.make(contentDetailMoviesBinding.clContentMovies, message, Snackbar.LENGTH_SHORT)
            .show()
    }

}