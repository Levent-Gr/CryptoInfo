package com.leventgorgu.cryptoinfo.api

import com.leventgorgu.cryptoinfo.model.cryptoInfo.CryptoInfo
import com.leventgorgu.cryptoinfo.model.cryptos.Cryptos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CryptoAPI {

    @GET("/v1/cryptocurrency/listings/latest")
    suspend fun getCryptos(
        @Header("X-CMC_PRO_API_KEY") apikey:String,
        @Query("limit") limit :String
    ): Response<Cryptos>

    //@Query("sort") sort:String="market_cap",



    @GET("/v2/cryptocurrency/info")
    suspend fun getCryptoInfo(
        @Header("X-CMC_PRO_API_KEY") apikey:String,
        @Query("symbol") symbol :String
    ): Response<CryptoInfo>
}