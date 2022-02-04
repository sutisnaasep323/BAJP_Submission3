package com.bismillah.mymovies.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bismillah.mymovies.R
import com.bismillah.mymovies.data.source.local.entity.MovieEntity
import com.bismillah.mymovies.databinding.ActivityDetailBinding
import com.bismillah.mymovies.ui.detail.DetailType
import com.bismillah.mymovies.ui.viewmodel.DetailViewModel
import com.bismillah.mymovies.viewmodel.ViewModelFactory
import com.bismillah.mymovies.vo.Resource
import com.bismillah.mymovies.vo.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener { onBackPressed() }

        window.statusBarColor = ContextCompat.getColor(this, R.color.purple_700)
        val type = intent.getIntExtra(EXTRA_TYPE, -1)
        val enumType: DetailType = DetailType.values()[type]
        val id = intent.getIntExtra(EXTRA_ID, -1)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        binding.progressBar.visibility = View.VISIBLE
        binding.nestedScrollView.visibility = View.GONE
        binding.collapsingToolbar.visibility = View.GONE

        binding.backButton.setOnClickListener { onBackPressed() }

        when (enumType) {
            DetailType.MOVIES -> {
                viewModel.selectedMovieId(id)
                viewModel.movieDetail.observe(this, { movie ->
                    if (movie != null) {
                        showDetail(movie)
                    }
                })
            }
            DetailType.TV_SHOWS -> {
                viewModel.selectedTvShowId(id)
                viewModel.tvShowDetail.observe(this, { tvShow ->
                    if (tvShow != null) {
                        showDetail(tvShow)
                    }
                })
            }
        }

        binding.favoriteButton.setOnClickListener {
            when (enumType) {
                DetailType.MOVIES -> {
                    viewModel.setFavoriteMovie()
                }
                DetailType.TV_SHOWS -> {
                    viewModel.setFavoriteTvShow()
                }
            }
        }

    }

    private fun populateDetail(movie: MovieEntity) {
        with(binding) {
            titleDetail.text = movie.title
            releaseDetail.text = movie.releaseDate
            sinopisDetail.text = movie.overview
            popularityDetail.text = movie.popularity.toString()
            voteDetail.text = movie.voteAverage.toString()
            languageDetail.text = movie.originalLanguage

            Glide.with(this@DetailActivity)
                .load("https://image.tmdb.org/t/p/original/" + movie.posterPath)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(binding.posterDetail)
        }
    }

    private fun showDetail(movie: Resource<MovieEntity>) {
        when (movie.status) {
            Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
            Status.SUCCESS -> if (movie.data != null) {
                binding.progressBar.visibility = View.GONE
                binding.nestedScrollView.visibility = View.VISIBLE
                binding.collapsingToolbar.visibility = View.VISIBLE

                val state = movie.data.favorite

                setFavoriteState(state)
                populateDetail(movie.data)
            }
            Status.ERROR -> {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(
                    this,
                    "Terjadi kesalahan",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setFavoriteState(state: Boolean) {
        if (state) {
            binding.favoriteButton.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite
                )
            )
        } else {
            binding.favoriteButton.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite_border
                )
            )
        }
    }

    companion object {
        const val EXTRA_TYPE = "extraType"
        const val EXTRA_ID = "extraId"
    }
}