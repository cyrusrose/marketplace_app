package com.it.access.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.it.access.data.repository.ItemRepository
import com.it.access.data.repository.items
import com.it.access.data.response.ItemResp
import com.it.access.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val rep: ItemRepository): ViewModel() {
    init {
        viewModelScope.launch {
            rep.deleteAll(items)
            rep.insertAll(items)
        }
    }
    

    val stateFlow: StateFlow<Resource<List<ItemResp>>> = rep.getAll().mapLatest {
        Resource.Success(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = Resource.Loading()
    )

}