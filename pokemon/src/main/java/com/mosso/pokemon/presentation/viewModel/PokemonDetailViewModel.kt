package com.mosso.pokemon.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mosso.pokemon.domain.usecase.GetDetailPokemonUseCase
import com.mosso.pokemon.presentation.state.PokemonDetailUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.mosso.core.presentation.Result

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val useCase: GetDetailPokemonUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonDetailUIState>(PokemonDetailUIState.Loading)
    val uiState get() = _uiState.asStateFlow()

    fun getPokemonDetail(pokemonName: String) = viewModelScope.launch {
        useCase.execute(pokemonName).collect { state ->
            when (state) {
                is Result.Error -> _uiState.emit(
                    PokemonDetailUIState.Error(
                        state.throwable.message ?: ""
                    )
                )

                is Result.Success ->
                    _uiState.emit(
                        PokemonDetailUIState.ShowPokemonDetail(
                            state.body
                        )
                    )
            }
        }
    }
}