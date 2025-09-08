package com.mosso.bimbo.pokemon.domain

import com.mosso.bimbo.pokemon.data.GetPokemonListResponse
import com.mosso.bimbo.pokemon.data.Result
import kotlinx.coroutines.flow.Flow

interface GetPokemonListRepository {

    fun getPokemonList(): Flow<Result<GetPokemonListResponse>>
}