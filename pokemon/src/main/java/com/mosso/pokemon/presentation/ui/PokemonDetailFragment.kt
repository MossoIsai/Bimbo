package com.mosso.pokemon.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.mosso.pokemon.R
import com.mosso.pokemon.databinding.FragmentPokemonDetailBinding
import com.mosso.pokemon.domain.models.PokemonDetailDomain
import com.mosso.pokemon.presentation.state.PokemonDetailUIState
import com.mosso.pokemon.presentation.viewModel.PokemonDetailViewModel
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
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = pokemonName?.uppercase() ?: ""
            setDisplayHomeAsUpEnabled(true) // Show back arrow
        }
        pokemonName?.let {
            viewmodel.getPokemonDetail(it)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewmodel.uiState.collect { uiState ->
                    when (uiState) {
                        is PokemonDetailUIState.Error -> showError(uiState.message)
                        PokemonDetailUIState.Loading -> showLoader()
                        is PokemonDetailUIState.ShowPokemonDetail -> showPokemonDetail(uiState.pokemonDetail)
                    }
                }
            }
        }
    }

    private fun showPokemonDetail(pokemonDetail: PokemonDetailDomain) {
        with(binding) {
            val abilities = pokemonDetail.abilities
            binding.tvPokemonName.text = pokemonDetail.name
            Glide.with(binding.imgPokemon).load(pokemonDetail.urlImg)
                .placeholder(R.drawable.poke_placeholder)
                .into(binding.imgPokemon)
            binding.tvWeight.text =
                getString(R.string.weight_format, pokemonDetail.weight / 10)
            binding.tvHeight.text =
                getString(R.string.height_format, pokemonDetail.height / 10)
            binding.progressBar.visibility = View.GONE
            binding.tvPokemonName.visibility = View.VISIBLE
            binding.imgPokemon.visibility = View.VISIBLE
            binding.tvHeight.visibility = View.VISIBLE
            binding.tvHeight.visibility = View.VISIBLE
            for (item in abilities) {
                binding.tvAbilitiesList.append(item.name + " ")
            }
        }
    }

    private fun showLoader() {
        with(binding) {
            binding.progressBar.visibility = View.VISIBLE
            binding.tvPokemonName.visibility = View.GONE
            binding.imgPokemon.visibility = View.VISIBLE
            binding.tvHeight.visibility = View.GONE
            binding.tvHeight.visibility = View.GONE
        }
    }

    private fun showError(errorMessage: String) {
        with(binding) {
            binding.tvPokemonName.visibility = View.VISIBLE
            binding.imgPokemon.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
            /* SnackBarMessage.make(clContainerPokemonDetail, errorMessage)
                 .show()*/
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