package com.it.access.presentation

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.it.access.data.response.ItemResp
import com.it.access.domain.*
import com.it.access.domain.use_cases.*
import com.it.access.util.DEBUG
import com.it.access.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class MyViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val getUseCase: GetUseCase,
    private val filterCheckBoxUseCase: FilterCheckBoxUseCase,
    private val filterTextUseCase: FilterTextUseCase,
    private val isEmptyUseCase: IsEmptyUseCase
): ViewModel() {
    private val _searchFlow = MutableStateFlow(SearchState())

    private val _isCleaning = MutableStateFlow(false)
    val isCleaning = _isCleaning.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _itemsFlow = getUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = emptyList()
        )

    val itemFlow = searchUseCase(
        itemsFlow = _itemsFlow,
        eventFlow = _searchFlow
            .debounce(1000.milliseconds)
            .onEach {
            _isSearching.update { true }
        }
    )
    .onEach {
        _isSearching.update { false }
    }
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = Resource.Loading()
    )

    private val _searchSheetFlow = MutableStateFlow(UiEvent.SearchSheet())
    val searchSheetFlow = _searchSheetFlow.asStateFlow()

    private val _detailsSheetFlow = MutableStateFlow(UiEvent.DetailsSheet())
    val detailsSheetFlow = _detailsSheetFlow.asStateFlow()

    private val _fabFlow = MutableStateFlow(CleanState.FILTER)
    val fabFlow = _fabFlow.asStateFlow()

    private val _uiFlow = MutableSharedFlow<UiEvent>()

    init {
        combine(_searchFlow, _searchSheetFlow, _detailsSheetFlow) { event, searchState, detailsState ->
            val isEmpty = isEmptyUseCase(event)

            _fabFlow.update {
                if(isEmpty || detailsState.isVisible)
                    CleanState.FILTER
                else if (!searchState.isVisible && !detailsState.isVisible)
                    CleanState.COLLAPSE
                else
                    CleanState.CLEAN
            }

            if(isEmpty)
                _isCleaning.update { false }
        }
        .launchIn(viewModelScope)

        _uiFlow.onEach { event ->
            when (event) {
                is UiEvent.TextChangedEvent ->
                    _searchFlow.update {
                        filterTextUseCase(
                            item = it,
                            param = event.param,
                            type = event.type
                        )
                    }
                is UiEvent.CheckBoxChangedEvent ->
                    _searchFlow.update {
                        filterCheckBoxUseCase(
                            item = it,
                            type = event.type,
                            isChecked = event.isChecked,
                            value = event.value
                        )
                    }
                is UiEvent.FabEvent -> {
                    if (_fabFlow.value in arrayOf(CleanState.FILTER, CleanState.COLLAPSE)) {
                        _detailsSheetFlow.update { UiEvent.DetailsSheet(false) }
                        _searchSheetFlow.update { UiEvent.SearchSheet(true) }
                    } else {
                        onSearchCleaned()
                    }
                }
                is UiEvent.SearchSheet -> {
                    _searchSheetFlow.update { event }
                }
                is UiEvent.DetailsSheet -> {
                    _detailsSheetFlow.update { event }
                }
            }
            Log.d(DEBUG, "why")
        }
        .launchIn(viewModelScope)
    }

    fun onEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiFlow.emit(event)
        }
    }

    private fun onSearchCleaned() {
        _isCleaning.update { true }
        _searchFlow.update { SearchState() }
    }
}

enum class CleanState {
    FILTER, CLEAN, COLLAPSE
}


sealed class UiEvent {
    class SearchSheet(val isVisible: Boolean = false): UiEvent()
    class DetailsSheet(val isVisible: Boolean = false, val item: ItemResp? = null): UiEvent()

    object FabEvent: UiEvent()
    class TextChangedEvent(val param: String, val type: Types): UiEvent()
    class CheckBoxChangedEvent(val isChecked: Boolean, val value: String, val type: Types): UiEvent()
}

enum class Types {
    TYPE,
    SURFACE,
    LOCATION,
    POWER,
    ELEMENT,
    SPEED,
    COLOR,
    PRICE_FROM,
    PRICE_TO,
    LENGTH_FROM,
    LENGTH_TO,
    WIDTH_FROM,
    WIDTH_TO,
    HEIGHT_FROM,
    HEIGHT_TO,
    WEIGHT_FROM,
    WEIGHT_TO,
    FUNCTIONS
}
