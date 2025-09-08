package com.mosso.bimbo.pokemon.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mosso.bimbo.databinding.FragmentPokemonDetailBinding
import com.mosso.bimbo.pokemon.data.models.PokemonDetailResponse
import com.mosso.bimbo.pokemon.presentation.state.PokemonDetailUIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {

    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding get() = _binding!!
    private val viewmodel: PokemonDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokemonName = arguments?.getString(KEY_NAME_POKEMON)
        pokemonName?.let {
            viewmodel.getPokemonDetail(it)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewmodel.uiState.collect { uiState ->
                    when (uiState) {
                        is PokemonDetailUIState.Error -> Log.d("Mosso", uiState.message)
                        PokemonDetailUIState.Loading -> Log.d("Mosso", "Loading")
                        is PokemonDetailUIState.ShowPokemonDetail -> showPokemonDetail(uiState.pokemonDetail)
                    }
                }
            }
        }
    }

    private fun showPokemonDetail(pokemonDetailResponse: PokemonDetailResponse) {
        with(binding) {
            binding.tvPokemonName.text = pokemonDetailResponse.forms[0].name
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val KEY_NAME_POKEMON = "pokemon_name"
    }
}