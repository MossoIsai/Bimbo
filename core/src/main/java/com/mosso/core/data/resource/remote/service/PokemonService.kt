package com.mosso.core.data.resource.remote.service


import com.mosso.core.data.models.GetPokemonListResponse
import com.mosso.core.data.models.PokemonDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {

    @GET("pokemon")
    suspend fun getPokemonList(): Response<GetPokemonListResponse>

    @GET("pokemon/{pokemonName}")
    suspend fun getDetailPokemon(
        @Path("pokemonName") pokemonName: String
    ): Response<PokemonDetailResponse>
}