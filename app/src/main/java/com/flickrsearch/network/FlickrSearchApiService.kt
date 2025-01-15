package com.flickrsearch.network

import com.flickrsearch.model.FlickrResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrSearchApiService {

    @GET("services/feeds/photos_public.gne?format=json&nojsoncallback=1")
    suspend fun getPhotosByTag(@Query("tags") tag: String): FlickrResponse

    companion object {

        fun create(): FlickrSearchApiService {
            return Retrofit.Builder()
                .baseUrl("https://api.flickr.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FlickrSearchApiService::class.java)
        }
    }
}
