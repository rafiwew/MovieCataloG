package com.piwew.movieapp_cleanarchitecture.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.piwew.movieapp_cleanarchitecture.core.BuildConfig
import com.piwew.movieapp_cleanarchitecture.core.databinding.ItemListMovieBinding
import com.piwew.movieapp_cleanarchitecture.core.domain.model.Movie
import com.piwew.movieapp_cleanarchitecture.core.utils.loadImage

class MovieAdapter : ListAdapter<Movie, MovieAdapter.ListViewHolder>(MovieDiffCallback()) {

    var onItemClick: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = ItemListMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class ListViewHolder(private val binding: ItemListMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Movie) {
            with(binding) {
                ivItemBackdrop.loadImage(BuildConfig.BASE_URL_IMAGE + data.backdropPath)
                tvItemTitle.text = data.title
                tvItemLanguage.text = data.originalLanguage
                tvItemReleaseDate.text = data.releaseDate
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(getItem(adapterPosition))
            }
        }
    }
}

class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.movieId == newItem.movieId
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}