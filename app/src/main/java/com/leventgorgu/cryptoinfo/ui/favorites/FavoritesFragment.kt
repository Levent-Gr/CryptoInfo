package com.leventgorgu.cryptoinfo.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.leventgorgu.cryptoinfo.adapter.CryptoFavoritesRecyclerAdapter
import com.leventgorgu.cryptoinfo.databinding.FragmentFavoritesBinding
import com.leventgorgu.cryptoinfo.roomdb.CryptoEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val favoritesViewModel:FavoritesViewModel by viewModels()
    private var cryptoFavoritesRecyclerAdapter = CryptoFavoritesRecyclerAdapter(arrayListOf())


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObserve()
    }

    override fun onResume() {
        super.onResume()
        favoritesViewModel.cryptoFavoritesEntity

        binding.recyclerCryptoFavorites.layoutManager = LinearLayoutManager(context)
        binding.recyclerCryptoFavorites.adapter = cryptoFavoritesRecyclerAdapter
    }

    private fun subscribeObserve(){

        favoritesViewModel.cryptoFavoritesEntity.observe(viewLifecycleOwner, Observer {
            it.let {
                val cryptoList = it
                cryptoFavoritesRecyclerAdapter.updateCryptosFavoritesDataList(ArrayList(cryptoList))
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}