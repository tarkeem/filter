package com.example.image_editor.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.image_editor.R
import com.example.image_editor.databinding.ActivitySavedImagesBinding
import com.example.image_editor.viewmodel.SavedImagesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream
import java.io.File

class SavedImagesActivity : AppCompatActivity() {
    private  lateinit var binding: ActivitySavedImagesBinding
    val myViewModel: SavedImagesViewModel by viewModel<SavedImagesViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySavedImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

       myViewModel.savedImageLiveState.observe(this, Observer {
           val datastate=it
           if (datastate.isLoading)
           {
               binding.savedImagesProgressBar.visibility=View.VISIBLE
           }
           else
           {
               binding.savedImagesProgressBar.visibility=View.GONE
               if (datastate.savedImages!=null)
               {
                   binding.savedImagesRecyclerView.adapter=CustomAdapter(this,datastate.savedImages!!)
               }

           }
       })
        myViewModel.getSavedImages()

    }
}


class CustomAdapter(var cxt:Context,val savedImages:List<Pair<File, Bitmap>>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.filtercontainer, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val myimage = savedImages[position]

        // sets the image to the imageview from our itemHolder class

        holder.textView.text=""
        holder.imageView.setImageBitmap(myimage.second)
        holder.imageView.setOnClickListener {
            val stream = ByteArrayOutputStream()
            myimage.second.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val byteArray: ByteArray = stream.toByteArray()

            val in1 = Intent(cxt,PreviewImage::class.java)
            in1.putExtra("image", byteArray)
            val bund=Bundle()
            startActivity(cxt,in1,bund)
        }



    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return savedImages.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView), View.OnClickListener {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.filtertxt)
        override fun onClick(p0: View?) {
            var pos:Int=bindingAdapterPosition
            println(pos.toString())
        }


    }
}