package com.example.leafywalls.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.leafywalls.R
import com.example.leafywalls.common.Constants
import com.example.leafywalls.data.db.FavouriteDao
import com.example.leafywalls.data.db.LeafyDatabase
import com.example.leafywalls.data.remote.UnsplashApi
import com.example.leafywalls.data.repository.AuthRepositoryImpl
import com.example.leafywalls.data.repository.PhotoRepositoryImpl
import com.example.leafywalls.domain.repository.AuthRepository
import com.example.leafywalls.domain.repository.PhotoRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // Optional: Add logging interceptor for debugging
            .build()
    }

    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesCloudReference() = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun providesRepositoryImpl(firebaseAuth: FirebaseAuth, googleSignInClient: GoogleSignInClient, storage: FirebaseStorage): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth,googleSignInClient, storage)
    }

    @Provides
    @Singleton
    fun provideGoogleSignInClient(@ApplicationContext applicationContext: Context): GoogleSignInClient {
        val mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(applicationContext.getString(R.string.web_client_id))
            .requestProfile()
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(applicationContext, mGoogleSignInOptions)
    }

    @Provides
    @Singleton
    fun provideUnsplashApi(okHttpClient: OkHttpClient): UnsplashApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(UnsplashApi::class.java)
    }

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideLeafyDatabase(@ApplicationContext context: Context): LeafyDatabase {
        return Room.databaseBuilder(
            context,
            LeafyDatabase::class.java,
            "leafy.db"
        ).build()
    }

    @Provides
    fun provideStepsDao(leafyDatabase: LeafyDatabase): FavouriteDao {
        return leafyDatabase.favouriteDao
    }

    @Provides
    @Singleton
    fun providePhotoRepository(api: UnsplashApi, context: Context, favouriteDao: FavouriteDao): PhotoRepository {
        return PhotoRepositoryImpl(api, context, favouriteDao)
    }

}