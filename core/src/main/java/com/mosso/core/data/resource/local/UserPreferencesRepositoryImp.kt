package com.mosso.core.data.resource.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mosso.core.domain.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepositoryImp @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserPreferencesRepository {

    private object Keys {
        val USER_NAME = stringPreferencesKey("user_name")
    }

    override suspend fun saveUserName(name: String) {
        dataStore.edit { preferences ->
            preferences[Keys.USER_NAME] = name
        }
    }

    override fun getUserName(): Flow<String> =
        dataStore.data.map { preferences ->
            preferences[Keys.USER_NAME] ?: ""
        }
}