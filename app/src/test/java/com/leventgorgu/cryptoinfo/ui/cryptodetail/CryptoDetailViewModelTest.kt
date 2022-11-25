package com.leventgorgu.cryptoinfo.ui.cryptodetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.leventgorgu.cryptoinfo.MainCoroutineRule
import com.leventgorgu.cryptoinfo.getOrAwaitValueTest
import com.leventgorgu.cryptoinfo.repo.FakeCryptoRepository
import com.leventgorgu.cryptoinfo.roomdb.CryptoDetailEntity
import com.leventgorgu.cryptoinfo.roomdb.CryptoEntity
import com.leventgorgu.cryptoinfo.roomdb.CryptoFavoriteEntity
import com.leventgorgu.cryptoinfo.ui.favorites.FavoritesViewModel
import com.leventgorgu.cryptoinfo.ui.home.HomeViewModel
import com.leventgorgu.cryptoinfo.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CryptoDetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var cryptoDetailViewModel: CryptoDetailViewModel

    @Before
    fun setup(){
        cryptoDetailViewModel = CryptoDetailViewModel(FakeCryptoRepository())
    }

    @Test
    fun getCryptoDetailsTest(){
        val cryptoEntityETH = CryptoEntity(1,"Ethereum","ETH",2,"2.000","")

        cryptoDetailViewModel.getCryptoDetails(cryptoEntityETH.id)
        val data = cryptoDetailViewModel.selectedCryptoDetail.getOrAwaitValueTest()
        assertThat(data.name).isEqualTo(cryptoEntityETH.name)
    }


    @Test
    fun getCryptoDetailEntityTest(){
        val cryptoEntityETH = CryptoEntity(1,"Ethereum","ETH",2,"2.000","")

        cryptoDetailViewModel.getCryptoDetailEntity(cryptoEntityETH.id)
        val data = cryptoDetailViewModel.cryptoDetailEntity.getOrAwaitValueTest()
        assertThat(data.cryptoSymbol).isEqualTo(cryptoEntityETH.symbol)
    }

    @Test
    fun insertSelectedCryptoDetailTest(){
        val cryptoDetailDOGE = CryptoDetailEntity(5,"","","Dogecoin","DOGE")

        cryptoDetailViewModel.insertSelectedCryptoDetail(cryptoDetailDOGE)
        cryptoDetailViewModel.getCryptoDetailEntity(5)
        val data = cryptoDetailViewModel.cryptoDetailEntity.getOrAwaitValueTest()
        assertThat(data.cryptoName).isEqualTo(cryptoDetailDOGE.cryptoName)

    }

    @Test
    fun insertCryptoFavoriteTest(){
        val cryptoDetailDOGE = CryptoFavoriteEntity(10,"Avalanche","AVAX",20)
        val status = cryptoDetailViewModel.insertCryptoFavorite(cryptoDetailDOGE)
        assertThat(status.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun deleteCryptoFavoriteTest(){
        val cryptoDetailDOGE = CryptoFavoriteEntity(10,"Avalanche","AVAX",20)
        val status = cryptoDetailViewModel.deleteCryptoFavorite(cryptoDetailDOGE)
        assertThat(status.status).isEqualTo(Status.SUCCESS)
    }

}