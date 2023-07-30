package com.example.pokemon.data.repository

import com.example.pokemon.data.remote.PokemonSpeciesResponse
import com.example.pokemon.data.remote.RemoteDataSource
import com.example.pokemon.data.remote.helper.BaseApiResponse
import com.example.pokemon.data.remote.helper.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class PokemonRepository @Inject constructor(
  private val remoteDataSource: RemoteDataSource
) : BaseApiResponse() {
  fun getPokemonSpecies(
    limit: Int,
    pageNumber: Int
  ): Flow<NetworkResult<PokemonSpeciesResponse>> = flow {
    emit(safeApiCall {
      remoteDataSource.getPokemonSpecies(limit, pageNumber)
    })
  }.flowOn(Dispatchers.IO)
}