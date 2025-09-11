package com.mosso.pokemon.presentation.state

import com.mosso.pokemon.domain.models.PokemonDomain

sealed class PokemonListUIState {
    data object Loading : PokemonListUIState()
    data class Error(val message: String) : PokemonListUIState()
    data object Empty : PokemonListUIState()
    data class DisplayList(var pokemonList: List<PokemonDomain>) : PokemonListUIState()
}