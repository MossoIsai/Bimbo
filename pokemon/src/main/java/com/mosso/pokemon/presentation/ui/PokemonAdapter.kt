package com.mosso.pokemon.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mosso.pokemon.databinding.ItemLayoutPokemonBinding
import com.mosso.pokemon.domain.models.PokemonDomain

class PokemonAdapter(
    private val onSelectPokemon: (PokemonDomain) -> Unit
) : ListAdapter<PokemonDomain, PokemonAdapter.PokemonViewHolder>(PokemonDiffCallback()) {

    override fun onCreateViewHolder(
        viewgroup: ViewGroup,
        viewType: Int
    ): PokemonViewHolder {
        val itemBinding = ItemLayoutPokemonBinding.inflate(
            LayoutInflater.from(viewgroup.context),
            viewgroup,
            false
        )
        return PokemonViewHolder(itemBinding)
    }

    override fun onBindViewHolder(
        holder: PokemonViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class PokemonViewHolder(private val bindingItem: ItemLayoutPokemonBinding) :
        RecyclerView.ViewHolder(bindingItem.root) {
        fun bind(pokemon: PokemonDomain) {
            bindingItem.tvPokemonName.text = pokemon.name
            bindingItem.clContainerItemPokemon.setOnClickListener {
                onSelectPokemon(pokemon)
            }
        }
    }

    class PokemonDiffCallback : DiffUtil.ItemCallback<PokemonDomain>() {

        override fun areItemsTheSame(
            oldItem: PokemonDomain,
            newItem: PokemonDomain
        ): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(
            oldItem: PokemonDomain,
            newItem: PokemonDomain
        ): Boolean =
            oldItem == newItem
    }
}