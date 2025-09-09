package com.mosso.bimbo.pokemon.domain.repository

import com.mosso.bimbo.pokemon.data.models.GetPokemonListResponse
import com.mosso.bimbo.core.presentation.Result
import com.mosso.bimbo.pokemon.data.models.PokemonDetailResponse
import com.mosso.bimbo.pokemon.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface GetPokemonRepository {

    fun getPokemonList(): Flow<Result<List<Pokemon>>>

    fun getPokemonDetail(pokemonName: String): Flow<Result<PokemonDetailResponse>>
}