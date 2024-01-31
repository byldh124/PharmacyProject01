package com.moondroid.pharmacyproject01.presentation.ui.splash

import androidx.lifecycle.viewModelScope
import com.moondroid.pharmacyproject01.common.debug
import com.moondroid.pharmacyproject01.domain.Repository
import com.moondroid.pharmacyproject01.domain.model.onError
import com.moondroid.pharmacyproject01.domain.model.onFail
import com.moondroid.pharmacyproject01.domain.model.onSuccess
import com.moondroid.pharmacyproject01.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: Repository,
) : BaseViewModel() {

}