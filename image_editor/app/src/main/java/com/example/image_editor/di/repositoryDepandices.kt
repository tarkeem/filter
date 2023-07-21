package com.example.image_editor.di

import com.example.image_editor.repositories.EditImageRepo
import com.example.image_editor.repositories.EditImageRepoImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val reposatoryModule= module {
    factory <EditImageRepo>{EditImageRepoImpl(androidContext())  }
}