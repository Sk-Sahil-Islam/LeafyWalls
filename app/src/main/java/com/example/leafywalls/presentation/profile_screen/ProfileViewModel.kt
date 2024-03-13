package com.example.leafywalls.presentation.profile_screen

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leafywalls.common.Resource
import com.example.leafywalls.domain.model.UserData
import com.example.leafywalls.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _currentState = MutableStateFlow(ProfileState())
    val currentState: StateFlow<ProfileState> get() = _currentState

    private val _prevState = MutableStateFlow(ProfileState())
    val prevState: StateFlow<ProfileState> get() = _prevState

    init {
        viewModelScope.launch {
            delay(100)
            updatePreviousState()
        }
    }
    fun updateProfile(context: Context, name: String, photoUri: Uri) {
        viewModelScope.launch {
            when(val result = authRepository.updateProfile(name = name, photoUri = photoUri)) {
                is Resource.Error -> {
                    _currentState.value = _currentState.value.copy(
                        error = result.message ?: "Unknown error occurred.",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _currentState.value = _currentState.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _currentState.value = _currentState.value.copy(
                        error = "",
                        isLoading = false
                    )
                    Toast.makeText(context, "Profile update successful", Toast.LENGTH_SHORT).show()
                    updatePreviousState()
                }
            }
        }
    }

//    fun uploadUserImage(imageUri: Uri, userId: String, onSuccess:(String) -> Unit) {
//        authRepository.uploadProfileImage(imageUri, userId).onEach { result ->
//            when(result) {
//                is Resource.Error -> {
//                    _currentState.value = _currentState.value.copy(
//                        error = result.message ?: "Profile update unsuccessful",
//                        isLoading = false
//                    )
//                }
//                is Resource.Loading -> {
//                    _currentState.value = _currentState.value.copy(
//                        isLoading = true
//                    )
//                }
//                is Resource.Success -> {
//                    _currentState.value = _currentState.value.copy(
//                        error = "",
//                        isLoading = false
//                    )
//                    onSuccess(result.data!!)
//                }
//            }
//        }.launchIn(viewModelScope)
//    }

    fun updateCurrentState(userData: UserData) {
        _currentState.value.userData = userData
    }

    fun updateCurrentStateUri(uri: Uri?) {
        _currentState.value.userData = _currentState.value.userData.copy(
            profilePictureUrl = uri.toString()
        )
    }



//    fun uploadUserImage(imageUri: Uri, userId: String): Flow<Resource<String>> = flow {
//        emit(Resource.Loading())
//        try {
//            val profilePhotoRef = storage.getReference("images/$userId/profile_image.jpeg")
//            profilePhotoRef.putFile(imageUri).await()
//            emit(Resource.Success("Photo upload successful"))
//        } catch (e: Exception) {
//            emit(Resource.Error("An unknown error occurred"))
//            Log.e("savedStateHandle", "Their is an error: " + e.message.toString())
//        }
//    }

//    fun retrieveUserImage(userId: String): Flow<Resource<ByteArray>> = flow {
//        emit(Resource.Loading())
//        try {
//            val profilePhotoRef = storageRef.child("users/$userId/images/profile_photo.jpg")
//            val maxDownloadSizeBytes: Long = 1024 * 1024 // Maximum allowed file size is 1MB
//            val byteArray = profilePhotoRef.getBytes(maxDownloadSizeBytes).await()
//            emit(Resource.Success(byteArray))
//        } catch (e: Exception) {
//            emit(Resource.Error("An unknown error occurred"))
//        }
//    }.flowOn(Dispatchers.IO)


//    fun selectImageFromGallery(activity: Activity) {
//        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
//
//    }
//
//    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
//            val selectedImageUri: Uri? = data.data
//            selectedImageUri?.let {
//                Log.e("savedStateHandle", it.toString())
//            }
//        }
//    }

    private fun updatePreviousState() {
        _prevState.value = _currentState.value.copy()
    }
}