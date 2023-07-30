package com.example.pokemon.presentation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokemon.data.remote.PokemonSpeciesResponse
import com.example.pokemon.data.remote.RemoteDataSource
import com.example.pokemon.data.remote.helper.NetworkResult
import com.example.pokemon.data.repository.PokemonRepository
import kotlinx.coroutines.flow.first

class PokemonPagingSource(
  private val pokemonRepository: PokemonRepository
) : PagingSource<Int, PokemonSpeciesResponse.Result>() {
  override fun getRefreshKey(state: PagingState<Int, PokemonSpeciesResponse.Result>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      val anchorPage = state.closestPageToPosition(anchorPosition)
      anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonSpeciesResponse.Result> {
    return try {
      val nextPageNumber = params.key?.plus(1) ?: 1
      val response =
        pokemonRepository.getPokemonSpecies(limit = 20, pageNumber = nextPageNumber).first()
      LoadResult.Page(
        response.data?.results?.filterNotNull() ?: emptyList(),
        prevKey = null, nextKey = nextPageNumber
      )
    } catch (exception: Exception) {
      LoadResult.Error(exception)
    }
  }
}