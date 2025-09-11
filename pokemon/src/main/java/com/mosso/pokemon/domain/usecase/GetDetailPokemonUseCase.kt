package com.mosso.pokemon.domain.usecase

import com.mosso.core.domain.BaseUseCase
import com.mosso.core.presentation.di.CoreModule.IoDispatcher
import com.mosso.pokemon.domain.models.PokemonDetailDomain
import com.mosso.pokemon.domain.repository.GetPokemonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import com.mosso.core.presentation.Result
import javax.inject.Inject

class GetDetailPokemonUseCase @Inject constructor(
    private val repository: GetPokemonRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseUseCase<String, Result<PokemonDetailDomain>>() {

    override fun execute(params: String): Flow<Result<PokemonDetailDomain>> =
        repository.getPokemonDetail(params).flowOn(dispatcher)
}