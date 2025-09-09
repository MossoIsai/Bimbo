package com.mosso.bimbo.pokemon.domain.model

import com.mosso.bimbo.core.data.PokemonEntity
import com.mosso.bimbo.pokemon.data.models.GetPokemonListResponse


fun GetPokemonListResponse.toPokemonEntity(): List<PokemonEntity> =
    pokemonList.flatMap { itemPokemon ->
        arrayListOf(itemPokemon.toPokemonEntity())
    }

fun Pokemon.toPokemonEntity(): PokemonEntity =
    PokemonEntity(name = name, url = url)

fun PokemonEntity.toPokemon(): Pokemon =
    Pokemon(name = name, url = url)

fun List<PokemonEntity>.toDomain(): List<Pokemon> = flatMap { entity ->
    arrayListOf(entity.toPokemon())
}