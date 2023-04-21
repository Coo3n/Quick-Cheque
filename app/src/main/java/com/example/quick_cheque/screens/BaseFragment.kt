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
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.quick_cheque.R
import com.example.quick_cheque.screens.viewmodels.ToolBarViewModel
import kotlinx.coroutines.launch

open class BaseFragment : Fragment() {
    private val toolBarViewModel: ToolBarViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            toolBarViewModel.setActionBar(it)
        }
    }

    fun isEmptyLastQuerySearch(): Boolean = toolBarViewModel.isLastQuerySearchEmpty()

    fun setVisibleToolBar(isVisible: Boolean = true) {
        toolBarViewModel.setVisibleToolbar(isVisible)
    }

    fun setVisibleHomeButton(isVisible: Boolean = true) {
        toolBarViewModel.setVisibleHomeButton(isVisible)
    }

    fun setupToolBar(toolbarMenu: Int) {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(toolbarMenu, menu)
                val searchMenuItem = menu.findItem(R.id.search_button)
                val searchView = searchMenuItem?.actionView as SearchView

                viewLifecycleOwner.lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        toolBarViewModel.isExpandedSearchItem.collect { isExpanded ->
                            if (isExpanded) {
                                searchMenuItem.expandActionView()
                                searchView.requestFocus()
                            }
                        }
                    }
                }

                viewLifecycleOwner.lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        toolBarViewModel.lastQuerySearch.collect {
                            searchView.setQuery(toolBarViewModel.lastQuerySearch.value, false)
                        }
                    }
                }

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?) = handleText(query)
                    override fun onQueryTextChange(newText: String?) = handleText(newText)

                    private fun handleText(text: String?): Boolean {
                        text?.let { toolBarViewModel.setLastQuerySearch(it) }
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
                    R.id.search_button -> {
                        toolBarViewModel.setExpandedSearchItem(true)
                        return true
                    }
                    android.R.id.home -> {
                        toolBarViewModel.setExpandedSearchItem(false)
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