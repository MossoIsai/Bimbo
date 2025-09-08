package com.mosso.bimbo.pokemon.domain.usecase

import com.mosso.bimbo.core.BaseUseCase
import com.mosso.bimbo.core.CoreModule.IoDispatcher
import com.mosso.bimbo.pokemon.data.models.GetPokemonListResponse
import com.mosso.bimbo.core.Result
import com.mosso.bimbo.pokemon.domain.repository.GetPokemonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val repository: GetPokemonRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseUseCase<Unit, Result<GetPokemonListResponse>>() {

    override fun execute(params: Unit): Flow<Result<GetPokemonListResponse>> =
        repository.getPokemonList().flowOn(dispatcher)
}