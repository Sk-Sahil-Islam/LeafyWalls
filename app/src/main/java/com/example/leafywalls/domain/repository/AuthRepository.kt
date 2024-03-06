package com.example.leafywalls.domain.repository

import com.example.leafywalls.common.Resource
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun loginUser(email: String, password: String): Resource<FirebaseUser>
    suspend fun registerUser(email: String, password: String): Resource<FirebaseUser>
    suspend fun googleSignIn(credential: AuthCredential): Resource<FirebaseUser>
    //suspend fun facebookSignIn(credential: AuthCredential): Resource<FirebaseUser>
    fun logout()
}