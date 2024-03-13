package com.example.leafywalls.data.repository

import android.net.Uri
import com.example.leafywalls.common.Resource
import com.example.leafywalls.domain.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient?,
    private val storage: FirebaseStorage
): AuthRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun loginUser(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun registerUser(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            firebaseAuth.currentUser?.sendEmailVerification()?.await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun googleSignIn(credential: AuthCredential): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithCredential(credential).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun updateProfile(name: String, photoUri: Uri): Resource<Boolean> {
        return try {
            val uploadResult = uploadProfileImage(photoUri, userId = getCurrentUserId()!!)
            if (uploadResult is Resource.Error) {
                return Resource.Error(uploadResult.message ?: "Failed to upload photo")
            }

            when (val downloadResult = retrieveProfileImage(userId = getCurrentUserId()!!)) {
                is Resource.Success -> {
                    val profileUpdates = userProfileChangeRequest {
                        displayName = name
                        this.photoUri = downloadResult.data
                    }
                    currentUser?.updateProfile(profileUpdates)?.await()
                    Resource.Success(true)
                }
                is Resource.Error -> Resource.Error(downloadResult.message ?: "Failed to retrieve photo")
                is Resource.Loading -> Resource.Error("Unexpected loading state while retrieving photo")
            }
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    private suspend fun uploadProfileImage(imageUri: Uri, userId: String): Resource<Unit> {
        return try {
            val profilePhotoRef = storage.getReference("images/$userId/profile_image.jpeg")
            profilePhotoRef.putFile(imageUri).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("An unknown error occurred while uploading photo")
        }
    }

    private suspend fun retrieveProfileImage(userId: String): Resource<Uri> {
        return try {
            val profilePhotoRef = storage.getReference("images/$userId/profile_image.jpeg")
            val downloadUrl = profilePhotoRef.downloadUrl.await()
            Resource.Success(downloadUrl)
        } catch (e: Exception) {
            Resource.Error("An unknown error occurred while retrieving photo")
        }
    }

//    override suspend fun updateProfile(name: String, photoUri: Uri): Resource<Boolean> {
//        val profileUpdates = userProfileChangeRequest {
//            displayName = name
//            this.photoUri = photoUri
//        }
//        return try{
//            currentUser?.updateProfile(profileUpdates)?.await()
//            Resource.Success(true)
//        } catch (e: Exception) {
//            Resource.Error(e.message.toString())
//        }
//    }
//
//    override fun uploadProfileImage(imageUri: Uri, userId: String): Flow<Resource<String>> = flow {
//        emit(Resource.Loading())
//        try {
//            val profilePhotoRef = storage.getReference("images/$userId/profile_image.jpeg")
//            profilePhotoRef.putFile(imageUri).await()
//            emit(Resource.Success("Photo upload successful"))
//        } catch (e: Exception) {
//            emit(Resource.Error("An unknown error occurred"))
//            Log.e("savedStateHandle", "There is an error: ${e.message}")
//        }
//    }
//
//    override fun retrieveProfileImage(userId: String): Flow<Resource<Uri>> = flow {
//        emit(Resource.Loading())
//        try {
//            val profilePhotoRef = storage.getReference("images/$userId/profile_image.jpeg")
//            val downloadUrl = profilePhotoRef.downloadUrl.await()
//            emit(Resource.Success(downloadUrl))
//        } catch (e: Exception) {
//            emit(Resource.Error("An unknown error occurred"))
//        }
//    }

//    override suspend fun facebookSignIn(credential: AuthCredential): Resource<FirebaseUser> {
//        return try {
//            val result = firebaseAuth.signInWithCredential(credential).await()
//            Resource.Success(result.user!!)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Resource.Error(e.message.toString())
//        }
//    }

    override fun logout() {
        firebaseAuth.signOut()
        googleSignInClient?.signOut()
        //revokePublishPermissions()
    }

    private fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }
}

//fun revokePublishPermissions() {
//    val accessToken = AccessToken.getCurrentAccessToken()
//    if (accessToken != null && !accessToken.isExpired) {
//
//        val request = GraphRequest.newGraphPathRequest(
//            accessToken,
//            "/me/permissions",
//            null
//        )
//
//        // Use HttpMethod.DELETE to revoke publish permissions
//        request.httpMethod = HttpMethod.DELETE
//
//        request.callback = GraphRequest.Callback { response: GraphResponse? ->
//            // Handle the response here
//            if (response != null) {
//                // Permissions revoked, you can handle the response
//            }
//        }
//
//        request.executeAsync()
//
//        LoginManager.getInstance().logOut()
//    }
//
//}