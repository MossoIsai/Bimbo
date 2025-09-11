package com.mosso.pokemon.domain.usecase

import com.mosso.core.domain.BaseUseCase
import com.mosso.core.presentation.di.CoreModule.IoDispatcher
import com.mosso.pokemon.domain.models.PokemonDomain
import com.mosso.pokemon.domain.repository.GetPokemonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import com.mosso.core.presentation.Result

class GetPokemonListUseCase @Inject constructor(
    private val repository: GetPokemonRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseUseCase<Unit, Result<List<PokemonDomain>>>() {

    override fun execute(params: Unit): Flow<Result<List<PokemonDomain>>> =
        repository.getPokemonList().flowOn(dispatcher)
}