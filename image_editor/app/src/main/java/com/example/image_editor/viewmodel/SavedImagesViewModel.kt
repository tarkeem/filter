package com.example.image_editor.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.image_editor.repositories.savedImageReop
import com.example.image_editor.utilities.coroutines
import java.io.File

class SavedImagesViewModel(private val savedImageReop: savedImageReop):ViewModel() {

    private val savedImageState: MutableLiveData<SavedImageDataState> by lazy {
        MutableLiveData()
    }
    val  savedImageLiveState: LiveData<SavedImageDataState>
        get() = savedImageState


    fun getSavedImages()
    {
        coroutines.io {
            runCatching {
                EmitSavedImageState(isLoading = true)
                savedImageReop.loadeSaveImage()
            }.onSuccess {
                EmitSavedImageState(savedImages = it)
            }


        }





    }
    private fun EmitSavedImageState(isLoading:Boolean=false, savedImages:List<Pair<File, Bitmap>>?=null, err:String?=null){
        val  dataState= SavedImageDataState(isLoading,savedImages,err)
        savedImageState.postValue(dataState)

    }
    data class SavedImageDataState(var isLoading:Boolean, var savedImages:List<Pair<File, Bitmap>>?, val err:String?)

}