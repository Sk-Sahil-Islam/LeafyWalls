package com.example.leafywalls.presentation.photo_details.components


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.leafywalls.domain.model.PhotoDetail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoDetailModal(
    sheetState: SheetState,
    photoDetail: PhotoDetail,
    isSheetOpen: Boolean
) {

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {  }
    ) {
        Text(text = "Hey")
    }
}