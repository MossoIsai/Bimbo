package com.mosso.bimbo.pokemon.presentation.ui

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
import com.mosso.bimbo.R
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

    private fun showPokemonDetail(pokemonDetailResponse: PokemonDetailResponse) {
        with(binding) {
            val abilities = pokemonDetailResponse.abilities
            binding.tvPokemonName.text = pokemonDetailResponse.name
            Glide.with(binding.imgPokemon).load(pokemonDetailResponse.sprites.photo)
                .placeholder(R.drawable.poke_placeholder)
                .into(binding.imgPokemon)
            binding.tvWeight.text =
                getString(R.string.weight_format, pokemonDetailResponse.weight.toDouble() / 10)
            binding.tvHeight.text =  getString(R.string.height_format, pokemonDetailResponse.height.toDouble() / 10)
            binding.progressBar.visibility = View.GONE
            binding.tvPokemonName.visibility = View.VISIBLE
            binding.imgPokemon.visibility = View.VISIBLE
            binding.tvHeight.visibility = View.VISIBLE
            binding.tvHeight.visibility = View.VISIBLE
            for (item in abilities) {
                binding.tvAbilitiesList.append(item.ability.name + " ")
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
            SnackBarMessage.make(clContainerPokemonDetail, errorMessage)
                .show()
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