package com.heuron.heuronproject.data.api

import com.heuron.heuronproject.data.model.ImageModel
import retrofit2.http.GET

interface ImageApi {
    @GET("v2/list")
    suspend fun getImageList(): List<ImageModel>
}