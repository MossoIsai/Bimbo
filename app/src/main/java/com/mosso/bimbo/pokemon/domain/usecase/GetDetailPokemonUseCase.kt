package com.mosso.bimbo.pokemon.domain.usecase

import com.mosso.bimbo.core.domain.BaseUseCase
import com.mosso.bimbo.core.presentation.di.CoreModule.IoDispatcher
import com.mosso.bimbo.core.presentation.Result
import com.mosso.bimbo.pokemon.data.models.PokemonDetailResponse
import com.mosso.bimbo.pokemon.domain.repository.GetPokemonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetDetailPokemonUseCase @Inject constructor(
    private val repository: GetPokemonRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseUseCase<String, Result<PokemonDetailResponse>>() {

    override fun execute(params: String): Flow<Result<PokemonDetailResponse>> =
        repository.getPokemonDetail(params).flowOn(dispatcher)
}