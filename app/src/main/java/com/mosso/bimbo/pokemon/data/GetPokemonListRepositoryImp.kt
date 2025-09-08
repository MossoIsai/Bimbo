package com.mosso.bimbo.pokemon.data

import com.mosso.bimbo.pokemon.domain.GetPokemonListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPokemonListRepositoryImp @Inject constructor(
    private val apiService: PokemonService
) : GetPokemonListRepository {
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
}