package com.flickrsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flickrsearch.model.FlickrImageResponse
import com.flickrsearch.repo.FlickrSearchUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FlickrViewModel(private val searchPhotosUseCase: FlickrSearchUseCase) : ViewModel() {

    private val _photos = MutableStateFlow<List<FlickrImageResponse>>(emptyList())
    val photos: StateFlow<List<FlickrImageResponse>> = _photos

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun searchPhotos(query: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val results = searchPhotosUseCase.execute(query)
                if (results.isEmpty()) {
                    _error.value = "No photos found for the query \"$query\""
                } else {
                    _photos.value = results
                }
            } catch (e: Exception) {
                _error.value = "Error fetching photos: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}
