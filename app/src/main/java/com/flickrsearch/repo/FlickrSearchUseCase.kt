package com.flickrsearch.repo

class FlickrSearchUseCase(private val repository: FlickrSearchRepo) {
    suspend fun execute(tags: String) = repository.searchFlickrImages(tags)
}
