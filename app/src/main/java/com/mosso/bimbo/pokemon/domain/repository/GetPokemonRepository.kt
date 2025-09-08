package com.mosso.bimbo.pokemon.domain.repository

import com.mosso.bimbo.pokemon.data.models.GetPokemonListResponse
import com.mosso.bimbo.core.Result
import com.mosso.bimbo.pokemon.data.models.PokemonDetailResponse
import kotlinx.coroutines.flow.Flow

interface GetPokemonRepository {

    fun getPokemonList(): Flow<Result<GetPokemonListResponse>>

    fun getPokemonDetail(pokemonName: String): Flow<Result<PokemonDetailResponse>>
}