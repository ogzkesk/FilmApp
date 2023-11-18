package com.ogzkesk.filmapp.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ogzkesk.filmapp.data.dto.Search
import com.ogzkesk.filmapp.databinding.ItemMovieGenericBinding
import com.ogzkesk.filmapp.util.capitalize
import com.ogzkesk.filmapp.util.showImage
import java.util.Locale

class SearchAdapter : ListAdapter<Search, SearchAdapter.SearchViewHolder>(DiffUtil()) {

    private var onClick: ((imdbId: String?) -> Unit)? = null

    inner class SearchViewHolder(private val binding: ItemMovieGenericBinding)
        : ViewHolder(binding.root) {

        fun bind(model: Search) {
            binding.apply {
                textViewTitle.text = model.title
                textViewMediaType.text = model.type?.capitalize()
                textViewReleaseDate.text = model.year
                imageViewGenericPoster.showImage(model.poster)
                root.setOnClickListener {
                    onClick?.invoke(model.imdbID)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemMovieGenericBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffUtil : ItemCallback<Search>() {
        override fun areItemsTheSame(oldItem: Search, newItem: Search): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Search, newItem: Search): Boolean {
            return oldItem == newItem
        }
    }

    fun setOnClickListener(onClick: (imdbId: String?) -> Unit){
        this.onClick = onClick
    }
}