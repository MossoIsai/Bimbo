package com.mosso.bimbo.pokemon.presentation.di

import com.mosso.bimbo.pokemon.data.repository.GetPokemonRepositoryImp
import com.mosso.bimbo.pokemon.domain.repository.GetPokemonRepository
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
        repositoryImp: GetPokemonRepositoryImp
    ): GetPokemonRepository

}