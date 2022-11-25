package com.leventgorgu.cryptoinfo.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CryptoFavorite")
data class CryptoFavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "Name")
    val name: String,
    @ColumnInfo(name = "Symbol")
    val symbol: String,
    @ColumnInfo(name = "Rank")
    val cmcRank: Int,
)