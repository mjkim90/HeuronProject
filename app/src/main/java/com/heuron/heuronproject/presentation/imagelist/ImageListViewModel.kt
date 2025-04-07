package com.heuron.heuronproject.presentation.imagelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heuron.heuronproject.domain.model.ImageItem
import com.heuron.heuronproject.domain.usecase.GetImageUseCase
import com.heuron.heuronproject.core.UiState
import com.heuron.heuronproject.domain.util.NetworkError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

data class ImageQuery(
    val id: String = "",
    val author: String = "",
    val width: String = "",
    val height: String = ""
)

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val getImageUseCase: GetImageUseCase
) : ViewModel() {

    private val _query = MutableStateFlow(ImageQuery())
    val query: StateFlow<ImageQuery> = _query.asStateFlow()

    private val _imageUiState = MutableStateFlow<UiState<List<ImageItem>>>(UiState.Success(emptyList()))
    val imageUiState: StateFlow<UiState<List<ImageItem>>> = _imageUiState.asStateFlow()

    private var allImages: List<ImageItem> = emptyList()
    private var hasFetchedImage = false

    fun setQueryChanged(
        id: String? = null,
        author: String? = null,
        width: String? = null,
        height: String? = null
    ) {
        val newQuery = query.value.copy(
            id = id ?: query.value.id,
            author = author ?: query.value.author,
            width = width ?: query.value.width,
            height = height ?: query.value.height
        )

        _query.value = newQuery

        val isAllFieldsBlank = newQuery.id.isBlank() &&
                newQuery.author.isBlank() &&
                newQuery.width.isBlank() &&
                newQuery.height.isBlank()

        if (isAllFieldsBlank) {
            _imageUiState.value = UiState.Success(emptyList())
            return
        }

        if (!hasFetchedImage) {
            fetchImages()
        } else {
            val filtered = filterImages(allImages, newQuery)
            _imageUiState.value = UiState.Success(filtered)
        }
    }

    private fun fetchImages() {
        viewModelScope.launch {
            _imageUiState.value = UiState.Loading

            val result = getImageUseCase()
            when {
                result.isSuccess -> {
                    allImages = result.getOrNull() ?: emptyList()
                    val filtered = filterImages(allImages, query.value)
                    _imageUiState.value = UiState.Success(filtered)
                    hasFetchedImage = true
                }

                result.isFailure -> {
                    val errorMessage = when (val exception = result.exceptionOrNull()) {
                        is UnknownHostException -> NetworkError.NoInternet
                        is SocketTimeoutException -> NetworkError.Timeout
                        is ConnectException -> NetworkError.ServerNotUsed
                        else -> NetworkError.Unknown(exception)
                    }
                    _imageUiState.value = UiState.Error(errorMessage.message)
                }
            }
        }
    }

    private fun filterImages(images: List<ImageItem>, query: ImageQuery): List<ImageItem> {
        return images.filter {
            it.id.contains(query.id, ignoreCase = true) &&
                    it.author.contains(query.author, ignoreCase = true) &&
                    it.width.toString().contains(query.width) &&
                    it.height.toString().contains(query.height)
        }
    }
}