package com.bismillah.mymovies.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bismillah.mymovies.R
import com.bismillah.mymovies.data.source.local.MovieEntity
import com.bismillah.mymovies.databinding.ActivityDetailBinding
import com.bismillah.mymovies.viewmodel.ViewModelFactory
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

        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        binding.progressBar.visibility = View.VISIBLE
        binding.nestedScrollView.visibility = View.GONE
        binding.collapsingToolbar.visibility = View.GONE

        when (enumType) {
            DetailType.MOVIES -> {
                viewModel.selectedMovieId(id.toString())
                viewModel.getMovieDetail().observe(this, { movie ->
                    binding.progressBar.visibility = View.GONE
                    binding.nestedScrollView.visibility = View.VISIBLE
                    binding.collapsingToolbar.visibility = View.VISIBLE
                    populateMovieDetail(movie)
                })
            }
            DetailType.TV_SHOWS -> {
                viewModel.selectedTvShowId(id.toString())
                viewModel.getTvShowDetail().observe(this, { tvShow ->
                    binding.progressBar.visibility = View.GONE
                    binding.nestedScrollView.visibility = View.VISIBLE
                    binding.collapsingToolbar.visibility = View.VISIBLE
                    populateTvShowsDetail(tvShow)
                })
            }
        }

    }

    private fun populateMovieDetail(movie: MovieEntity) {
        with(binding) {
            titleDetail.text = movie.title
            releaseDetail.text = movie.releaseDate
            sinopisDetail.text = movie.overview
            popularityDetail.text = movie.popularity.toString()
            voteDetail.text = movie.voteAverage.toString()
            languageDetail.text = movie.originalLanguage

            Glide.with(this@DetailActivity)
                .load("https://image.tmdb.org/t/p/original/" + movie.posterPath)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error))
                .into(binding.posterDetail)
        }
    }

    private fun populateTvShowsDetail(tvShow: MovieEntity) {
        with(binding) {
            titleDetail.text = tvShow.title
            releaseDetail.text = tvShow.overview
            sinopisDetail.text = tvShow.originalLanguage
            popularityDetail.text = tvShow.popularity.toString()
            voteDetail.text = tvShow.voteAverage.toString()
            languageDetail.text = tvShow.releaseDate

            Glide.with(this@DetailActivity)
                .load("https://image.tmdb.org/t/p/original/" + tvShow.posterPath)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error))
                .into(binding.posterDetail)
        }
    }

    companion object {
        const val EXTRA_TYPE = "extraType"
        const val EXTRA_ID = "extraId"
    }
}