package com.mosso.bimbo.pokemon.presentation.state

import com.mosso.bimbo.pokemon.data.models.PokemonDetailResponse

sealed class PokemonDetailUIState {
    data object Loading : PokemonDetailUIState()
    data class Error(val message: String) : PokemonDetailUIState()
    data class ShowPokemonDetail(val pokemonDetail: PokemonDetailResponse) : PokemonDetailUIState()
}