package com.mosso.bimbo.pokemon.data.repository

import com.mosso.bimbo.pokemon.data.service.PokemonService
import com.mosso.bimbo.core.presentation.Result
import com.mosso.bimbo.pokemon.data.models.GetPokemonListResponse
import com.mosso.bimbo.pokemon.data.models.PokemonDetailResponse
import com.mosso.bimbo.pokemon.domain.repository.GetPokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPokemonRepositoryImp @Inject constructor(
    private val apiService: PokemonService
) : GetPokemonRepository {

    override fun getPokemonList(): Flow<Result<GetPokemonListResponse>> =
        flow<Result<GetPokemonListResponse>> {
            val response = apiService.getPokemonList()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.Success(it))
                } ?: Throwable(response.message())
            } else {
                emit(Result.Error(Throwable(response.message())))
            }
        }.catch { e ->
            emit(Result.Error(e))
        }

    override fun getPokemonDetail(pokemonName: String): Flow<Result<PokemonDetailResponse>> =
        flow<Result<PokemonDetailResponse>> {
            val response = apiService.getDetailPokemon(pokemonName)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.Success(it))
                } ?: Throwable(response.message())
            } else {
                emit(Result.Error(Throwable(response.message())))
            }
        }.catch { e ->
            emit(Result.Error(e))
        }
}