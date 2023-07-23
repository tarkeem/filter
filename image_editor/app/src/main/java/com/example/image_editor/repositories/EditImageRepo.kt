package com.example.image_editor.repositories

import android.graphics.Bitmap
import android.net.Uri
import com.example.image_editor.data.ImageFilter

interface EditImageRepo {
    suspend fun prepareImage(imageUri: Uri):Bitmap?
    suspend fun getFilters(imageBitmap: Bitmap):List<ImageFilter>?
}