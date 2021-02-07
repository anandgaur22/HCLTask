package com.anandgaur.hcltask.ui.movies

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.anandgaur.hcltask.R
import com.anandgaur.hcltask.databinding.FragmentMoviesBinding
import com.anandgaur.hcltask.utils.SortUtils
import com.anandgaur.hcltask.valueobject.Status
import com.anandgaur.hcltask.viewmodel.ViewModelFactory

class MoviesFragment : Fragment() {

    companion object{
        const val TAG = "MoviesFragment"
    }

    private var _fragmentMoviesBinding: FragmentMoviesBinding? = null
    private val binding get() = _fragmentMoviesBinding

    private lateinit var viewModel: MoviesViewModel
    private lateinit var moviesAdapter: MoviesPageListAdapter
    private lateinit var progressDialog: SweetAlertDialog



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentMoviesBinding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // show menu search
        setHasOptionsMenu(true)

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

    private fun getDataMovies(){
        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory).get(MoviesViewModel::class.java)

        moviesAdapter = MoviesPageListAdapter()

        viewModel.getAllMoviesSortBy(SortUtils.DEFAULT).observe(viewLifecycleOwner, Observer { movies ->
            Log.d(TAG, "Cek: ${movies.status} - ${hashCode()}")

            if (movies != null) {
                when (movies.status) {
                    Status.LOADING -> {
                        progressDialog.show()
                    }
                    Status.SUCCESS -> {
                        progressDialog.dismissWithAnimation()
                        moviesAdapter.submitList(movies.data)
                        moviesAdapter.notifyDataSetChanged()
                    }
                    Status.ERROR -> {
                        progressDialog.dismissWithAnimation()
                        showErrorDialog()
                    }
                }
            }
        })

        with(binding?.rvMovies){
            this?.layoutManager = LinearLayoutManager(context)
            this?.setHasFixedSize(true)
            this?.adapter = moviesAdapter
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort = ""
        when (item.getItemId()) {
            R.id.action_default -> sort = SortUtils.DEFAULT
            R.id.action_popularity -> sort = SortUtils.POPULAR
        }
        viewModel.getAllMoviesSortBy(sort).observe(viewLifecycleOwner, Observer { movies ->
            Log.d(TAG,"tes "+movies.status+hashCode())
            if (movies != null) {
                when (movies.status) {
                    Status.LOADING -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding?.progressBar?.visibility = View.GONE
                        moviesAdapter.submitList(movies.data)
                        moviesAdapter.notifyDataSetChanged()
                    }
                    Status.ERROR -> {
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(context, getString(R.string.msg_error_text), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        item.setChecked(true)
        return super.onOptionsItemSelected(item)
    }


}