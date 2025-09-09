package com.mosso.bimbo.core.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon", indices = [Index(value = ["newsId"], unique = true)])
data class PokemonEntity(
    @PrimaryKey @ColumnInfo(name = "newsId") val newsId: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "url") val url: String,
)
