package com.heuron.heuronproject.data.di

import com.heuron.heuronproject.data.api.ImageApi
import com.heuron.heuronproject.domain.usecase.GetImageUseCase
import com.heuron.heuronproject.domain.usecase.GetImageUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideImageRepository(api: ImageApi): GetImageUseCase {
        return GetImageUseCaseImpl(api)
    }
}