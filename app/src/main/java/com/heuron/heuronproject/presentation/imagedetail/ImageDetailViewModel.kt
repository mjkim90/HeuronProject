package com.heuron.heuronproject.presentation.imagedetail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ImageDetailViewModel @Inject constructor() : ViewModel() {

    private val _grayScaleMap = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val grayScaleMap: StateFlow<Map<String, Boolean>> = _grayScaleMap.asStateFlow()

    // 이미지 컬러&흑백처리
    fun toggleGrayscale(imageId: String) {
        val current = _grayScaleMap.value[imageId] ?: false
        _grayScaleMap.value = _grayScaleMap.value.toMutableMap().apply {
            this[imageId] = !current
        }
    }
}