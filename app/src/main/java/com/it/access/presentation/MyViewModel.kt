package com.it.access.presentation

import android.util.Log
import android.view.View
import android.widget.CheckBox
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.it.access.data.response.ItemResp
import com.it.access.domain.GetUseCase
import com.it.access.domain.SearchState
import com.it.access.domain.SearchUseCase
import com.it.access.util.DEBUG
import com.it.access.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val getUseCase: GetUseCase
): ViewModel() {

    private val _searchFlow = MutableStateFlow(SearchState())
    private val _itemsFlow = getUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = emptyList()
        )

    val itemFlow = searchUseCase(_itemsFlow, _searchFlow)
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = Resource.Loading()
    )

    private val _searchSheetFlow = MutableStateFlow(SearchSheet())
    val searchSheetFlow = _searchSheetFlow.asStateFlow()

    private val _detailsSheetFlow = MutableStateFlow(DetailsSheet())
    val detailsSheetFlow = _detailsSheetFlow.asStateFlow()

    fun onValueChanged(event: SearchState) {
        _searchFlow.value = event
    }

    fun showSearchSheet(isVisible: Boolean) {
        _searchSheetFlow.value = SearchSheet(isVisible)
    }

    fun showDetailsSheet(isVisible: Boolean, item: ItemResp? = null) {
        _detailsSheetFlow.value = DetailsSheet(isVisible, item)
    }

    fun onCheckBoxClicked(view: Boolean, type: String, name: Types) {
//        if (view is CheckBox) {
            Log.d(DEBUG, "$type $name ${view}")
//        }
    }

    data class SearchSheet(val isVisible: Boolean = false)
    data class DetailsSheet(val isVisible: Boolean = false, val item: ItemResp? = null)


}

enum class Types {
    TYPE,
    SURFACE,
    LOCATION,
    POWER
}