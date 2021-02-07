package com.anandgaur.hcltask.ui.favorites

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.anandgaur.hcltask.R
import com.anandgaur.hcltask.data.local.entity.MoviesEntity
import com.anandgaur.hcltask.databinding.FragmentMoviesBinding
import com.anandgaur.hcltask.ui.movies.MoviesPageListAdapter
import com.anandgaur.hcltask.valueobject.Status
import com.anandgaur.hcltask.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar


class FavoritesFragment : Fragment() {

    companion object {
        const val TAG = "FavoritesFragment"
    }

    private var _fragmentMoviesBinding: FragmentMoviesBinding? = null
    private val binding get() = _fragmentMoviesBinding

    private lateinit var progressDialog: SweetAlertDialog
    private lateinit var viewModel: FavouritesViewModel
    private lateinit var favouritesAdapter: MoviesPageListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentMoviesBinding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemTouchHelper.attachToRecyclerView(binding?.rvMovies)

        // set loading dialog
        setProgressDialog()

        if (activity != null) {
            getDataMovies()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentMoviesBinding = null
    }

    private fun getDataMovies() {
        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory).get(FavouritesViewModel::class.java)

        favouritesAdapter = MoviesPageListAdapter()

        viewModel.getFavoritesMovies().observe(viewLifecycleOwner, Observer { movies ->
            Log.d(TAG, "FavoritesTvShowsFragment --> Cek: ${movies.status} - ${hashCode()}")

            when (movies.status) {
                Status.LOADING -> progressDialog.show()
                Status.SUCCESS -> {
                    progressDialog.dismissWithAnimation()
                    showMoviesList(movies.data as PagedList<MoviesEntity>, favouritesAdapter)
                }
                Status.ERROR -> {
                    progressDialog.dismissWithAnimation()
                    showErrorDialog()
                }
            }
        })

        with(binding?.rvMovies) {
            this?.setHasFixedSize(true)
            this?.layoutManager = LinearLayoutManager(context)
            this?.adapter = favouritesAdapter
        }
    }

    private fun showMoviesList(
        listMovies: PagedList<MoviesEntity>,
        favouritesAdapter: MoviesPageListAdapter
    ) {
        if (listMovies.size > 0) {
            binding?.rvMovies?.visibility = View.VISIBLE
            binding?.emptyView?.layoutEmpty?.visibility = View.GONE
            favouritesAdapter.submitList(listMovies)
            favouritesAdapter.notifyDataSetChanged()
        } else {
            binding?.rvMovies?.visibility = View.GONE
            binding?.emptyView?.layoutEmpty?.visibility = View.VISIBLE
            binding?.emptyView?.imgEmpty?.setImageResource(R.drawable.ic_empty_data)
        }
    }

    private fun setProgressDialog() {
        progressDialog = SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE)
            .setTitleText(getString(R.string.loading_alert))
    }

    private fun showErrorDialog() {
        val errorDialog = SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
            .setTitleText(getString(R.string.msg_error_title))
            .setContentText(getString(R.string.msg_error_text))
        errorDialog.show()
    }

    // function for item swipe
    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition

                val moviesEntity = favouritesAdapter.getSwipedData(swipedPosition)

                moviesEntity?.let { viewModel.setFavouritesMovies(it) }

                val snackbar =
                    Snackbar.make(
                        view as View,
                        getString(R.string.message_undo),
                        Snackbar.LENGTH_LONG
                    )

                snackbar.setAction(getString(R.string.message_ok)) {
                    moviesEntity?.let { viewModel.setFavouritesMovies(it) }
                }

                snackbar.show()
            }
        }
    })

}