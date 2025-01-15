package com.flickrsearch.repo

import com.flickrsearch.model.FlickrImageResponse

interface FlickrSearchRepo {
    suspend fun searchFlickrImages(tags: String): List<FlickrImageResponse>
}
