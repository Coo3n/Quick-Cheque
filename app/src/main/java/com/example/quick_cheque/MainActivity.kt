package com.example.quick_cheque

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quick_cheque.databinding.ActivityMainBinding
import com.example.quick_cheque.screens.ChoiceChequeFragment


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}