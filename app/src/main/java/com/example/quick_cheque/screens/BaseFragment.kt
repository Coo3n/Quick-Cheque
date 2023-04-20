package com.example.quick_cheque.screens

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.quick_cheque.R
import com.example.quick_cheque.screens.viewmodels.ChoiceChequeViewModel

open class BaseFragment : Fragment() {
    private val viewModel: ChoiceChequeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.let { viewModel.setActionBar(it) }
    }

    fun setVisibleToolBar(isVisible: Boolean = true) {
        if (isVisible) {
            viewModel.actionBar.value?.show()
        } else {
            viewModel.actionBar.value?.hide()
        }
    }

    fun setVisibleHomeButton(isVisible: Boolean = true) {
        viewModel.actionBar.value?.setDisplayHomeAsUpEnabled(isVisible)
    }

    fun setupToolBar(toolbarMenu: Int) {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(toolbarMenu, menu)
                val mSearchView = menu.findItem(R.id.search_button)?.actionView as SearchView

                mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?) = handleText(query)
                    override fun onQueryTextChange(newText: String?) = handleText(newText)

                    private fun handleText(text: String?): Boolean {
                        text?.let { filterSearchingItems(it) }
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.add_button -> {
                        handleAddButtonClicked()
                        return true
                    }
                    android.R.id.home -> {
                        findNavController().navigateUp()
                    }
                    else -> true
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.STARTED)
    }

    protected open fun handleAddButtonClicked() {}
    protected open fun filterSearchingItems(query: String) {}
}