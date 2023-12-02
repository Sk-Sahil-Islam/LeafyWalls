package com.example.leafywalls.di

import android.app.Application
import android.content.Context
import com.example.leafywalls.common.Constants
import com.example.leafywalls.data.remote.UnsplashApi
import com.example.leafywalls.data.repository.PhotoRepositoryImpl
import com.example.leafywalls.domain.repository.PhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUnsplashApi(): UnsplashApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UnsplashApi::class.java)
    }

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun providePhotoRepository(api: UnsplashApi, context: Context): PhotoRepository {
        return PhotoRepositoryImpl(api, context)
    }
}