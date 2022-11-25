package com.leventgorgu.cryptoinfo.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.leventgorgu.cryptoinfo.model.cryptoInfo.CryptoDetail
import com.leventgorgu.cryptoinfo.model.cryptoInfo.CryptoInfo
import com.leventgorgu.cryptoinfo.model.cryptos.Cryptos
import com.leventgorgu.cryptoinfo.roomdb.CryptoDetailEntity
import com.leventgorgu.cryptoinfo.roomdb.CryptoEntity
import com.leventgorgu.cryptoinfo.roomdb.CryptoFavoriteEntity
import com.leventgorgu.cryptoinfo.util.APIResult

interface CryptoRepositoryInterface {

    suspend fun insertCrypto(cryptoList: ArrayList<CryptoEntity>)

    suspend fun insertCryptoDetail(cryptoDetailEntity: CryptoDetailEntity)

    fun getCryptos() : LiveData<List<CryptoEntity>>

    fun getAllFavoriteCryptos():LiveData<List<CryptoFavoriteEntity>>

    suspend fun insertCryptoFavorite(cryptoFavoriteEntity: CryptoFavoriteEntity)

    suspend fun deleteCryptoFavorite(cryptoFavoriteEntity: CryptoFavoriteEntity)

    suspend fun searchCrypto(cryptoName:String): List<CryptoEntity>

    suspend fun getCryptoWithId(cryptoId:Int) : CryptoEntity

    suspend fun getCryptoDetailEntity(cryptoDetailId:Int) : CryptoDetailEntity

    suspend fun getCryptoDataFromAPI(limit: String) : APIResult<ArrayList<CryptoEntity>>

    suspend fun getCryptoInfoDataFromAPI(symbol: String) : APIResult<CryptoInfo>

    fun getCryptoEntityData(cryptos: Cryptos):ArrayList<CryptoEntity>

}