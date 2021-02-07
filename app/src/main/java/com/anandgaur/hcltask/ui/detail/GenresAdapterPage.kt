package com.anandgaur.hcltask.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anandgaur.hcltask.data.local.entity.GenresEntity
import com.anandgaur.hcltask.databinding.ItemsGenresBinding
import kotlin.collections.ArrayList

class GenresAdapterPage : RecyclerView.Adapter<GenresAdapterPage.GenresViewHolder>() {

    private var listGenres = ArrayList<GenresEntity>()

    fun setGenres(genre: List<GenresEntity>) {
        if (genre.isNullOrEmpty()) return
        this.listGenres.clear()
        this.listGenres.addAll(genre)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val itemsGenresBinding =
            ItemsGenresBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenresViewHolder(itemsGenresBinding)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        val movies = listGenres[position]
        holder.bind(movies)
    }

    override fun getItemCount(): Int = listGenres.size

    inner class GenresViewHolder(private val binding: ItemsGenresBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: GenresEntity) {
            with(binding) {
                tvItemTitle.text = genre.name

            }
        }
    }


}