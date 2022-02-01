package com.example.parkplusexercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.parkplusexercise.network.ApiStatus
import com.example.parkplusexercise.network.NoConnectivityException
import com.example.parkplusexercise.network.SingleEvent
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import retrofit2.HttpException
import java.io.IOException

open class BaseViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    fun addDisposable(disposable: Disposable) = compositeDisposable.add(disposable)

    var _isLoading = MutableLiveData<SingleEvent<ApiStatus>>()
    val isLoading: LiveData<SingleEvent<ApiStatus>>
        get() = _isLoading

    private var _errorMessage = MediatorLiveData<SingleEvent<String>>()
    val errorMessage: LiveData<SingleEvent<String>>
        get() = _errorMessage

    override fun onCleared() = compositeDisposable.clear()

    protected fun setError(error: Throwable) = handleError(error)

    private fun handleError(error: Throwable) {
        when (error) {
            is HttpException ->
                when (error.code()) {
                    401 -> setFailedMessage("Your session has expired. Please login to continue.")
                    else ->
                        setFailedMessage("Something went wrong... try after sometime.")
                }
            is NoConnectivityException ->
                setFailedMessage(error.message)

            is IOException ->
                setFailedMessage("Something went wrong... try after sometime.")
            else -> error.message?.let { setFailedMessage(it) }
        }
    }

    private fun setFailedMessage(message: String) {
        _errorMessage.postValue(SingleEvent(message))
    }
}