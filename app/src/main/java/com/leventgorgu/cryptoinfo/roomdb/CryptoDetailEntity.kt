package com.leventgorgu.cryptoinfo.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CryptoDetail")
data class CryptoDetailEntity (
    @PrimaryKey(autoGenerate = true)
    val cryptoDetailId: Int ,
    @ColumnInfo(name = "Category")
    val category: String,
    @ColumnInfo(name = "Description")
    val description: String,
    @ColumnInfo(name = "Crypto Name")
    val cryptoName: String,
    @ColumnInfo(name = "Crypto Symbol")
    val cryptoSymbol: String
    )

