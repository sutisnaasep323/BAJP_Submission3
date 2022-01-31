package com.bismillah.mymovies.ui.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bismillah.mymovies.R
import com.bismillah.mymovies.databinding.FragmentMoviesBinding
import com.bismillah.mymovies.viewmodel.ViewModelFactory


class MoviesFragment : Fragment() {

    private var _fragmentMoviesBinding: FragmentMoviesBinding? = null
    private val binding get() = _fragmentMoviesBinding!!

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var viewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentMoviesBinding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            val factory = ViewModelFactory.getInstance()
            viewModel = ViewModelProvider(this, factory)[MoviesViewModel::class.java]

            moviesAdapter = MoviesAdapter()

            binding.progressBar.visibility = View.VISIBLE

            viewModel.getMovies().observe(viewLifecycleOwner, { movies ->
                binding.progressBar.visibility = View.GONE
                moviesAdapter.setMovies(movies)
                moviesAdapter.notifyDataSetChanged()
            })

            with(binding.rvMovies) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = moviesAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentMoviesBinding = null
    }

}