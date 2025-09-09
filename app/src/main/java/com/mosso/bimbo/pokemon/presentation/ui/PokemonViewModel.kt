package com.mosso.bimbo.pokemon.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mosso.bimbo.core.presentation.Result
import com.mosso.bimbo.pokemon.domain.usecase.GetPokemonListUseCase
import com.mosso.bimbo.pokemon.presentation.state.PokemonListUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    val uiState: StateFlow<PokemonListUIState> =
        getPokemonListUseCase.execute()
            .map { result ->
                when (result) {
                    is Result.Success -> {
                        if (result.body.pokemonList.isEmpty()) {
                            PokemonListUIState.Empty
                        } else {
                            PokemonListUIState.DisplayList(
                                pokemonList = result.body.pokemonList
                            )
                        }
                    }

                    is Result.Error -> PokemonListUIState.Error(
                        result.throwable.message.orEmpty()
                    )
                }
            }
            .onStart { emit(PokemonListUIState.Loading) }
            .catch { emit(PokemonListUIState.Error(it.message.orEmpty())) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Companion.WhileSubscribed(5_000),
                initialValue = PokemonListUIState.Loading
            )
}