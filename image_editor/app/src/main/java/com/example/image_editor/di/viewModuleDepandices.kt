package com.example.image_editor.di

import com.example.image_editor.viewmodel.EditImageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule= module {
    viewModel { EditImageViewModel(get()) }
}