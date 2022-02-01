package com.example.parkplusexercise.network

import com.example.parkplusexercise.model.Item
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface AppApi {
    @GET(RetrofitInstance.END_POINT)
    fun getApiData(@Query("page") page: Int, @Query("per_page") perPage: Int): Single<ArrayList<Item>>
}