package com.flickrsearch.model

import com.google.gson.annotations.SerializedName

data class FlickrResponse(
    val title: String,
    val link: String,
    val description: String?,
    val modified: String,
    val generator: String,
    val items: List<FlickrImageItem>
)

data class FlickrImageItem(
    val title: String,
    val link: String,
    val media: Media,
    val date_taken: String,
    val description: String,
    val published: String,
    val author: String,
    val author_id: String,
    val tags: String
)

data class Media(
    @SerializedName("m") val imageUrl: String
)
