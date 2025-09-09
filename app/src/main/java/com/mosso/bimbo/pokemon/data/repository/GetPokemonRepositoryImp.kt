package com.mosso.bimbo.pokemon.data.repository

import com.mosso.bimbo.core.data.PokemonDao
import com.mosso.bimbo.core.data.PokemonEntity
import com.mosso.bimbo.pokemon.data.service.PokemonService
import com.mosso.bimbo.core.presentation.Result
import com.mosso.bimbo.pokemon.data.models.PokemonDetailResponse
import com.mosso.bimbo.pokemon.domain.model.Pokemon
import com.mosso.bimbo.pokemon.domain.model.toDomain
import com.mosso.bimbo.pokemon.domain.model.toPokemonEntity
import com.mosso.bimbo.pokemon.domain.repository.GetPokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPokemonRepositoryImp @Inject constructor(
    private val apiService: PokemonService,
    private val dao: PokemonDao
) : GetPokemonRepository {

    override fun getPokemonList(): Flow<Result<List<Pokemon>>> =
        flow<Result<List<Pokemon>>> {
            val response = apiService.getPokemonList()
            if (response.isSuccessful) {
                response.body()?.let {
                    val pokemonFromDB = dao.getPokemonList()
                    if (pokemonFromDB.first().isEmpty()) {
                        emit(Result.Success(it.pokemonList))
                        dao.insertPokemon(it.toPokemonEntity())
                    } else {
                        emit(Result.Success(pokemonFromDB.first().toDomain()))
                    }
                } ?: Throwable(response.message())
            } else {
                emit(Result.Error(Throwable(response.message())))
            }
        }.catch { e ->
            emit(Result.Error(e))
            val listPokemonFlow: Flow<List<PokemonEntity>> = dao.getPokemonList()
            listPokemonFlow.collect { listNewsEntity ->
                if (listNewsEntity.isEmpty()) {
                    //empty state
                    emit(Result.Success(emptyList()))
                } else {
                    emit(Result.Success(listNewsEntity.toDomain()))
                }
            }
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