package com.example.image_editor.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.image_editor.EditImageActivity
import com.example.image_editor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object{
        private  const val  req_code=1
        const val imgurl="url"
    }
    private  lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.saveimg.setOnClickListener {
            Intent(this, EditImageActivity::class.java).also {
                    intent ->
                startActivity(intent)}
        }
        binding.editimg.setOnClickListener {
            println("clicked")

            var intent = Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.
            EXTERNAL_CONTENT_URI).also { intenter ->
                intenter.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivityForResult(intenter, req_code)
            }

            println("finished")

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

            println("navigate..............")
            data?.data.let { uriimg ->
                println(uriimg.toString()+"...........................................................")
                Intent(applicationContext, EditImageActivity::class.java).also {
                        intent -> intent.putExtra(imgurl,uriimg)

                    startActivity(intent)}
            }



    }
}