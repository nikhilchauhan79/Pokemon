package com.example.pokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.data.remote.PokemonSpeciesResponse
import com.example.pokemon.data.remote.helper.NetworkResult
import com.example.pokemon.databinding.ActivityMainBinding
import com.example.pokemon.presentation.adapters.PokemonAdapter
import com.example.pokemon.presentation.viewmodels.PokemonViewModel
import com.example.pokemon.utils.ExtensionUtils.hide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  private val pokemonViewModel: PokemonViewModel by viewModels()
  private lateinit var activityMainBinding: ActivityMainBinding
  private val pagingAdapter = PokemonAdapter()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(activityMainBinding.root)

    with(activityMainBinding) {
      rvPokemon.apply {
        adapter = pagingAdapter
        layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
      }
      lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
          launch {
            pokemonViewModel.pokemonSpeciesResult.collect { networkResult ->
              if (networkResult != null) {
                handlePokemonSpeciesNetworkResult(networkResult)
              }
            }
          }

          launch {
            pokemonViewModel.pokemonFlow.collect { data ->
              pagingAdapter.submitData(data)
            }
          }
        }
      }
    }
  }

  private fun handlePokemonSpeciesNetworkResult(networkResult: NetworkResult<PokemonSpeciesResponse>) {
    with(activityMainBinding) {
      when (networkResult) {
        is NetworkResult.Error -> {
          progressBar.hide()
        }

        is NetworkResult.Loading -> {
          progressBar.show()
        }

        is NetworkResult.Success -> {
          progressBar.hide()
        }
      }
    }
  }
}