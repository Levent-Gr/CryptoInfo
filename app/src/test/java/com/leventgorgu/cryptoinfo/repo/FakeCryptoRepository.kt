package com.leventgorgu.cryptoinfo.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.leventgorgu.cryptoinfo.getOrAwaitValueTest
import com.leventgorgu.cryptoinfo.model.cryptoInfo.CryptoInfo
import com.leventgorgu.cryptoinfo.model.cryptoInfo.Status
import com.leventgorgu.cryptoinfo.model.cryptos.Cryptos
import com.leventgorgu.cryptoinfo.roomdb.CryptoDetailEntity
import com.leventgorgu.cryptoinfo.roomdb.CryptoEntity
import com.leventgorgu.cryptoinfo.roomdb.CryptoFavoriteEntity
import com.leventgorgu.cryptoinfo.util.APIResult

class FakeCryptoRepository : CryptoRepositoryInterface {

    private val cryptosArray = mutableListOf<CryptoEntity>()
    private val cryptosLiveData = MutableLiveData<List<CryptoEntity>>(cryptosArray)

    private val cryptoDetailArray = mutableListOf<CryptoDetailEntity>()
    private val cryptoDetailLiveData = MutableLiveData<List<CryptoDetailEntity>>(cryptoDetailArray)


    private val cryptoFavoritesArray = mutableListOf<CryptoFavoriteEntity>()
    private val cryptoFavoritesLiveData = MutableLiveData<List<CryptoFavoriteEntity>>(cryptoFavoritesArray)

    private fun refreshCryptoEntityData(){
        cryptosLiveData.postValue(cryptosArray)
    }
    private fun refreshCryptoDetailData(){
        cryptoDetailLiveData.postValue(cryptoDetailArray)
    }
    private fun refreshCryptoFavoritesData(){
        cryptoFavoritesLiveData.postValue(cryptoFavoritesArray)
    }


    override suspend fun insertCrypto(cryptoList: ArrayList<CryptoEntity>) {
        for (crypto in cryptoList){
            cryptosArray.add(crypto)
        }
        refreshCryptoEntityData()
    }

    override suspend fun insertCryptoDetail(cryptoDetailEntity: CryptoDetailEntity) {
        cryptoDetailArray.add(cryptoDetailEntity)
        refreshCryptoDetailData()
    }

    override fun getCryptos(): LiveData<List<CryptoEntity>> {
        return cryptosLiveData
    }

    override fun getAllFavoriteCryptos(): LiveData<List<CryptoFavoriteEntity>> {
        return cryptoFavoritesLiveData
    }

    override suspend fun insertCryptoFavorite(cryptoFavoriteEntity: CryptoFavoriteEntity) {
        cryptoFavoritesArray.add(cryptoFavoriteEntity)
        refreshCryptoFavoritesData()
    }

    override suspend fun deleteCryptoFavorite(cryptoFavoriteEntity: CryptoFavoriteEntity) {
        cryptoFavoritesArray.remove(cryptoFavoriteEntity)
        refreshCryptoFavoritesData()
    }

    override suspend fun searchCrypto(cryptoName: String): List<CryptoEntity> {

        val cryptoList = mutableListOf<CryptoEntity>()
        val cryptoSearchData = cryptosLiveData.getOrAwaitValueTest()

        for (crypto in cryptoSearchData){
            if (crypto.name.equals(cryptoName)){
                cryptoList.add(crypto)
            }
        }
        return cryptoList
    }

    override suspend fun getCryptoWithId(cryptoId: Int): CryptoEntity {
        val cryptoEntityBTC = CryptoEntity(0, "Bitcoin", "BTC", 1, "16.000", "")
        val cryptoEntityETH = CryptoEntity(1, "Ethereum", "ETH", 2, "2.000", "")

        cryptosArray.add(cryptoEntityBTC)
        cryptosArray.add(cryptoEntityETH)

        var id = 0
        var name = ""
        var symbol = ""
        var rank = 0
        var price = "0"
        var platform = ""

        val getCryptoData = cryptosLiveData.getOrAwaitValueTest()

        for (crypto in getCryptoData) {
            if (crypto.id == cryptoId) {
                id = crypto.id
                name = crypto.name
                symbol = crypto.symbol
                rank = crypto.cmcRank
                price = crypto.price
                platform = crypto.platform
            }
        }

        return CryptoEntity(id, name, symbol, rank, price, platform)
    }

    override suspend fun getCryptoDetailEntity(cryptoDetailId: Int): CryptoDetailEntity {
        val cryptoDetailEntityBTC = CryptoDetailEntity(0,"" , "This is bitcoin", "Bitcoin", "BTC")
        val cryptoDetailEntityETH = CryptoDetailEntity(1, "", "This is ethereum", "Ethereum", "ETH")

        cryptoDetailArray.add(cryptoDetailEntityBTC)
        cryptoDetailArray.add(cryptoDetailEntityETH)

        var id = 0
        var category = ""
        var description = ""
        var name = ""
        var symbol = ""


        val getCryptoDetailData = cryptoDetailLiveData.getOrAwaitValueTest()

        for (cryptoDetail in getCryptoDetailData) {
            if (cryptoDetail.cryptoDetailId == cryptoDetailId) {
                id = cryptoDetail.cryptoDetailId
                name = cryptoDetail.cryptoName
                symbol = cryptoDetail.cryptoSymbol
                category = cryptoDetail.category
                description = cryptoDetail.description
            }
        }

        return CryptoDetailEntity(id,category,description,name,symbol)
    }

    override suspend fun getCryptoDataFromAPI(limit: String): APIResult<ArrayList<CryptoEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCryptoInfoDataFromAPI(symbol: String): APIResult<CryptoInfo> {
        TODO("Not yet implemented")
    }

    override fun getCryptoEntityData(cryptos: Cryptos): ArrayList<CryptoEntity> {
        TODO("Not yet implemented")
    }
}