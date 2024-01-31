package com.moondroid.pharmacyproject01.presentation.ui.home

import androidx.lifecycle.viewModelScope
import com.moondroid.pharmacyproject01.common.MutableEventFlow
import com.moondroid.pharmacyproject01.common.asEventFlow
import com.moondroid.pharmacyproject01.common.debug
import com.moondroid.pharmacyproject01.data.model.LocationItem
import com.moondroid.pharmacyproject01.domain.Repository
import com.moondroid.pharmacyproject01.domain.model.onError
import com.moondroid.pharmacyproject01.domain.model.onFail
import com.moondroid.pharmacyproject01.domain.model.onSuccess
import com.moondroid.pharmacyproject01.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
) : BaseViewModel() {

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    fun get(numOfRows: Int, lng: Double, lat: Double) {
        viewModelScope.launch {
            repository.getListByLocation(numOfRows, lng, lat).collect {
                loading(false)
                it.onSuccess { list ->
                    event(Event.Update(list))
                }.onFail { m ->
                    fail(m)
                }.onError { t ->
                    error(t)
                }
            }
        }
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed interface Event {
        data class Update(val list : List<LocationItem>): Event
    }
}