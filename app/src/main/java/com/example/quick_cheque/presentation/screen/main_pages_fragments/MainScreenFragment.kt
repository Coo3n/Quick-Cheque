package com.example.quick_cheque.presentation.screen.main_pages_fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.FragmentMainScreenBinding
import com.example.quick_cheque.presentation.screen.BaseFragment
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
        setVisibleHomeButton(false)
        setVisibleToolBar()
//        requireContext().contentResolver.
//        val pickImage = registerForActivityResult(
//            ActivityResultContracts.GetContent()
//        ) { uri: Uri? ->
//            uri?.port?.let { binding.tempImage.setImageResource(it) }
//        }
//
//        binding.rectangle1.setOnClickListener { pickImage.launch("image/*") }



        activity?.parent?.findViewById<BottomNavigationView>(
            R.id.main_bottom_nav
        )?.visibility = View.INVISIBLE
    }

    private fun takePicture() {

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