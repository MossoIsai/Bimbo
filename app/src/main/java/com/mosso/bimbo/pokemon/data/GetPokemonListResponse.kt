package com.mosso.bimbo.pokemon.data

import com.google.gson.annotations.SerializedName
import com.mosso.bimbo.pokemon.domain.Pokemon

data class GetPokemonListResponse(
    val count: Int,
    val next: String,
    val previous: Any? = null,
    @SerializedName("result") val pokemonList: List<Pokemon>
)
