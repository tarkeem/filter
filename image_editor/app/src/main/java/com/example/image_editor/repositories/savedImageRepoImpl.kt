package com.example.image_editor.repositories

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import java.io.File

class savedImageRepoImpl(private val cxt:Context):savedImageReop {
    override suspend fun loadeSaveImage(): List<Pair<File, Bitmap>> {
        val savedImage = ArrayList<Pair<File,Bitmap>>()
        val storageDir=File(cxt.getExternalFilesDir(Environment.DIRECTORY_PICTURES),"saved image")
        storageDir.listFiles().let {data->
            data.forEach {
                savedImage.add(Pair(it,getImageBitMap(it)))
            }
            return  savedImage
        }

    }
    private fun getImageBitMap(file:File):Bitmap{
        val imageBitmap=BitmapFactory.decodeFile(file.absolutePath)
        return imageBitmap
    }
}