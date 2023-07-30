package com.example.pokemon.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
  private val pokemonService: PokemonService
) {
  suspend fun getPokemonSpecies(
    limit: Int,
    pageNumber: Int
  ) = pokemonService.getPokemonSpecies(limit, pageNumber)
}