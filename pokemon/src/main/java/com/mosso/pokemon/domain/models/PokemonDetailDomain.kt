package com.mosso.pokemon.domain.models

data class PokemonDetailDomain(
    val abilities: List<AbilitiesDomain>,
    val name: String,
    val urlImg: String,
    val weight: Double,
    val height: Double
)

data class AbilitiesDomain(
    val name: String,
    val url: String
)