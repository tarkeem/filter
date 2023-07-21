package com.example.image_editor.repositories

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.InputStream

class EditImageRepoImpl(private val cxt:Context):EditImageRepo {
    override suspend fun prepareImage(imageUri: Uri): Bitmap {
        getInputStream(imageUri).let {
                val  imagebitmp=BitmapFactory.decodeStream(it)
                return imagebitmp
        }
    }

    fun getInputStream(imageUri: Uri): InputStream?
    {
        return cxt.contentResolver.openInputStream(imageUri)
    }
}