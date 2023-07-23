package com.example.image_editor.activities

import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.image_editor.R
import com.example.image_editor.data.ImageFilter
import com.example.image_editor.databinding.ActivityMainBinding
import com.example.image_editor.databinding.ActivitySavedImagesBinding
import com.example.image_editor.globalListeners.filterListeners
import com.example.image_editor.repositories.savedImageRepoImpl
import com.example.image_editor.viewmodel.EditImageViewModel
import com.example.image_editor.viewmodel.SavedImagesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
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
                   binding.savedImagesRecyclerView.adapter=CustomAdapter(datastate.savedImages!!)
               }

           }
       })
        myViewModel.getSavedImages()

    }
}


class CustomAdapter(val savedImages:List<Pair<File, Bitmap>>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

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