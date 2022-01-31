package com.bismillah.mymovies.ui.tvshows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bismillah.mymovies.R
import com.bismillah.mymovies.databinding.FragmentTvShowsBinding
import com.bismillah.mymovies.viewmodel.ViewModelFactory

class TvShowsFragment : Fragment() {

    private var _fragmentTvShowsBinding: FragmentTvShowsBinding? = null
    private val binding get() = _fragmentTvShowsBinding!!

    private lateinit var viewModel: TvShowsViewModel
    private lateinit var tvShowsAdapter: TvShowsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentTvShowsBinding = FragmentTvShowsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance ()
            viewModel = ViewModelProvider(this, factory)[TvShowsViewModel::class.java]

            tvShowsAdapter = TvShowsAdapter()

            binding.progressBar.visibility = View.VISIBLE
            viewModel.getTvShows().observe(viewLifecycleOwner, { movies ->
                binding.progressBar.visibility = View.GONE
                tvShowsAdapter.setTvShows(movies)
                tvShowsAdapter.notifyDataSetChanged()
            })

            with(binding.rvTvShows) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvShowsAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentTvShowsBinding = null
    }
}