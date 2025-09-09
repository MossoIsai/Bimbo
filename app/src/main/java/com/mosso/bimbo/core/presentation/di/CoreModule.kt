package com.mosso.bimbo.core.presentation.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.mosso.bimbo.core.data.PokemonDao
import com.mosso.bimbo.core.data.PokemonDatabase
import com.mosso.bimbo.pokemon.data.service.PokemonService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {

        val httpLoginInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_CONNECTION_HTTP, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_WRITE_HTTP, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_READ_HTTP, TimeUnit.SECONDS)
            .addInterceptor(httpLoginInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class IoDispatcher

    @Provides
    fun provideApiService(retrofit: Retrofit): PokemonService =
        retrofit.create(PokemonService::class.java)

    @Provides
    @Singleton
    fun providePreferencesDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            scope = CoroutineScope(SupervisorJob()),
            produceFile = { context.preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }

    @Provides
    fun provideDao(database: PokemonDatabase): PokemonDao =
        database.pokemonDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PokemonDatabase =
        Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            "pokemon_database"
        ).fallbackToDestructiveMigration(false)
            .build()

    private const val BASE_URL = "https://pokeapi.co/api/v2/"
    private const val TIMEOUT_READ_HTTP = 30L
    private const val TIMEOUT_WRITE_HTTP = 30L
    private const val TIMEOUT_CONNECTION_HTTP = 15L

    private const val USER_PREFERENCES = "MY_PREFERENCES"

}