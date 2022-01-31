package com.bismillah.mymovies.ui.tvshows

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
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

class TvShowsAdapter : RecyclerView.Adapter<TvShowsAdapter.TvShowsViewHolder>() {

    private var listMovies = ArrayList<MovieEntity>()

    fun setTvShows(tvShow: List<MovieEntity>?) {
        if (tvShow == null) return
        this.listMovies.clear()
        this.listMovies.addAll(tvShow)
    }

    class TvShowsViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: MovieEntity) {
            with(binding) {
                title.text = tvShow.title
                release.text = tvShow.overview
                popularity.text = tvShow.popularity.toString()
                language.text = tvShow.releaseDate
                overview.text = tvShow.originalLanguage
                vote.text = tvShow.voteAverage.toString()
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_TYPE, DetailType.TV_SHOWS.ordinal)
                    intent.putExtra(DetailActivity.EXTRA_ID, tvShow.id)
                    itemView.context.startActivity(intent)
                }
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/original/" + tvShow.posterPath)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE
                            return false
                        }
                    })
                    .into(poster)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowsViewHolder {
        val itemBinding =
            ItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return TvShowsViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: TvShowsViewHolder, position: Int) {
        val tvShow = listMovies[position]
        holder.bind(tvShow)
    }

    override fun getItemCount(): Int = listMovies.size

}