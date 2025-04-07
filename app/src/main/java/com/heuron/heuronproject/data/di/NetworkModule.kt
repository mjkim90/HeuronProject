package com.heuron.heuronproject.data.di

import com.heuron.heuronproject.data.api.ImageApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://picsum.photos/"
private const val TIME_SEC: Long = 30

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideImageApi(client: OkHttpClient): ImageApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImageApi::class.java)
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .connectTimeout(TIME_SEC, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(TIME_SEC, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(TIME_SEC, java.util.concurrent.TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }
}