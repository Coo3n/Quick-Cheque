package com.example.quick_cheque.screens

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.quick_cheque.R

open class BaseFragment : Fragment() {
    fun updateToolbar(
        text: String = "", // title
        setDisplayHome: Boolean = true, // button back
        backgroundColor: Int = 2131034147, // Blue
        menu: Int = 0, // Menu exist
        isVisible: Boolean = true
    ): Toolbar {
        val mainActivity = (activity as AppCompatActivity)
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(setDisplayHome)

        val toolbar: Toolbar = mainActivity.findViewById(R.id.toolbar_quick_cheque)
        toolbar.title = text

        if (!isVisible) {
            toolbar.visibility = View.GONE
            return toolbar
        }
        toolbar.visibility = View.VISIBLE

        toolbar.menu.clear()
        if (menu != 0) {
            toolbar.inflateMenu(menu)
            val mSearchView = toolbar.menu.getItem(0).actionView as SearchView
            mSearchView.queryHint = "Поиск"
        }

        toolbar.setBackgroundColor(ContextCompat.getColor(mainActivity, backgroundColor))
        return toolbar
    }
}