package com.example.parkplusexercise.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        @Volatile
        private var retrofit: Retrofit? = null
        private const val BASE_URL: String = "https://api.github.com"
        const val END_POINT: String = "/orgs/octokit/repos"

        fun getInstance(): Retrofit {
            if (retrofit == null)
                synchronized(RetrofitInstance::class.java) {
                    if (retrofit == null) {
                        retrofit = Retrofit.Builder()
                            .client(OkHttpClient.Builder().build())
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                            .build()
                    }
                }
            return retrofit!!
        }
    }
}