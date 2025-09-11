package com.mosso.pokemon.presentation.di


import com.mosso.pokemon.data.GetPokemonRepositoryImp
import com.mosso.pokemon.domain.repository.GetPokemonRepository
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