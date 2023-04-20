package com.example.quick_cheque.screens.main_pages_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.FragmentMainScreenBinding
import com.example.quick_cheque.screens.BaseFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yandex.mobile.ads.banner.AdSize
import com.yandex.mobile.ads.common.AdRequest

class MainScreenFragment : BaseFragment() {
    private lateinit var binding: FragmentMainScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.rectangle1.setOnClickListener {
            findNavController().navigate(R.id.action_mainScreenFragment_to_choiceRoomFragment)
        }

        activity?.parent?.findViewById<BottomNavigationView>(R.id.main_bottom_nav)?.visibility =
            View.INVISIBLE
    }

    //secret function Tsss...
    private fun initYandexADS() {
        val banner = binding.ads
        banner.setAdUnitId("demo-banner-yandex")
        banner.setAdSize(AdSize.FULL_SCREEN)
        val adRequest = AdRequest.Builder().build()
        banner.loadAd(adRequest)
    }
}