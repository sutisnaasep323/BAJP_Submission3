package com.bismillah.mymovies.ui.movies

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bismillah.mymovies.data.source.local.MovieEntity
import com.bismillah.mymovies.databinding.ItemListBinding
import com.bismillah.mymovies.ui.detail.DetailActivity
import com.bismillah.mymovies.ui.detail.DetailType
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    private var listMovies = ArrayList<MovieEntity>()

    fun setMovies(movie: List<MovieEntity>?) {
        if (movie == null) return
        this.listMovies.clear()
        this.listMovies.addAll(movie)
    }

    class MoviesViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            with(binding) {
                title.text = movie.title
                release.text = movie.releaseDate
                popularity.text = movie.popularity.toString()
                language.text = movie.originalLanguage
                overview.text = movie.overview
                vote.text = movie.voteAverage.toString()
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.EXTRA_TYPE, DetailType.MOVIES.ordinal)
                        putExtra(DetailActivity.EXTRA_ID, movie.id)
                    }
                    itemView.context.startActivity(intent)
                }
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/original/" + movie.posterPath)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = GONE
                            return false
                        }
                    })
                    .into(poster)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemBinding =
            ItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return MoviesViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = listMovies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = listMovies.size

}