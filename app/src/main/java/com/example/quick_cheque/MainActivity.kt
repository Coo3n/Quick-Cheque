package com.example.quick_cheque

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i("MyTag", "onCreateMain")

        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, ReceiptWindowFragment())
            .commit()

    }

}