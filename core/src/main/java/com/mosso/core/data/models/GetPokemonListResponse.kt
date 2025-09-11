package com.mosso.core.data.models

import com.google.gson.annotations.SerializedName

data class GetPokemonListResponse(
    val count: Int,
    val next: String,
    val previous: String? = null,
    @SerializedName("results") val pokemonList: List<PokemonData>
)
