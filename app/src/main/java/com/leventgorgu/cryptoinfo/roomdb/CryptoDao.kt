package com.leventgorgu.cryptoinfo.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CryptoDao {

    @Query("SELECT * FROM CryptoDetail WHERE cryptoDetailId =:cryptoDetailId")
    suspend fun getCryptoDetailEntity(cryptoDetailId:Int): CryptoDetailEntity

    @Query("SELECT * FROM Cryptos WHERE id =:cryptoId")
    suspend fun getCryptoWithId(cryptoId:Int): CryptoEntity

    @Query("SELECT * FROM Cryptos ORDER BY `CoinMarketCap_Rank` ASC")
    fun getAllCrypto(): LiveData<List<CryptoEntity>>

    @Query("SELECT * FROM Cryptos WHERE Name LIKE '%' || :cryptoName || '%'")
    suspend fun searchCrypto(cryptoName:String): List<CryptoEntity>

    @Query("SELECT * FROM CryptoFavorite")
    fun getAllFavoriteCryptos():LiveData<List<CryptoFavoriteEntity>>


    @Delete
    suspend fun deleteCryptoFavorite(cryptoFavoriteEntity: CryptoFavoriteEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCryptoFavorite(cryptoFavoriteEntity: CryptoFavoriteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrypto(vararg cryptoEntity: CryptoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCryptoDetail(cryptoDetailEntity: CryptoDetailEntity)
}