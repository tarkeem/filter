package com.example.image_editor.viewmodel

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.image_editor.data.ImageFilter
import com.example.image_editor.repositories.EditImageRepo
import com.example.image_editor.utilities.coroutines
import java.io.File
import java.io.FileOutputStream

class EditImageViewModel(private  val editImageRepo: EditImageRepo):ViewModel() {

    //region::prepareImage
    private val imageState:MutableLiveData<ImageDataState>by lazy {
        MutableLiveData()
    }
    val  imageLiveState:LiveData<ImageDataState>get() = imageState


    fun PrepareImage(imageUri: Uri){
        coroutines.io {
            runCatching {
   EmitImageState(true)
                editImageRepo.prepareImage(imageUri)

            }.onSuccess {
                if (it!=null)
                {
                    EmitImageState(imageBitMap = it)

                }
                else
                {
                    println("null image......................")
                }
            }.onFailure {
                println(it.message)
                println("error happen......................")
            }
        }
    }
    @SuppressLint("SuspiciousIndentation")
    private fun EmitImageState(isLoading:Boolean=false, imageBitMap:Bitmap?=null, err:String?=null){
    val  dataState=ImageDataState(isLoading,imageBitMap,err)
        imageState.postValue(dataState)

    }


   data class ImageDataState(var isLoading:Boolean, var imageBitMap:Bitmap?, val err:String?)
    //endregion

    //region::prepareImageFilter
    private val imageFilterState:MutableLiveData<ImageFilterDataState>by lazy {
        MutableLiveData()
    }
    val  imageFilterLiveState:LiveData<ImageFilterDataState>get() = imageFilterState



    fun loadFilters(imageBitMap: Bitmap)
    {

        coroutines.io {
            runCatching {
                EmitFilterState(isLoading = true)
                editImageRepo.getFilters(imageBitMap)
            }.onSuccess {
                if (it!=null)
                {
                    println("load filter......................")
                    EmitFilterState(imageFilters = it)
                }
                else
                {
                    println("null filter list......................")
                }

            }.onFailure {
                println("error filter......................")
            }
        }
    }

    private fun getPreviewImage(imageBitMap: Bitmap):Bitmap
    {
        return  imageBitMap
    }

    private fun EmitFilterState(
        isLoading: Boolean=false,
        imageFilters: List<ImageFilter>? = null,
        err: String? = null)
    {
        val dataStateer=ImageFilterDataState(isLoading,imageFilters,err)
        imageFilterState.postValue(dataStateer)
    }


    data class ImageFilterDataState(val isLoading: Boolean,val imageFilter:List<ImageFilter>?,val err: String?)
    //endregion

    //region:save image

    fun saveImage(imageBitMap: Bitmap)
    {
        coroutines.io {
            editImageRepo.saveImage(imageBitMap)
        }
    }


    //endregion

}