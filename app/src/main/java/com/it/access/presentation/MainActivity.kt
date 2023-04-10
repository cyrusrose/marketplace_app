package com.it.access.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.it.access.R
import com.it.access.databinding.ActivityMainBinding
import com.it.access.databinding.SearchSheetBinding
import com.it.access.presentation.MyViewModel.*
import com.it.access.util.*
import dagger.hilt.android.AndroidEntryPoint
import setSheetVisibility

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val ui by lazy {  ActivityMainBinding.inflate(layoutInflater) }
    private val vm by viewModels<MyViewModel>()
    private val descrAdapter by lazy { DescrAdapter() }
    private val detailsCallback = object: BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if(newState == BottomSheetBehavior.STATE_HIDDEN)
                vm.onEvent(UiEvent.DetailsSheet(false))
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit
    }
    private val searchCallback = object: BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if(newState == BottomSheetBehavior.STATE_HIDDEN)
                vm.onEvent(UiEvent.SearchSheet(false))
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
        ui.search.vm = vm
        ui.search.lifecycleOwner = this
        setContentView(ui.root)

        setUpDescriptionAdapter()
        setUpAdapter()
        setUpFab()
        setUpSheets()
        setUpSearch()
    }


    private fun setUpSearch() {
        collectLatestLifecycleFlow(vm.isCleaning) {
            if (it) Log.d(DEBUG, "clean")
            if (it) with(ui.search) {
                cleanSearch()
            }
        }

        collectLatestLifecycleFlow(vm.isSearching) {
            ui.isSearching.visibility = if (it) View.VISIBLE else View.GONE
            ui.content.rv.visibility = if (it) View.GONE else View.VISIBLE
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setUpFab() {
        collectLatestLifecycleFlow(vm.fabFlow) {
            ui.fab.icon = when(it) {
                CleanState.FILTER -> resources.getDrawable(R.drawable.ic_filter, applicationContext.theme)
                CleanState.COLLAPSE -> resources.getDrawable(R.drawable.ic_up, applicationContext.theme)
                else -> resources.getDrawable(R.drawable.ic_clean, applicationContext.theme)
            }
        }

        ui.fab.setOnClickListener {
            vm.onEvent(UiEvent.FabEvent)
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
            vm.onEvent(UiEvent.DetailsSheet(true, item))
            vm.onEvent(UiEvent.SearchSheet(false))
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

fun SearchSheetBinding.cleanSearch() {
    whiteColorParam.isChecked = false
    blackColorParam.isChecked = false
    ptcElementParam.isChecked = false
    fiberElementParam.isChecked = false
    wireElementParam.isChecked = false
    halogenElementParam.isChecked = false
    remoteControl.isChecked = false
    heatingProtection.isChecked = false
    rolloverProtection.isChecked = false
    fanTypeParam.isChecked = false
    oilTypeParam.isChecked = false
    tubeTypeParam.isChecked = false
    floorLocationParam.isChecked = false
    wallLocationParam.isChecked = false
    smallSurfaceParam.isChecked = false
    mediumSurfaceParam.isChecked = false
    smallPowerParam.isChecked = false
    mediumPowerParam.isChecked = false
    largePowerParam.isChecked = false
    extraPowerParam.isChecked = false
    twoSpeedParam.isChecked = false
    threeSpeedParam.isChecked = false
    fourSpeedParam.isChecked = false
    priceFrom.editText?.setText("")
    priceTo.editText?.setText("")
    lengthFrom.editText?.setText("")
    lengthTo.editText?.setText("")
    widthFrom.editText?.setText("")
    widthTo.editText?.setText("")
    heightFrom.editText?.setText("")
    heightTo.editText?.setText("")
    weightFrom.editText?.setText("")
    weightTo.editText?.setText("")
}