package com.example.image_editor.utilities

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object  coroutines
{
    fun io(work:suspend () -> Unit){
CoroutineScope(Dispatchers.IO).launch {
work()
}
    }
}