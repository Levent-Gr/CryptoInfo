package com.leventgorgu.cryptoinfo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.leventgorgu.cryptoinfo.databinding.HomeCryptoRecyclerRowBinding
import com.leventgorgu.cryptoinfo.model.cryptos.Data
import com.leventgorgu.cryptoinfo.roomdb.CryptoEntity
import com.leventgorgu.cryptoinfo.roomdb.CryptoFavoriteEntity
import com.leventgorgu.cryptoinfo.ui.home.HomeFragmentDirections

class CryptoRecyclerAdapter(private val cryptosEntities:ArrayList<CryptoEntity>) : RecyclerView.Adapter<CryptoRecyclerAdapter.CryptoViewHolder>() {

    private var cryptoFavoriteEntityList = ArrayList<CryptoFavoriteEntity>()

    class CryptoViewHolder(val homeCryptoRecyclerRowBinding: HomeCryptoRecyclerRowBinding) : RecyclerView.ViewHolder(homeCryptoRecyclerRowBinding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val binding = HomeCryptoRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CryptoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.homeCryptoRecyclerRowBinding.crypto = cryptosEntities[position]
         var favorite = false
        for (favoritesCrypto in cryptoFavoriteEntityList){
            if (cryptosEntities[position].id ==favoritesCrypto.id ){
               favorite = true
            }
        }

        holder.itemView.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToCryptoDetailFragment()
            action.id = cryptosEntities[position].id
            action.symbol = cryptosEntities[position].symbol
            action.favorite = favorite
            Navigation.findNavController(holder.itemView).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return cryptosEntities.size
    }

    fun updateCryptosDataList(cryptoEntityList:List<CryptoEntity>){
        cryptosEntities.clear()
        cryptosEntities.addAll(cryptoEntityList)
        notifyDataSetChanged()
    }

    fun refreshCryptoFavorites(cryptoFavoriteEntity: List<CryptoFavoriteEntity>){
        cryptoFavoriteEntityList.clear()
        cryptoFavoriteEntityList.addAll(cryptoFavoriteEntity)
        notifyDataSetChanged()
    }
}