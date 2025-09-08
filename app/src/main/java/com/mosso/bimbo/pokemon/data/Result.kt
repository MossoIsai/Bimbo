package com.mosso.bimbo.pokemon.data

sealed class Result<T> {
    data class Success<T>(val body: T) : Result<T>()
    data class Error<T>(val throwable: Throwable): Result<T>()
}