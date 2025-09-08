package com.mosso.bimbo.pokemon.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mosso.bimbo.databinding.PokemonListFragmentBinding
import com.mosso.bimbo.pokemon.domain.Pokemon
import com.mosso.bimbo.pokemon.presentation.ui.PokemonViewModel
import com.mosso.bimbo.pokemon.presentation.state.PokemonListUIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonListFragment : Fragment() {
    private var _binding: PokemonListFragmentBinding? = null
    private val binding get() = _binding!!
    private val pokemonViewModel: PokemonViewModel by viewModels()
    private lateinit var pokemonAdapter: PokemonAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PokemonListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                pokemonViewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is PokemonListUIState.DisplayList -> {
                            displayPokemonList(uiState.pokemonList)
                        }

                        is PokemonListUIState.Empty -> emptyState()
                        is PokemonListUIState.Error -> uiState.message
                        is PokemonListUIState.Loading -> showLoader()
                    }
                }
            }
        }
    }

    private fun displayPokemonList(pokemonList: List<Pokemon>) {
        with(binding) {
            sfLayout.visibility = View.GONE
            rvPokemonList.visibility = View.VISIBLE
            clEmptyStateLayout.visibility = View.GONE
            pokemonAdapter.submitList(pokemonList)
            rvPokemonList.layoutManager = LinearLayoutManager(
                activity,
                RecyclerView.VERTICAL,
                false
            )
            rvPokemonList.setHasFixedSize(true)
            rvPokemonList.adapter = pokemonAdapter
        }
    }

    private fun showLoader() {
        with(binding) {
            sfLayout.visibility = View.VISIBLE
            rvPokemonList.visibility = View.GONE
            clEmptyStateLayout.visibility = View.GONE
            sfLayout.startShimmer()
        }
    }

    private fun emptyState() {
        with(binding) {
            sfLayout.visibility = View.GONE
            rvPokemonList.visibility = View.GONE
            clEmptyStateLayout.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}