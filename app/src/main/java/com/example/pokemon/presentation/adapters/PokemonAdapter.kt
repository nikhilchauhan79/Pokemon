package com.example.pokemon.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.data.remote.PokemonSpeciesResponse
import com.example.pokemon.databinding.PokemonItemBinding

class PokemonAdapter(
) :
  PagingDataAdapter<PokemonSpeciesResponse.Result, PokemonAdapter.PokemonViewHolder>(
    PokemonDiffUtilCallback
  ) {
  private lateinit var binding: PokemonItemBinding

  inner class PokemonViewHolder(binding: PokemonItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: PokemonSpeciesResponse.Result) {
      with(binding) {
        pokemonName.text = item.name
        pokemonUrl.text = item.url
      }
    }
  }

  override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
    getItem(position)?.let { holder.bind(it) }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
    binding = PokemonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return PokemonViewHolder(binding)
  }

}