package com.pmgohil.harmonify

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url

interface SpotifyService {
    @GET("v1/me/tracks")
    suspend fun getSavedTracks(
        @Header("Authorization") accessToken: String,
        @Query("limit") limit: Int? = 1,
        @Query("offset") offset: Int = 0
    ): Response<PagingObject<SavedTrack>>

    fun getUserTracks(@Header("Authorization") accessToken: String): Call<PagingObject<SavedTrack>>

    @GET
    suspend fun getNextTracks(
        @Header("Authorization") token: String, @Url url: String
    ): Response<PagingObject<SavedTrack>>

    @GET("v1/me")
    suspend fun getCurrentUser(@Header("Authorization") accessToken: String): Response<UserProfile>

    @GET("v1/me/tracks")
    suspend fun getSavedTracks(@Header("Authorization") accessToken: String): Response<SavedTrack>

    @GET("v1/search")
    suspend fun searchTracks(
        @Header("Authorization") accessToken: String,
        @Query("q") query: String,
        @Query("type") type: String = "track",
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int = 0
    ): Response<SearchResponse>
}
