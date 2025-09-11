package com.mosso.core.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon", indices = [Index(value = ["pokemonId"], unique = true)])
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true) val pokemonId: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "url") val url: String,
)