package com.it.access.presentation.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.it.access.databinding.ActivityMainBinding
import com.it.access.presentation.MyViewModel
import com.it.access.presentation.MyViewModel.ScreenEvent
import com.it.access.util.DEBUG
import com.it.access.util.Resource
import com.it.access.util.collectLatestLifecycleFlow
import com.it.access.util.collectLifecycleFlow
import dagger.hilt.android.AndroidEntryPoint
import setSheetVisibility

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val ui by lazy {  ActivityMainBinding.inflate(layoutInflater) }
    private val vm by viewModels<MyViewModel>()
    private val descrAdapter by lazy { DescrAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(ui.root)

        setUpSheets()
        setUpAdapters()
        setUpFab()
    }

    private fun setUpAdapters() {
        setUpDescriptionAdapter()
        setUpAdapter()
    }

    private fun setUpFab() {
        ui.fab.setOnClickListener {
            vm.showDetailsSheet(false)
            vm.showSearchSheet(true)
        }
    }

    private fun setUpSheets() {
        collectLifecycleFlow(vm.sharedFlow) {
            when(it) {
                is ScreenEvent.DetailsSheet ->
                    ui.details.sheet.setSheetVisibility(it.isVisible)
                is ScreenEvent.SearchSheet ->
                    ui.search.sheet.setSheetVisibility(it.isVisible)
            }
        }

        lifecycleScope.launchWhenStarted {
            vm.showDetailsSheet(false)
            vm.showSearchSheet(false)
        }
    }

    private fun setUpAdapter() {
        val adapter = MyAdapter()
        adapter.notifyListener = NotifyListener { item ->
            descrAdapter.submitList(item.toList())

            vm.showDetailsSheet(true)
            vm.showSearchSheet(false)
        }

        ui.content.rv.adapter = adapter

        collectLatestLifecycleFlow(vm.stateFlow) {
            if (it is Resource.Success)
                adapter.submitList(it.data)
        }
    }

    private fun setUpDescriptionAdapter() {
        ui.details.rv.adapter = descrAdapter
    }

}