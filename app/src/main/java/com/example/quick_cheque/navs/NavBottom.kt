package com.example.quick_cheque.navs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.quick_cheque.R
import com.example.quick_cheque.screens.ChoiceChequeFragment
import com.example.quick_cheque.screens.JoinScreenFragment
import com.example.quick_cheque.screens.ProfileScreenFragment

class NavBottom : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view?.findViewById<Button>(R.id.nav_bottom_profile)?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.screen_frame, ProfileScreenFragment())
                .commit()
        }

        view?.findViewById<Button>(R.id.nav_bottom_create)?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.screen_frame, ChoiceChequeFragment())
                .commit()
        }

        view?.findViewById<Button>(R.id.nav_bottom_join)?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.screen_frame, JoinScreenFragment())
                .commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav_bottom, container, false)
    }
}