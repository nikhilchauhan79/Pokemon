package com.example.pokemon.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.pokemon.data.remote.PokemonSpeciesResponse
import com.example.pokemon.data.remote.helper.NetworkResult
import com.example.pokemon.data.repository.PokemonRepository
import com.example.pokemon.presentation.paging.PokemonPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
  private val pokemonRepository: PokemonRepository
) : ViewModel() {
  private val _pokemonSpeciesResult: MutableStateFlow<NetworkResult<PokemonSpeciesResponse>?> =
    MutableStateFlow(null)
  val pokemonSpeciesResult: StateFlow<NetworkResult<PokemonSpeciesResponse>?> =
    _pokemonSpeciesResult.asStateFlow()
  val pokemonFlow = Pager(
    PagingConfig(pageSize = 20, prefetchDistance = 1)
  ) {
    PokemonPagingSource(pokemonRepository)
  }.flow.cachedIn(viewModelScope)

  fun getPokemonSpecies(
    limit: Int,
    pageNumber: Int
  ) {
    viewModelScope.launch {
      pokemonRepository.getPokemonSpecies(limit, pageNumber).collect { result ->
        _pokemonSpeciesResult.emit(result)
      }
    }
  }
}