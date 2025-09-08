package com.mosso.bimbo.pokemon.data

import retrofit2.Response
import retrofit2.http.GET

interface PokemonService {

    @GET("pokemon")
    suspend fun getPokemonList(): Response<GetPokemonListResponse>

}