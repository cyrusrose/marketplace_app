package com.it.access.presentation.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.it.access.databinding.ActivityMainBinding
import com.it.access.presentation.MyViewModel
import com.it.access.presentation.MyViewModel.*
import com.it.access.util.DEBUG
import com.it.access.util.Resource
import com.it.access.util.collectLatestLifecycleFlow
import com.it.access.util.collectLifecycleFlow
import dagger.hilt.android.AndroidEntryPoint
import setSheetVisibility
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val ui by lazy {  ActivityMainBinding.inflate(layoutInflater) }
    private val vm by viewModels<MyViewModel>()
    private val descrAdapter by lazy { DescrAdapter() }
    private val detailsCallback = object: BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if(newState == BottomSheetBehavior.STATE_HIDDEN)
                vm.showDetailsSheet(false)
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit
    }
    private val searchCallback = object: BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if(newState == BottomSheetBehavior.STATE_HIDDEN)
                vm.showSearchSheet(false)
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit
    }

    override fun onStop() {
        super.onStop()
        BottomSheetBehavior.from(ui.details.sheet).removeBottomSheetCallback(detailsCallback)
        BottomSheetBehavior.from(ui.search.sheet).removeBottomSheetCallback(searchCallback)
    }

    override fun onStart() {
        super.onStart()
        BottomSheetBehavior.from(ui.details.sheet).addBottomSheetCallback(detailsCallback)
        BottomSheetBehavior.from(ui.search.sheet).addBottomSheetCallback(searchCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(ui.root)

        setUpDescriptionAdapter()
        setUpAdapter()
        setUpFab()
        setUpSheets()
    }

    private fun setUpFab() {
        ui.fab.setOnClickListener {
            vm.showDetailsSheet(false)
            vm.showSearchSheet(true)
        }
    }

    private fun setUpSheets() {
        collectLifecycleFlow(vm.detailsSheetFlow) {
            it.item?.let { item ->
                descrAdapter.submitList(item.toList())
            }
            ui.details.sheet.setSheetVisibility(it.isVisible)
        }

        collectLifecycleFlow(vm.searchSheetFlow) {
            ui.search.sheet.setSheetVisibility(it.isVisible)
        }
    }

    private fun setUpAdapter() {
        val adapter = MyAdapter()
        adapter.notifyListener = NotifyListener { item ->
            vm.showDetailsSheet(true, item)
            vm.showSearchSheet(false)
        }

        ui.content.rv.adapter = adapter

        collectLatestLifecycleFlow(vm.itemFlow) {
            if (it is Resource.Success)
                adapter.submitList(it.data)
        }
    }

    private fun setUpDescriptionAdapter() {
        ui.details.rv.adapter = descrAdapter
    }

}