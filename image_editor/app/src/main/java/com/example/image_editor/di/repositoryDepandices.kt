package com.example.image_editor.di

import com.example.image_editor.repositories.EditImageRepo
import com.example.image_editor.repositories.EditImageRepoImpl
import com.example.image_editor.repositories.savedImageReop
import com.example.image_editor.repositories.savedImageRepoImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

//if you want to pass context as parameter in injected class follow this
val reposatoryModule= module {
    factory <EditImageRepo>{EditImageRepoImpl(androidContext())  }
    factory <savedImageReop>{savedImageRepoImpl(androidContext())  }
}