package com.mosso.bimbo.core

import com.mosso.bimbo.pokemon.data.service.PokemonService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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


    private const val BASE_URL = "https://pokeapi.co/api/v2/"
    private const val TIMEOUT_READ_HTTP = 30L
    private const val TIMEOUT_WRITE_HTTP = 30L
    private const val TIMEOUT_CONNECTION_HTTP = 15L

    private const val USER_PREFERENCES = "MY_PREFERENCES"

}