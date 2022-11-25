package com.leventgorgu.cryptoinfo.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leventgorgu.cryptoinfo.repo.CryptoRepository
import com.leventgorgu.cryptoinfo.repo.CryptoRepositoryInterface
import com.leventgorgu.cryptoinfo.roomdb.CryptoEntity
import com.leventgorgu.cryptoinfo.roomdb.CryptoFavoriteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val cryptoRepository: CryptoRepositoryInterface): ViewModel() {

    val cryptoFavoritesEntity = cryptoRepository.getAllFavoriteCryptos()
}