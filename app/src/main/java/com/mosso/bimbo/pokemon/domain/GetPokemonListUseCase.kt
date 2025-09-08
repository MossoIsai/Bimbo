package com.mosso.bimbo.pokemon.domain

import com.mosso.bimbo.core.BaseUseCase
import com.mosso.bimbo.core.CoreModule.IoDispatcher
import com.mosso.bimbo.pokemon.data.GetPokemonListResponse
import com.mosso.bimbo.pokemon.data.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val repository: GetPokemonListRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseUseCase<Unit, Result<GetPokemonListResponse>>() {

    override fun execute(params: Unit): Flow<Result<GetPokemonListResponse>> = flow {
        repository.getPokemonList().collect { state ->
            when (state) {
                is Result.Error -> emit(Result.Error(throwable = state.throwable))
                is Result.Success -> emit(Result.Success(state.body))
            }
        }
    }.flowOn(dispatcher)
}