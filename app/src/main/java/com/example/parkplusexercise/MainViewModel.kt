package com.example.parkplusexercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.parkplusexercise.model.Item
import com.example.parkplusexercise.network.ApiStatus
import com.example.parkplusexercise.network.NetworkService
import com.example.parkplusexercise.network.SingleEvent
import io.reactivex.rxjava3.schedulers.Schedulers

open class MainViewModel : BaseViewModel() {
    init {
        getApiData()
    }
    private var _apiData = MutableLiveData<SingleEvent<ArrayList<Item>>>()
    val apiData: LiveData<SingleEvent<ArrayList<Item>>>
        get() = _apiData

    fun getApiData(page: Int = 1, perPage: Int = 10) {
        _isLoading.postValue(SingleEvent(ApiStatus.LOADING))
        addDisposable(
            NetworkService.callApi(page, perPage)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    _isLoading.postValue(SingleEvent(ApiStatus.SUCCESS))
                    _apiData.postValue(SingleEvent(it))

                }, {
                    _isLoading.postValue(SingleEvent(ApiStatus.FAILURE))
                    setError(it)
                })
        )
    }
}