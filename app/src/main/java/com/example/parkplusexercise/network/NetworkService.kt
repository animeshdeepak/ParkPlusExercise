package com.example.parkplusexercise.network

import com.example.parkplusexercise.model.Item
import io.reactivex.rxjava3.core.Single

object NetworkService {
    fun callApi(page: Int = 1, perPage: Int = 10) : Single<ArrayList<Item>> {
        return RetrofitInstance.getInstance().
        create(AppApi::class.java).
        getApiData(page, perPage)
    }
}