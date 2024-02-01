package com.moondroid.pharmacyproject01.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moondroid.pharmacyproject01.common.debug
import com.moondroid.pharmacyproject01.data.model.DetailItem
import com.moondroid.pharmacyproject01.domain.Repository
import com.moondroid.pharmacyproject01.domain.model.onError
import com.moondroid.pharmacyproject01.domain.model.onFail
import com.moondroid.pharmacyproject01.domain.model.onSuccess
import com.moondroid.pharmacyproject01.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: Repository,
) : BaseViewModel() {
    private val _detailItem = MutableLiveData<DetailItem>()
    val detailItem: LiveData<DetailItem> get() = _detailItem

    fun getDetail(hpid: String) {
        loading(true)
        viewModelScope.launch {
            repository.getDetail(hpid).collect { result ->
                loading(false)
                result.onSuccess {
                    debug("success $it")
                    _detailItem.value = it
                }.onFail {
                    debug(it)
                    fail(it, ::finish)
                }.onError {
                    debug(it.toString())
                    error(it, ::finish)
                }
            }
        }
    }
}