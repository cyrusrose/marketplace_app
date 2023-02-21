package com.it.access.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.it.access.data.repository.ItemRepository
import com.it.access.data.repository.items
import com.it.access.data.response.ItemResp
import com.it.access.domain.RepopulateUseCase
import com.it.access.domain.SearchState
import com.it.access.domain.SearchUseCase
import com.it.access.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val repopulateUseCase: RepopulateUseCase,
    private val searchUseCase: SearchUseCase
    ): ViewModel() {
    init {
        viewModelScope.launch {
            repopulateUseCase(items)
        }
    }

    private val _searchFlow = MutableStateFlow(SearchState())

    val itemFlow = searchUseCase(_searchFlow)
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

    data class SearchSheet(val isVisible: Boolean = false)
    data class DetailsSheet(val isVisible: Boolean = false, val item: ItemResp? = null)
}