package com.example.quick_cheque.screens

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.quick_cheque.R

open class BaseFragment : Fragment() {
    fun updateToolbar(
        text: String = "", // title
        setDisplayHome: Boolean = true, // button back
        backgroundColor: Int = 2131034147, // Blue
        menu: Int = 0 // Menu exist
    ) {
        val mainActivity = (activity as AppCompatActivity)
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(setDisplayHome)

        val toolbar: Toolbar = mainActivity.findViewById(R.id.toolbar_quick_cheque)
        toolbar.title = text

        toolbar.menu.clear()
        if (menu != 0) {
            toolbar.inflateMenu(menu)
        }

        toolbar.setBackgroundColor(ContextCompat.getColor(mainActivity, backgroundColor))
    }
}