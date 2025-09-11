package com.mosso.pokemon.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mosso.pokemon.databinding.PokemonListFragmentBinding
import com.mosso.pokemon.domain.models.PokemonDomain
import com.mosso.pokemon.presentation.state.PokemonListUIState
import com.mosso.pokemon.presentation.ui.PokemonDetailFragment.Companion.KEY_NAME_POKEMON
import com.mosso.pokemon.presentation.viewModel.PokemonViewModel
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
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Bienvenido " + pokemonViewModel.getUserName()
            setDisplayHomeAsUpEnabled(false)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                pokemonViewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is PokemonListUIState.DisplayList -> {
                            displayPokemonList(uiState.pokemonList)
                        }

                        is PokemonListUIState.Empty -> emptyState()
                        is PokemonListUIState.Error -> showError(uiState.message)
                        is PokemonListUIState.Loading -> showLoader()
                    }
                }
            }
        }
    }

    private fun displayPokemonList(pokemonList: List<PokemonDomain>) {
        with(binding) {
            sfLayout.visibility = View.GONE
            rvPokemonList.visibility = View.VISIBLE
            clEmptyStateLayout.visibility = View.GONE
            pokemonAdapter = PokemonAdapter { item ->
                val bundle = Bundle().apply {
                    putString(KEY_NAME_POKEMON, item.name)
                }

                findNavController().navigate("app://pokemon/detail".toUri()) {
                    arguments = bundle
                }
            }
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

    private fun showError(errorMessage: String) {
        with(binding) {
            sfLayout.visibility = View.GONE
            rvPokemonList.visibility = View.GONE
            clEmptyStateLayout.visibility = View.VISIBLE
           /* SnackBarMessage.make(clMainContainer, errorMessage)
                .show()*/
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}