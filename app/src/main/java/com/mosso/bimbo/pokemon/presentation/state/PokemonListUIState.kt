package com.mosso.bimbo.pokemon.presentation.state

import com.mosso.bimbo.pokemon.domain.model.Pokemon

sealed class PokemonListUIState {
    data object Loading : PokemonListUIState()
    data class Error(val message: String) : PokemonListUIState()
    data object Empty : PokemonListUIState()
    data class DisplayList(var pokemonList: List<Pokemon>) : PokemonListUIState()
}