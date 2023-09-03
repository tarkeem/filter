package com.example.image_editor.activities

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.image_editor.databinding.ActivityPreviewImageBinding


class PreviewImage : AppCompatActivity() {
    lateinit var binding:ActivityPreviewImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding=ActivityPreviewImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val byteArray = intent.getByteArrayExtra("image")
        val bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
        binding.imageView2.setImageBitmap(bmp)
    }
}