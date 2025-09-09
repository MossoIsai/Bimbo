package com.mosso.bimbo.core.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(pokemon: PokemonEntity)

    @Query("SELECT * FROM pokemon")
    fun getNews(): Flow<List<PokemonEntity>>
}