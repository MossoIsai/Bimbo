package com.mosso.core.presentation.di

import com.mosso.core.data.resource.local.UserPreferencesRepositoryImp
import com.mosso.core.domain.UserPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserPreferencesModule {

    @Binds
    @Singleton
    abstract fun bindUserPreferences(
        impl: UserPreferencesRepositoryImp
    ): UserPreferencesRepository
}