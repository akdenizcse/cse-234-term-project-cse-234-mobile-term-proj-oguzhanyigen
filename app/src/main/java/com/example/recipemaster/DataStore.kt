// DataStore.kt
package com.example.recipemaster

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

object UserPreferences {
    private val USER_ID_KEY = intPreferencesKey("user_id")
    private val USER_TOKEN_KEY = stringPreferencesKey("user_token")

    suspend fun saveUserId(context: Context, userId: Int) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
        }
    }

    suspend fun saveUserToken(context: Context, userToken: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_TOKEN_KEY] = userToken
        }
    }

    fun getUserId(context: Context): Flow<Int?> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_ID_KEY]
        }
    }

    fun getUserToken(context: Context): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_TOKEN_KEY]
        }
    }

    suspend fun getUserTokenSync(context: Context): String? {
        return context.dataStore.data.map { preferences ->
            preferences[USER_TOKEN_KEY]
        }.first()
    }
}
