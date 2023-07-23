package com.example.image_editor.repositories

import android.graphics.Bitmap
import java.io.File

interface savedImageReop {
    suspend fun loadeSaveImage():List<Pair<File,Bitmap>>
}