package com.example.quick_cheque.screens

import android.content.ClipData
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.quick_cheque.R

open class BaseFragment : Fragment() {
    fun updateToolbar(
        text: String = "", // title
        setDisplayHome: Boolean = true, // button back
        setDisplaySearch: Boolean = true,
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

        val searchBtn = toolbar.menu.findItem(R.id.search_button)
        //searchBtn.isVisible = setDisplaySearch
        // TODO menuHost, provider

        toolbar.menu.clear()
        if (menu != 0) {
            toolbar.inflateMenu(menu)
            val mSearchView = toolbar.menu.getItem(0).actionView as SearchView
            mSearchView.queryHint = "Поиск"
        }

        toolbar.setBackgroundColor(ContextCompat.getColor(mainActivity, backgroundColor))
        return toolbar
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.updateToolbar()
    }
}