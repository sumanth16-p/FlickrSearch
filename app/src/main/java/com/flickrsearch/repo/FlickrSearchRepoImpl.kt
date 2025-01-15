package com.flickrsearch.repo

import com.flickrsearch.network.FlickrSearchApiService
import com.flickrsearch.model.FlickrImageResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FlickrSearchRepoImpl(
    private val apiService: FlickrSearchApiService
) : FlickrSearchRepo {

    override suspend fun searchFlickrImages(tag: String): List<FlickrImageResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getPhotosByTag(tag)
                if (response.items.isNotEmpty()) {
                    response.items.map { item ->
                        FlickrImageResponse(
                            title = item.title,
                            link = item.link,
                            imageUrl = item.media.imageUrl,
                            dateTaken = item.date_taken,
                            description = item.description,
                            published = item.published,
                            author = item.author,
                            tags = item.tags
                        )
                    }
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}
