package com.leventgorgu.cryptoinfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.leventgorgu.cryptoinfo.databinding.FavoritesCryptoRecyclerRowBinding
import com.leventgorgu.cryptoinfo.databinding.HomeCryptoRecyclerRowBinding
import com.leventgorgu.cryptoinfo.roomdb.CryptoEntity
import com.leventgorgu.cryptoinfo.roomdb.CryptoFavoriteEntity
import com.leventgorgu.cryptoinfo.ui.favorites.FavoritesFragmentDirections
import com.leventgorgu.cryptoinfo.ui.home.HomeFragmentDirections

class CryptoFavoritesRecyclerAdapter(private val cryptosEntities:ArrayList<CryptoFavoriteEntity>):RecyclerView.Adapter<CryptoFavoritesRecyclerAdapter.CryptoFavoritesViewHolder>() {

    class CryptoFavoritesViewHolder(val favoritesCryptoRecyclerRowBinding: FavoritesCryptoRecyclerRowBinding):RecyclerView.ViewHolder(favoritesCryptoRecyclerRowBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoFavoritesViewHolder {
        val binding = FavoritesCryptoRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CryptoFavoritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoFavoritesViewHolder, position: Int) {
        holder.favoritesCryptoRecyclerRowBinding.cryptoFavorite = cryptosEntities[position]

        holder.itemView.setOnClickListener {
            val action = FavoritesFragmentDirections.actionNavigationFavoritesToCryptoDetailFragment()
            action.id = cryptosEntities[position].id
            action.symbol = cryptosEntities[position].symbol
            action.favorite = true
            Navigation.findNavController(holder.itemView).navigate(action)
        }
    }

    fun updateCryptosFavoritesDataList(cryptoEntityList:List<CryptoFavoriteEntity>){
        cryptosEntities.clear()
        cryptosEntities.addAll(cryptoEntityList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return cryptosEntities.size
    }
}