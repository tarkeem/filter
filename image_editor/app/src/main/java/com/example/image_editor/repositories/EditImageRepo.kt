package com.example.image_editor.repositories

import android.graphics.Bitmap
import android.net.Uri

interface EditImageRepo {
    suspend fun prepareImage(imageUri: Uri):Bitmap
}