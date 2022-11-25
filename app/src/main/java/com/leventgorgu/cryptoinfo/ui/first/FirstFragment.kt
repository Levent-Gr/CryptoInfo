package com.leventgorgu.cryptoinfo.ui.first

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.leventgorgu.cryptoinfo.R
import com.leventgorgu.cryptoinfo.databinding.FragmentFirstBinding
import com.leventgorgu.cryptoinfo.databinding.FragmentHomeBinding
import com.leventgorgu.cryptoinfo.ui.favorites.FavoritesFragmentDirections


class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToNavigationHome()
            Navigation.findNavController(it).navigate(action)
        }
    }

}