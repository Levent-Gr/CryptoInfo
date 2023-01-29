package com.leventgorgu.cryptoinfo.ui.cryptodetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leventgorgu.cryptoinfo.model.cryptoInfo.CryptoInfo
import com.leventgorgu.cryptoinfo.model.cryptos.Cryptos
import com.leventgorgu.cryptoinfo.repo.CryptoRepository
import com.leventgorgu.cryptoinfo.repo.CryptoRepositoryInterface
import com.leventgorgu.cryptoinfo.roomdb.CryptoDetailEntity
import com.leventgorgu.cryptoinfo.roomdb.CryptoEntity
import com.leventgorgu.cryptoinfo.roomdb.CryptoFavoriteEntity
import com.leventgorgu.cryptoinfo.util.APIResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(private val cryptoRepository: CryptoRepositoryInterface) : ViewModel() {

    private val _cryptoInfo = MutableLiveData<APIResult<CryptoInfo>>()
    val cryptoInfo: LiveData<APIResult<CryptoInfo>> = _cryptoInfo

    private val _cryptoDetailEntity = MutableLiveData<CryptoDetailEntity>()
    val cryptoDetailEntity: LiveData<CryptoDetailEntity> = _cryptoDetailEntity

    private val _selectedCryptoDetail = MutableLiveData<CryptoEntity>()
    val selectedCryptoDetail: LiveData<CryptoEntity> = _selectedCryptoDetail

    private val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }

    fun getCryptoDetails(cryptoId:Int){
        viewModelScope.launch(handler) {
            val data = cryptoRepository.getCryptoWithId(cryptoId)
            data.let {
                _selectedCryptoDetail.value = it
            }
        }
    }

    fun getCryptoDetailEntity(cryptoDetailId:Int){
        viewModelScope.launch(handler) {
            _cryptoDetailEntity.value = cryptoRepository.getCryptoDetailEntity(cryptoDetailId)
        }
    }

    fun getCryptoInfoFromAPI(symbol:String){
        viewModelScope.launch(handler) {
            _cryptoInfo.value = cryptoRepository.getCryptoInfoDataFromAPI(symbol)
        }
    }

    fun insertSelectedCryptoDetail(cryptoDetailEntity: CryptoDetailEntity){
        viewModelScope.launch(handler) {
            cryptoRepository.insertCryptoDetail(cryptoDetailEntity)
        }
    }

    fun insertCryptoFavorite(cryptoFavoriteEntity: CryptoFavoriteEntity):APIResult<CryptoFavoriteEntity>{

        var result : APIResult<CryptoFavoriteEntity> = APIResult.loading(cryptoFavoriteEntity)
        if (cryptoFavoriteEntity.id!=0){
            viewModelScope.launch(handler) {
                result = APIResult.success(cryptoFavoriteEntity)
                cryptoRepository.insertCryptoFavorite(cryptoFavoriteEntity)
            }
        }else{
            result = APIResult.error("error",null)
        }
        return result
    }

    fun deleteCryptoFavorite(cryptoFavoriteEntity: CryptoFavoriteEntity):APIResult<CryptoFavoriteEntity>{

        var result : APIResult<CryptoFavoriteEntity> = APIResult.loading(cryptoFavoriteEntity)
        if (cryptoFavoriteEntity.id!=0){
            viewModelScope.launch(handler) {
                result = APIResult.success(cryptoFavoriteEntity)
                cryptoRepository.deleteCryptoFavorite(cryptoFavoriteEntity)
            }
        }else{
            result = APIResult.error("error",null)
        }
        return result
    }
}