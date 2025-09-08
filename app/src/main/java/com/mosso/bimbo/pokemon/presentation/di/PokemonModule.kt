package com.mosso.bimbo.pokemon.presentation.di

import com.mosso.bimbo.pokemon.data.GetPokemonListRepositoryImp
import com.mosso.bimbo.pokemon.domain.GetPokemonListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PokemonModule {

    @Binds
    @Singleton
    abstract fun providePokemonRepository(
        repositoryImp: GetPokemonListRepositoryImp
    ): GetPokemonListRepository

}