package com.mosso.pokemon.data

import com.mosso.core.data.entities.PokemonEntity
import com.mosso.core.data.resource.local.PokemonDao
import com.mosso.core.data.resource.remote.service.PokemonService
import com.mosso.pokemon.domain.models.PokemonDomain
import com.mosso.pokemon.domain.repository.GetPokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import com.mosso.core.presentation.Result
import com.mosso.pokemon.domain.mappers.toDomain
import com.mosso.pokemon.domain.mappers.toPokemonEntity
import com.mosso.pokemon.domain.mappers.totoDomain
import com.mosso.pokemon.domain.models.PokemonDetailDomain
import javax.inject.Inject

class GetPokemonRepositoryImp @Inject constructor(
    private val apiService: PokemonService,
    private val dao: PokemonDao
) : GetPokemonRepository {

    override fun getPokemonList(): Flow<Result<List<PokemonDomain>>> =
        flow<Result<List<PokemonDomain>>> {
            val response = apiService.getPokemonList()
            if (response.isSuccessful) {
                response.body()?.let {
                    val pokemonFromDB = dao.getPokemonList()
                    if (pokemonFromDB.first().isEmpty()) {
                        emit(Result.Success(it.pokemonList.totoDomain()))
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

    override fun getPokemonDetail(pokemonName: String): Flow<Result<PokemonDetailDomain>> =
        flow<Result<PokemonDetailDomain>> {
            val response = apiService.getDetailPokemon(pokemonName)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.Success(it.toDomain()))
                } ?: Throwable(response.message())
            } else {
                emit(Result.Error(Throwable(response.message())))
            }
        }.catch { e ->
            emit(Result.Error(e))
        }
}