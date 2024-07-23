package com.pmgohil.harmonify

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface GaanaApiService {
    @GET("songs.php")  // Replace with the actual endpoint
    suspend fun getTracks(): Response<GaanaResponse>
}

object GaanaRetrofitInstance {
    val api: GaanaApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.gaana.com/")  // Replace with the actual base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GaanaApiService::class.java)
    }
}
