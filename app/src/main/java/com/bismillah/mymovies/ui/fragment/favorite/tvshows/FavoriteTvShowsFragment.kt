package com.bismillah.mymovies.ui.fragment.favorite.tvshows

import android.os.Bundle
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
import com.bismillah.mymovies.R
import com.bismillah.mymovies.data.source.local.entity.MovieEntity
import com.bismillah.mymovies.databinding.FragmentFavoriteTvShowsBinding
import com.bismillah.mymovies.ui.adapter.FavoriteMoviesAdapter
import com.bismillah.mymovies.ui.viewmodel.FavoriteViewModel
import com.bismillah.mymovies.utils.SortUtils
import com.bismillah.mymovies.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class FavoriteTvShowsFragment : Fragment() {

    private var _fragmentFavoriteTvShowsBinding: FragmentFavoriteTvShowsBinding? = null
    private val binding get() = _fragmentFavoriteTvShowsBinding!!

    private lateinit var tvShowsAdapter: FavoriteMoviesAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentFavoriteTvShowsBinding =
            FragmentFavoriteTvShowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemTouchHelper.attachToRecyclerView(binding.rvTvShowsFavorite)

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        tvShowsAdapter = FavoriteMoviesAdapter()

        binding.progressBar.visibility = View.VISIBLE
        binding.notFound.visibility = View.GONE
        setList(SortUtils.RANDOM)

        with(binding.rvTvShowsFavorite) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = tvShowsAdapter
        }

        binding.random.setOnClickListener { setList(SortUtils.RANDOM) }
        binding.newest.setOnClickListener { setList(SortUtils.NEWEST) }
        binding.popularity.setOnClickListener { setList(SortUtils.POPULARITY) }
        binding.vote.setOnClickListener { setList(SortUtils.VOTE) }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val tvShowEntity = tvShowsAdapter.getSwipedData(swipedPosition)
                tvShowEntity?.let { viewModel.setFavorite(it) }
                val snackbar =
                    Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.message_ok) { _ ->
                    tvShowEntity?.let { viewModel.setFavorite(it) }
                }
                snackbar.show()
            }
        }
    })

    private fun setList(sort: String) {
        viewModel.getFavoriteTvShows(sort).observe(viewLifecycleOwner, tvShowsObserver)
    }

    private val tvShowsObserver = Observer<PagedList<MovieEntity>> { tvShows ->
        if (tvShows.isNotEmpty()) {
            binding.progressBar.visibility = View.GONE
            binding.notFound.visibility = View.GONE
            tvShowsAdapter.submitList(tvShows)
            tvShowsAdapter.notifyDataSetChanged()
        } else {
            binding.progressBar.visibility = View.GONE
            binding.notFound.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentFavoriteTvShowsBinding = null
    }
}