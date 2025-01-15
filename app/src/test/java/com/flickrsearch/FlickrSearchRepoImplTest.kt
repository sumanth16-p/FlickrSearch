package com.flickrsearch

import com.flickrsearch.model.FlickrImageItem
import com.flickrsearch.model.FlickrResponse
import com.flickrsearch.model.Media
import com.flickrsearch.network.FlickrSearchApiService
import com.flickrsearch.repo.FlickrSearchRepoImpl
import com.flickrsearch.model.FlickrImageResponse
import com.flickrsearch.repo.FlickrSearchRepo
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class FlickrSearchRepoImplTest {

    private val apiService: FlickrSearchApiService = mock()
    private val flickrRepository: FlickrSearchRepo = FlickrSearchRepoImpl(apiService)

    @Test
    fun `test searchPhotos maps API response to domain model`() = runTest {
        val mockFlickrResponse = FlickrResponse(
            title = "Flickr Feed",
            link = "Link",
            description = null,
            modified = "2024-11-01",
            generator = "Flickr",
            items = listOf(
                FlickrImageItem(
                    title = "Image Title",
                    link = "Image Link",
                    media = Media("imageUrl"),
                    date_taken = "2024-11-01",
                    description = "width=100, height=200",
                    published = "2024-11-01",
                    author = "Author",
                    author_id = "123",
                    tags = "nature, landscape"
                )
            )
        )

        whenever(apiService.getPhotosByTag("nature")).thenReturn(mockFlickrResponse)

        // When
        val result = flickrRepository.searchFlickrImages("nature")

        // Then
        val expected = listOf(
                FlickrImageResponse(
                    title = "Image Title",
                    link = "Image Link",
                    imageUrl = "imageUrl",
                    dateTaken = "2024-11-01",
                    description = "width=100, height=200",
                    published = "2024-11-01",
                    author = "Author",
                    tags = "nature, landscape"
                )
        )
        assertEquals(expected, result)
    }
}
