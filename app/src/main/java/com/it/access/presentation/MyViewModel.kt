package com.it.access.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.it.access.data.repository.ItemRepository
import com.it.access.data.repository.items
import com.it.access.data.response.Item
import com.it.access.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(val rep: ItemRepository): ViewModel() {
    init {
        viewModelScope.launch {
            rep.deleteAll(items)
            rep.insertAll(items)
        }
    }

    private val _stateFlow = MutableStateFlow<Response<List<Item>>>(Response.Loading())
    val stateFlow = _stateFlow.asStateFlow()

    fun getAll() {
        viewModelScope.launch {
            _stateFlow.value = rep.getAll()
        }

    }
}