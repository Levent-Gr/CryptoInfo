package com.leventgorgu.cryptoinfo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leventgorgu.cryptoinfo.repo.CryptoRepository
import com.leventgorgu.cryptoinfo.repo.CryptoRepositoryInterface
import com.leventgorgu.cryptoinfo.roomdb.CryptoEntity
import com.leventgorgu.cryptoinfo.util.APIResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val cryptoRepository: CryptoRepositoryInterface ) : ViewModel() {

    private val _cryptos = MutableLiveData<APIResult<ArrayList<CryptoEntity>>>()
    val cryptos: LiveData<APIResult<ArrayList<CryptoEntity>>> = _cryptos

    private val _cryptosSearch = MutableLiveData<List<CryptoEntity>>()
    val cryptosSearch: LiveData<List<CryptoEntity>> = _cryptosSearch

    val cryptoListLiveDataFromSQl = getCryptosData()

    var cryptoFavoritesEntity = cryptoRepository.getAllFavoriteCryptos()


    fun getCryptosFromAPI(limit:String){
        viewModelScope.launch {
            _cryptos.value = cryptoRepository.getCryptoDataFromAPI(limit)
        }
    }

    fun insertCryptos(cryptoEntityArray: ArrayList<CryptoEntity>){
        viewModelScope.launch {
            cryptoRepository.insertCrypto(cryptoEntityArray)
        }
    }

    fun getCryptosData():LiveData<List<CryptoEntity>>{
        return cryptoRepository.getCryptos()
    }

    fun searchCrypto(newText: String?) {
        viewModelScope.launch {
            _cryptosSearch.value = cryptoRepository.searchCrypto(newText!!)
        }
    }

}