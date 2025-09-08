package com.mosso.bimbo.pokemon.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mosso.bimbo.databinding.ItemLayoutPokemonBinding
import com.mosso.bimbo.pokemon.domain.Pokemon

class PokemonAdapter(
    private val onSelectPokemon: (Pokemon) -> Unit
) : ListAdapter<Pokemon, PokemonAdapter.PokemonViewHolder>(PokemonDiffCallback()) {

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
        fun bind(pokemon: Pokemon) {
            bindingItem.tvPokemonName.text = pokemon.name
            bindingItem.clContainerItemPokemon.setOnClickListener {
                onSelectPokemon(pokemon)
            }
        }
    }

    class PokemonDiffCallback : DiffUtil.ItemCallback<Pokemon>() {

        override fun areItemsTheSame(
            oldItem: Pokemon,
            newItem: Pokemon
        ): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(
            oldItem: Pokemon,
            newItem: Pokemon
        ): Boolean =
            oldItem == newItem
    }
}