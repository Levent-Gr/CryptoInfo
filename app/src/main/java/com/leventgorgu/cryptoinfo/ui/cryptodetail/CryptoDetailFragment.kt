package com.leventgorgu.cryptoinfo.ui.cryptodetail


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.data.*
import com.google.gson.Gson
import com.leventgorgu.cryptoinfo.R
import com.leventgorgu.cryptoinfo.databinding.FragmentCryptoDetailBinding
import com.leventgorgu.cryptoinfo.model.cryptoInfo.CryptoDetail
import com.leventgorgu.cryptoinfo.model.cryptoInfo.CryptoInfo
import com.leventgorgu.cryptoinfo.roomdb.CryptoDetailEntity
import com.leventgorgu.cryptoinfo.roomdb.CryptoEntity
import com.leventgorgu.cryptoinfo.roomdb.CryptoFavoriteEntity
import com.leventgorgu.cryptoinfo.util.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.json.JSONArray
import org.json.JSONObject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CryptoDetailFragment : Fragment() {

    private var _binding: FragmentCryptoDetailBinding? = null
    private val binding get() = _binding!!
    private val cryptoDetailViewModel: CryptoDetailViewModel by viewModels()
    private var cryptoId :Int  = 0
    private var cryptoSymbol:String = ""
    private var cryptofavorite:Boolean = false
    private lateinit var cryptoEntity : CryptoEntity



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cryptoId = CryptoDetailFragmentArgs.fromBundle(it).id
            cryptoSymbol = CryptoDetailFragmentArgs.fromBundle(it).symbol
            cryptofavorite = CryptoDetailFragmentArgs.fromBundle(it).favorite
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCryptoDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cryptoDetailViewModel.getCryptoDetails(cryptoId)
        cryptoDetailViewModel.getCryptoDetailEntity(cryptoId)
        observeSubscribe()


        if (cryptofavorite){
            binding.saveCryptoButton.text = getString(R.string.remove_from_favorites)
        }else{
            binding.saveCryptoButton.text = getString(R.string.save_to_favorites)
        }


        binding.saveCryptoButton.setOnClickListener {
            saveAndRemoveCrypto()
        }
    }



    private fun saveAndRemoveCrypto() {
        val id = cryptoEntity.id
        val name = cryptoEntity.name
        val symbol = cryptoEntity.symbol
        val rank = cryptoEntity.cmcRank

        val cryptoFavorite = CryptoFavoriteEntity(id,name,symbol,rank)

        if (cryptofavorite){
            val result = cryptoDetailViewModel.deleteCryptoFavorite(cryptoFavorite)
            when(result.status){
                Status.SUCCESS -> {
                    Toast.makeText(context,"Crypto removed from favorites",Toast.LENGTH_LONG).show()
                    binding.saveCryptoButton.text = getString(R.string.save_to_favorites)
                    cryptofavorite = false
                }
                Status.ERROR ->{
                    Toast.makeText(context,result.message,Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }else{
            val result = cryptoDetailViewModel.insertCryptoFavorite(cryptoFavorite)
            when(result.status){
                Status.SUCCESS -> {
                    Toast.makeText(context,"Crypto saved to favorites",Toast.LENGTH_LONG).show()
                    binding.saveCryptoButton.text = getString(R.string.remove_from_favorites)
                    cryptofavorite = true
                }
                Status.ERROR -> {
                    Toast.makeText(context,result.message,Toast.LENGTH_LONG).show()
                }
                else -> {}
            }

        }
    }

    private fun observeSubscribe(){
        cryptoDetailViewModel.selectedCryptoDetail.observe(viewLifecycleOwner, Observer {
            it.let {
                binding.selectedCrypto = it
                cryptoEntity = it
                if (cryptofavorite){
                    binding.saveCryptoButton.text = getString(R.string.remove_from_favorites)
                }else{
                    binding.saveCryptoButton.text = getString(R.string.save_to_favorites)
                }
            }
        })
        cryptoDetailViewModel.cryptoDetailEntity.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                binding.cryptoDetailEntity = it
            }else{
                cryptoDetailViewModel.getCryptoInfoFromAPI(cryptoSymbol)
            }
        })
        cryptoDetailViewModel.cryptoInfo.observe(viewLifecycleOwner, Observer {  cryptoInfo ->
            when(cryptoInfo.status){
                Status.SUCCESS ->{
                    parseData(cryptoInfo.data!!)
                    binding.progressBar.visibility = View.GONE
                }
                Status.LOADING ->{
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR ->{
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(),cryptoInfo.message ?: "Error", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun parseData(it: CryptoInfo){
        val gson = Gson()
        val json = gson.toJson(it?.data)
        val jsonObject = JSONObject(json)
        val jsonArray = jsonObject[cryptoSymbol] as JSONArray

        val cryptoData = gson.fromJson(jsonArray.getJSONObject(0).toString(), CryptoDetail::class.java)

        val cryptoId = cryptoData.id
        val cryptoName = cryptoData.name
        val cryptoCategory = cryptoData.category
        val cryptoDescription = cryptoData.description
        val cryptoSymbol = cryptoData.symbol

        binding.cryptoDescription.text = cryptoDescription
        binding.cryptoCategory.text = cryptoCategory

        val cryptoDetailEntity =CryptoDetailEntity(cryptoId,cryptoCategory,cryptoDescription,cryptoName,cryptoSymbol)

        cryptoDetailViewModel.insertSelectedCryptoDetail(cryptoDetailEntity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}