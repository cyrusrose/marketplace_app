package com.it.access.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.updateLayoutParams
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.it.access.R
import com.it.access.data.response.ItemResp
import com.it.access.databinding.ActivityMainBinding
import com.it.access.presentation.MyViewModel
import com.it.access.presentation.MyViewModel.ScreenEvent
import com.it.access.util.DEBUG
import com.it.access.util.Resource
import com.it.access.util.collectLatestLifecycleFlow
import com.it.access.util.collectLifecycleFlow
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.delay

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
        setUpDescriptionAdapter()
        setUpAdapter()
        setUpFabs()
    }

    private fun setUpFabs() {
        ui.fab.setOnClickListener {

        }
    }

    private fun setUpSheets() {
        val behavior = BottomSheetBehavior.from(ui.details.standardBottomSheet)
        behavior.state = BottomSheetBehavior.STATE_HIDDEN

        collectLifecycleFlow(vm.sharedFlow) {
            when(it) {
                is ScreenEvent.DetailsSheet -> {
                    val behavior = BottomSheetBehavior.from(ui.details.standardBottomSheet)
                    behavior.state = if (it.isVisible)
                        BottomSheetBehavior.STATE_COLLAPSED
                        else BottomSheetBehavior.STATE_HIDDEN
                }
                is ScreenEvent.SearchSheet -> {}
            }

        }
    }

    private fun setUpAdapter() {
        val adapter = MyAdapter()
        adapter.notifyListener = NotifyListener { item ->
            descrAdapter.submitList(item.toList())

            vm.showDetailsSheet(true)
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