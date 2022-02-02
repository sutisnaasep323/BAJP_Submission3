package com.bismillah.mymovies.ui.adapter

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bismillah.mymovies.data.source.local.entity.MovieEntity
import com.bismillah.mymovies.databinding.ItemListBinding
import com.bismillah.mymovies.ui.activity.DetailActivity
import com.bismillah.mymovies.ui.detail.DetailType
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class FavoriteTvShowsAdapter :
    PagedListAdapter<MovieEntity, FavoriteTvShowsAdapter.TvShowsViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowsViewHolder {
        val itemListBinding =
            ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowsViewHolder(itemListBinding)
    }

    override fun onBindViewHolder(holder: TvShowsViewHolder, position: Int) {
        val tvShowEntity = getItem(position)
        if (tvShowEntity != null) {
            holder.bind(tvShowEntity)
        }
    }

    fun getSwipedData(swipedPosition: Int): MovieEntity? = getItem(swipedPosition)

    inner class TvShowsViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShows: MovieEntity) {
            with(binding) {
                title.text = tvShows.title
                release.text = tvShows.releaseDate
                language.text = tvShows.originalLanguage
                popularity.text = tvShows.popularity.toString()
                vote.text = tvShows.voteAverage.toString()
                overview.text = tvShows.overview

                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/original/" + tvShows.posterPath)
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

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.EXTRA_TYPE, DetailType.TV_SHOWS.ordinal)
                        putExtra(DetailActivity.EXTRA_ID, tvShows.id)
                    }
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}