package com.mosso.bimbo.pokemon.data.models

import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(
    val abilities: List<Abilities>,
    @SerializedName("base_experience") val baseExperience: Int,
    val forms: List<Forms>,
)

data class Abilities(
    val ability: Ability,
    @SerializedName("is_hidden") val isHidden: Boolean,
    val slot: Int,
)

data class Ability(
    val name: String,
    val url: String
)

data class Forms(
    val name: String,
    val url: String
)