package com.example.pokemon.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonService {
  @GET("pokemon-species")
  suspend fun getPokemonSpecies(
    @Query("limit") limit: Int,
    @Query("offset") offset: Int,
  ): Response<PokemonSpeciesResponse>
}