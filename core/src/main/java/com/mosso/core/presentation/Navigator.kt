package com.mosso.core.presentation

import android.os.Bundle

interface Navigator {
    fun navigateToPokemonList()
    fun navigateToPokemonDetail(pokemonName: String)
}