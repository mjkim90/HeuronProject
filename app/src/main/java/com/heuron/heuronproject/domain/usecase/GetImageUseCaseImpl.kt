package com.heuron.heuronproject.domain.usecase

import com.heuron.heuronproject.data.api.ImageApi
import com.heuron.heuronproject.domain.model.ImageItem
import com.heuron.heuronproject.domain.model.toDomain
import javax.inject.Inject

class GetImageUseCaseImpl @Inject constructor(
    private val api: ImageApi
) : GetImageUseCase {
    override suspend fun invoke(): Result<List<ImageItem>> {
        return try {
            val data = api.getImageList().map { it.toDomain() }
            Result.success(data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}