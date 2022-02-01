package com.example.parkplusexercise

import com.example.parkplusexercise.model.Item
import com.example.parkplusexercise.network.NetworkService
import io.reactivex.rxjava3.core.Single

object MainRepository {
    fun callApi(page: Int = 1, perPage: Int = 10): Single<ArrayList<Item>> =
        NetworkService.callApi(page, perPage)
}