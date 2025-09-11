package com.mosso.pokemon.presentation.state

import com.mosso.pokemon.domain.models.PokemonDetailDomain

sealed class PokemonDetailUIState {
    data object Loading : PokemonDetailUIState()
    data class Error(val message: String) : PokemonDetailUIState()
    data class ShowPokemonDetail(val pokemonDetail: PokemonDetailDomain) : PokemonDetailUIState()
}