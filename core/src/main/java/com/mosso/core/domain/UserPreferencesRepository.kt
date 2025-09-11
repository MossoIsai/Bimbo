package com.mosso.core.domain

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    suspend fun saveUserName(name: String)
    fun getUserName(): Flow<String>
}