package com.example.pokemon.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.pokemon.data.remote.PokemonSpeciesResponse

object PokemonDiffUtilCallback : DiffUtil.ItemCallback<PokemonSpeciesResponse.Result>() {
  override fun areItemsTheSame(
    oldItem: PokemonSpeciesResponse.Result,
    newItem: PokemonSpeciesResponse.Result
  ): Boolean = oldItem.name == newItem.name

  override fun areContentsTheSame(
    oldItem: PokemonSpeciesResponse.Result,
    newItem: PokemonSpeciesResponse.Result
  ): Boolean = oldItem == newItem

}