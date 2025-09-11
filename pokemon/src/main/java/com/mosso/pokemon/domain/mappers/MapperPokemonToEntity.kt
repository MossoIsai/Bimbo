package com.mosso.pokemon.domain.mappers

import com.mosso.core.data.entities.PokemonEntity
import com.mosso.core.data.models.Abilities
import com.mosso.core.data.models.Ability
import com.mosso.core.data.models.GetPokemonListResponse
import com.mosso.core.data.models.PokemonData
import com.mosso.core.data.models.PokemonDetailResponse
import com.mosso.pokemon.domain.models.AbilitiesDomain
import com.mosso.pokemon.domain.models.PokemonDetailDomain
import com.mosso.pokemon.domain.models.PokemonDomain


fun GetPokemonListResponse.toPokemonEntity(): List<PokemonEntity> =
    pokemonList.flatMap { itemPokemon ->
        arrayListOf(PokemonEntity(name = itemPokemon.name, url = itemPokemon.url))
    }

fun PokemonDomain.toPokemonEntity(): PokemonEntity =
    PokemonEntity(name = name, url = url)

fun PokemonEntity.toPokemon(): PokemonDomain =
    PokemonDomain(name = name, url = url)

fun PokemonData.toPokemonDomain(): PokemonDomain = PokemonDomain(name = name, url = url)

fun List<PokemonEntity>.toDomain(): List<PokemonDomain> = flatMap { entity ->
    arrayListOf(entity.toPokemon())
}

fun List<PokemonData>.totoDomain(): List<PokemonDomain> = flatMap { entity ->
    arrayListOf(entity.toPokemonDomain())
}

fun List<Abilities>.toAbilitiesDomain(): List<AbilitiesDomain> = flatMap { abilities ->
    arrayListOf(abilities.ability.toDomain())
}

fun Ability.toDomain(): AbilitiesDomain = AbilitiesDomain(
    name = name, url = url
)

fun PokemonDetailResponse.toDomain(): PokemonDetailDomain =
    PokemonDetailDomain(
        abilities = abilities.toAbilitiesDomain(),
        name = name,
        urlImg = sprites.photo,
        weight = weight.toDouble(),
        height = height.toDouble()

    )