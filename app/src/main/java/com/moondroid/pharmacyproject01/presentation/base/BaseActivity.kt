package com.moondroid.pharmacyproject01.presentation.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.moondroid.pharmacyproject01.common.NetworkConnection
import com.moondroid.pharmacyproject01.common.collectEvent
import com.moondroid.pharmacyproject01.common.logException
import com.moondroid.pharmacyproject01.common.snack
import com.moondroid.pharmacyproject01.presentation.dialog.ButtonDialog
import com.moondroid.pharmacyproject01.presentation.dialog.DisconnectNetworkDialog
import com.moondroid.pharmacyproject01.presentation.dialog.LoadingDialog

open class BaseActivity : AppCompatActivity() {
    protected val mContext: Context by lazy { this }
    var baseViewModelLazy: Lazy<BaseViewModel>? = null

    // CommonDialog
    private val loadingDialog by lazy { LoadingDialog(mContext) }
    private val disconnectNetworkDialog by lazy { DisconnectNetworkDialog(mContext) }

    // ActivityResult
    private var onResult: (Intent?) -> Unit = {}

    //ActivityResultLauncher
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) onResult(it.data)
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        baseViewModelLazy?.value?.let {
            collectEvent(it.commonEvent, ::handleEvent)
        }

        // 네트워크 연결상태 observe
        NetworkConnection.observe(this) {
            disconnectNetworkDialog.isShow = !it
        }
    }

    private fun handleEvent(event: BaseViewModel.CommonEvent) {
        when (event) {
            is BaseViewModel.CommonEvent.Error -> showErrorMessage(event.throwable, event.callback)
            is BaseViewModel.CommonEvent.Loading -> showLoading(event.isLoading)
            is BaseViewModel.CommonEvent.Fail -> showFailMessage(event.message, event.callback)
            is BaseViewModel.CommonEvent.Message -> {}
            is BaseViewModel.CommonEvent.Toast -> window.decorView.snack(event.message)
            BaseViewModel.CommonEvent.Finish -> finish()
        }
    }

    private fun showFailMessage(message: String, callback: () -> Unit) {
        val builder = ButtonDialog.Builder(mContext).apply {
            this.message = message
            setPositiveButton("확인") {
                callback()
            }
        }
        builder.show()
    }

    private fun showErrorMessage(throwable: Throwable, callback: () -> Unit) {
        logException(throwable)
        val builder = ButtonDialog.Builder(mContext).apply {
            this.message = "에러발생 - 고객센터에 문의해주세요.\n${throwable.javaClass.simpleName}"
            setPositiveButton("확인") {
                callback()
            }
        }
        builder.show()
    }

    fun showLoading(isLoading: Boolean) {
        loadingDialog.isShow = isLoading
    }

    override fun onStart() {
        super.onStart()
        // Activity 전환 애니메이션 삭제
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, 0, 0)
        } else {
            @Suppress("DEPRECATION")
            overridePendingTransition(0, 0)
        }
    }

    /**
     * @param intent   실행 인텐트
     * @param onResult Callback 메서드
     *
     * StartActivityForResult
     */
    fun startActivityForResult(intent: Intent, onResult: (Intent?) -> Unit) {
        this.onResult = onResult
        resultLauncher.launch(intent)
    }
}

/**
 * BaseViewModel delegate
 */
@MainThread
inline fun <reified VM : BaseViewModel> BaseActivity.viewModel(
    noinline extrasProducer: (() -> CreationExtras)? = null,
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null,
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        defaultViewModelProviderFactory
    }

    val lazyViewModel = ViewModelLazy(
        VM::class,
        { viewModelStore },
        factoryPromise,
        { extrasProducer?.invoke() ?: this.defaultViewModelCreationExtras }
    )

    baseViewModelLazy = lazyViewModel
    return lazyViewModel
}