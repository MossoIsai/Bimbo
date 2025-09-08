package com.mosso.bimbo.pokemon.data.models

import com.google.gson.annotations.SerializedName
import com.mosso.bimbo.pokemon.domain.model.Pokemon

data class GetPokemonListResponse(
    val count: Int,
    val next: String,
    val previous: String? = null,
    @SerializedName("results") val pokemonList: List<Pokemon>
)
