package com.leventgorgu.cryptoinfo.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.leventgorgu.cryptoinfo.getOrAwaitValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class CryptoDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    lateinit var cryptoDao: CryptoDao


    @Inject
    @Named("testDatabase")
    lateinit var cryptoDatabase: CryptoDatabase


    @Before
    fun setup(){
        hiltRule.inject()
        cryptoDao = cryptoDatabase.getCryptoDao()
    }


    @Test
    fun insertCryptoTest() = runTest{
        val cryptoArrayList = ArrayList<CryptoEntity>()

        val cryptoEntityBTC = CryptoEntity(1,"Bitcoin","BTC",1,"16.000","null")
        val cryptoEntityETH = CryptoEntity(2,"Ethereum","ETH",2,"2.000","null")
        cryptoArrayList.add(cryptoEntityBTC)
        cryptoArrayList.add(cryptoEntityETH)

        cryptoDao.insertCrypto(*cryptoArrayList.toTypedArray())
        val data = cryptoDao.getAllCrypto().getOrAwaitValue()

        assertThat(ArrayList(data)).contains(cryptoEntityBTC)
    }

    @Test
    fun insertCryptoDetailTest() = runTest{
        val cryptoDetailEntityBTC = CryptoDetailEntity(1,"coin","Bitcoin nice","Bitcoin","BTC")

        cryptoDao.insertCryptoDetail(cryptoDetailEntityBTC)
        val data = cryptoDao.getCryptoDetailEntity(cryptoDetailEntityBTC.cryptoDetailId)
        assertThat(data).isEqualTo(cryptoDetailEntityBTC)
    }

    @Test
    fun insertCryptoFavoriteTest()= runTest{

        val cryptoFavoriteEntityUSDT = CryptoFavoriteEntity(1,"Tether","USDT",3)
        cryptoDao.insertCryptoFavorite(cryptoFavoriteEntityUSDT)

        val data = cryptoDao.getAllFavoriteCryptos().getOrAwaitValue()
        assertThat(data).contains(cryptoFavoriteEntityUSDT)
    }

    @Test
    fun deleteCryptoFavoriteTest()=runTest{

        val cryptoFavoriteEntityUSDT = CryptoFavoriteEntity(1,"Tether","USDT",3)
        cryptoDao.insertCryptoFavorite(cryptoFavoriteEntityUSDT)

        cryptoDao.deleteCryptoFavorite(cryptoFavoriteEntityUSDT)

        val data = cryptoDao.getAllFavoriteCryptos().getOrAwaitValue()
        assertThat(data).doesNotContain(cryptoFavoriteEntityUSDT)
    }

    @Test
    fun searchCryptoTest()= runTest{
        val cryptoArrayList = ArrayList<CryptoEntity>()

        val cryptoEntityBTC = CryptoEntity(1,"Bitcoin","BTC",1,"16.000","null")
        val cryptoEntityETH = CryptoEntity(2,"Ethereum","ETH",2,"2.000","null")
        val cryptoEntityUSDT= CryptoEntity(3,"Tether","USDT",3,"0.100","Ethereum")

        cryptoArrayList.add(cryptoEntityBTC)
        cryptoArrayList.add(cryptoEntityETH)
        cryptoArrayList.add(cryptoEntityUSDT)

        cryptoDao.insertCrypto(*cryptoArrayList.toTypedArray())

        val data = cryptoDao.searchCrypto("E")

        assertThat(data).doesNotContain(cryptoEntityBTC)
    }

    @Test
    fun getCryptoWithIdTest()= runTest{
        val cryptoArrayList = ArrayList<CryptoEntity>()

        val cryptoEntityBTC = CryptoEntity(1,"Bitcoin","BTC",1,"16.000","null")
        val cryptoEntityETH = CryptoEntity(2,"Ethereum","ETH",2,"2.000","null")
        cryptoArrayList.add(cryptoEntityBTC)
        cryptoArrayList.add(cryptoEntityETH)

        cryptoDao.insertCrypto(*cryptoArrayList.toTypedArray())
        val data = cryptoDao.getCryptoWithId(cryptoEntityBTC.id)

        assertThat(data).isEqualTo(cryptoEntityBTC)
    }


    @After
    fun teardown(){
        cryptoDatabase.close()
    }

}