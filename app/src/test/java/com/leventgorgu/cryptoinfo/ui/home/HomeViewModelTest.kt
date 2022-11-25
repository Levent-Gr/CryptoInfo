package com.leventgorgu.cryptoinfo.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.viewModels
import com.google.common.truth.Truth.assertThat
import com.leventgorgu.cryptoinfo.MainCoroutineRule
import com.leventgorgu.cryptoinfo.getOrAwaitValueTest
import com.leventgorgu.cryptoinfo.repo.FakeCryptoRepository
import com.leventgorgu.cryptoinfo.roomdb.CryptoEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup(){
        homeViewModel = HomeViewModel(FakeCryptoRepository())
    }

    @Test
    fun insertCryptoTest(){
        val cryptoArrayList = ArrayList<CryptoEntity>()

        val cryptoEntityBTC = CryptoEntity(0,"Bitcoin","BTC",1,"16.000","")
        val cryptoEntityETH = CryptoEntity(1,"Ethereum","ETH",2,"2.000","")
        cryptoArrayList.add(cryptoEntityBTC)
        cryptoArrayList.add(cryptoEntityETH)

        homeViewModel.insertCryptos(cryptoArrayList)
        val data = homeViewModel.cryptoListLiveDataFromSQl.getOrAwaitValueTest()

        assertThat(data).contains(cryptoEntityBTC)
    }

    @Test
    fun insertCryptosWithoutCryptoEntityTest(){
        val cryptoArrayList = ArrayList<CryptoEntity>()
        homeViewModel.insertCryptos(cryptoArrayList)
        val data = homeViewModel.cryptoListLiveDataFromSQl.getOrAwaitValueTest()

        assertThat(data).isEmpty()
    }

    @Test
    fun searchCryptoEntityTest(){
        val cryptoArrayList = ArrayList<CryptoEntity>()
        val cryptoEntityBTC = CryptoEntity(0,"Bitcoin","BTC",1,"16.000","")
        val cryptoEntityETH = CryptoEntity(1,"Ethereum","ETH",2,"2.000","")
        val cryptoEntityUSDC = CryptoEntity(2,"USD Coin","USDC",4,"1","Ethereum")

        cryptoArrayList.add(cryptoEntityBTC)
        cryptoArrayList.add(cryptoEntityETH)
        cryptoArrayList.add(cryptoEntityUSDC)

        homeViewModel.insertCryptos(cryptoArrayList)

        homeViewModel.searchCrypto("Bitcoin")
        val data = homeViewModel.cryptosSearch.getOrAwaitValueTest()

        assertThat(data).contains(cryptoEntityBTC)
    }



}