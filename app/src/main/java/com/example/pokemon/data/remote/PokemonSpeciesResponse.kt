package com.example.pokemon.data.remote


import com.google.gson.annotations.SerializedName

data class PokemonSpeciesResponse(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: Any?,
    @SerializedName("results")
    val results: List<Result?>?
) {
    data class Result(
        @SerializedName("name")
        val name: String?,
        @SerializedName("url")
        val url: String?
    )
}