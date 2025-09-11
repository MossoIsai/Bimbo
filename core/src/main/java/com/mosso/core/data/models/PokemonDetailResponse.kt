package com.mosso.core.data.models

import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(
    val abilities: List<Abilities>,
    @SerializedName("base_experience") val baseExperience: Int,
    val name: String,
    val weight: Int,
    val height: Int,
    val sprites: Sprites
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

data class Sprites(
    @SerializedName("front_default") val photo: String
)