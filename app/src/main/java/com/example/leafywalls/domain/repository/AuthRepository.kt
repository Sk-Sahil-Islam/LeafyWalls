package com.example.leafywalls.domain.repository

import android.net.Uri
import com.example.leafywalls.common.Resource
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun loginUser(email: String, password: String): Resource<FirebaseUser>
    suspend fun registerUser(email: String, password: String): Resource<FirebaseUser>
    suspend fun googleSignIn(credential: AuthCredential): Resource<FirebaseUser>
    //suspend fun facebookSignIn(credential: AuthCredential): Resource<FirebaseUser>

    suspend fun updateProfile(name: String, photoUri: Uri): Resource<Boolean>

//    fun uploadProfileImage(imageUri: Uri, userId: String): Flow<Resource<String>>
//    fun retrieveProfileImage(userId: String): Flow<Resource<Uri>>
    fun logout()
}