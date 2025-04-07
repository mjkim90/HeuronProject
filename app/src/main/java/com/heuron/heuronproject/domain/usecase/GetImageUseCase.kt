package com.heuron.heuronproject.domain.usecase

import com.heuron.heuronproject.domain.model.ImageItem

interface GetImageUseCase {
    suspend operator fun invoke(): Result<List<ImageItem>>
}
