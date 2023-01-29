package com.leventgorgu.cryptoinfo.ui.home

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leventgorgu.cryptoinfo.repo.CryptoRepository
import com.leventgorgu.cryptoinfo.repo.CryptoRepositoryInterface
import com.leventgorgu.cryptoinfo.roomdb.CryptoEntity
import com.leventgorgu.cryptoinfo.util.APIResult
import com.leventgorgu.cryptoinfo.util.CustomSharedPreferences
import com.leventgorgu.cryptoinfo.util.Util.LIMIT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class HomeViewModel @Inject constructor(private val cryptoRepository: CryptoRepositoryInterface,application: Application) : ViewModel() {

    private var customSharedPreferences = CustomSharedPreferences(application.applicationContext)
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L

    private val _cryptos = MutableLiveData<APIResult<ArrayList<CryptoEntity>>>()
    val cryptos: LiveData<APIResult<ArrayList<CryptoEntity>>> = _cryptos

    private val _cryptosSearch = MutableLiveData<List<CryptoEntity>>()
    val cryptosSearch: LiveData<List<CryptoEntity>> = _cryptosSearch

    val cryptoListLiveDataFromSQl = getCryptosData()

    var cryptoFavoritesEntity = cryptoRepository.getAllFavoriteCryptos()

    private val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }

    fun refreshData(){
        val updateTime = customSharedPreferences.getTime()
        if (updateTime !=null && updateTime!=0L && (System.nanoTime()-updateTime) < refreshTime){
            getCryptosData()
        }else{
            getCryptosFromAPI(LIMIT)
        }
    }

    fun getCryptosFromAPI(limit:String){
        viewModelScope.launch(handler) {
            _cryptos.value = cryptoRepository.getCryptoDataFromAPI(limit)
        }
    }

    fun insertCryptos(cryptoEntityArray: ArrayList<CryptoEntity>){
        viewModelScope.launch(handler) {
            cryptoRepository.insertCrypto(cryptoEntityArray)
            customSharedPreferences.saveTime(System.nanoTime())
        }
    }

    private fun getCryptosData():LiveData<List<CryptoEntity>>{
        return cryptoRepository.getCryptos()
    }

    fun searchCrypto(newText: String?) {
        viewModelScope.launch(handler) {
            _cryptosSearch.value = cryptoRepository.searchCrypto(newText!!)
        }
    }
}