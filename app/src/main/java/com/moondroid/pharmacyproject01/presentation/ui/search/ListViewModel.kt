package com.moondroid.pharmacyproject01.presentation.ui.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.moondroid.pharmacyproject01.common.MutableEventFlow
import com.moondroid.pharmacyproject01.common.asEventFlow
import com.moondroid.pharmacyproject01.data.model.AddressItem
import com.moondroid.pharmacyproject01.domain.Repository
import com.moondroid.pharmacyproject01.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: Repository,
) : BaseViewModel() {
    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    fun getListByAddress(city: String, district: String) {
        viewModelScope.launch {
            repository.getListByAddress(city, district).cachedIn(viewModelScope).collectLatest {
                _eventFlow.emit(Event.Update(it))
            }
        }
    }

    sealed interface Event {
        data class Update(val data: PagingData<AddressItem>) : Event
    }
}