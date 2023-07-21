package com.example.image_editor.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.image_editor.repositories.EditImageRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditImageViewModel(private  val editImageRepo: EditImageRepo):ViewModel() {

    private val imageState:MutableLiveData<ImageDataState>by lazy {
        MutableLiveData()
    }
    val  imageLiveState:LiveData<ImageDataState>get() = imageState


    fun PrepareImage(imageUri: Uri){
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
    imageLiveState.value?.isLoading=true
                editImageRepo.prepareImage(imageUri)

            }.onSuccess {
                if (it!=null)
                {
                    imageLiveState.value?.imageBitMap=it

                }
                else
                {
                    println("error happen......................")
                }
            }.onFailure {
                println("error happen......................")
            }
        }
    }
    private fun EmitImageState(isLoading:Boolean=false,imageBitMap:Bitmap?=null,err:String?=null){
    val  dataState=ImageDataState(isLoading,imageBitMap,err)
        imageState.value=dataState

    }


   data class ImageDataState(var isLoading:Boolean, var imageBitMap:Bitmap?, val err:String?)

}