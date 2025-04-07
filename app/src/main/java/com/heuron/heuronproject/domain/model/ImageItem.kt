package com.heuron.heuronproject.domain.model

import com.heuron.heuronproject.data.model.ImageModel

data class ImageItem(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val imageUrl: String
)

fun ImageModel.toDomain() = ImageItem(
    id = id,
    author = author,
    width = width,
    height = height,
    imageUrl = download_url
)