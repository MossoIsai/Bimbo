package com.mosso.pokemon.domain.repository

import com.mosso.pokemon.domain.models.PokemonDetailDomain
import com.mosso.pokemon.domain.models.PokemonDomain
import kotlinx.coroutines.flow.Flow
import com.mosso.core.presentation.Result

interface GetPokemonRepository {

    fun getPokemonList(): Flow<Result<List<PokemonDomain>>>

    fun getPokemonDetail(pokemonName: String): Flow<Result<PokemonDetailDomain>>
}