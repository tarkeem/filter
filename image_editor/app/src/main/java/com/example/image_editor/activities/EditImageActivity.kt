package com.example.image_editor

import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import androidx.recyclerview.widget.RecyclerView
import com.example.image_editor.activities.MainActivity
import com.example.image_editor.data.ImageFilter
import com.example.image_editor.databinding.ActivityEditImageBinding
import com.example.image_editor.globalListeners.filterListeners
import com.example.image_editor.viewmodel.EditImageViewModel
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.filter.GPUImageAddBlendFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBilateralBlurFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBoxBlurFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageCrosshatchFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageDilationFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageEmbossFilter
import org.koin.androidx.viewmodel.ext.android.viewModel


class EditImageActivity : AppCompatActivity(),filterListeners {

    private  lateinit var binding: ActivityEditImageBinding
      val myViewModel:EditImageViewModel by viewModel<EditImageViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityEditImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOserver()
        prepareImg()

    }





    private  fun  setOserver()
    {
        println("oserver fun fun..........................")
        myViewModel.imageLiveState.observe(this) { it ->
            println("change.................................")
            val datastate = it?:return@observe
            if (datastate.isLoading) {
                println("loading.................................")
                binding.progressBar.visibility=View.VISIBLE
            } else {
                println("no loading.................................")
                binding.progressBar.visibility=View.GONE
            }
            datastate.imageBitMap.let {bitmap->
                binding.previewimg.setImageBitmap(bitmap)
                if (bitmap==null)
                {
                    println("null test..............................")
                }
                else
                {
                    myViewModel.loadFilters(bitmap)
                }



            }
        }


        myViewModel.imageFilterLiveState.observe(this, Observer {
            val imageFilterState=it
            if (imageFilterState.isLoading)
            {
                binding.progressBar2.visibility=View.VISIBLE
            }
            else{
                binding.progressBar2.visibility=View.GONE
                binding.filterlist.adapter= CustomAdapter(this,imageFilterState.imageFilter!!,this)
            }
        })
    }
    private fun  prepareImg()
    {
        println("prepare image fun..........................")
        intent.getParcelableExtra<Uri>(MainActivity.imgurl).let { uri ->
            println(uri.toString())
            myViewModel.PrepareImage(uri!!);
        }
    }

    override fun onFilterChange(filter: ImageFilter) {
        /*first,make interface then make your activity inherit it then pass this as instance of the
        interface so any the change will affect on your activity */
        binding.previewimg.setImageBitmap(filter.filterPreview)
    }

}


class CustomAdapter(var cxt:Context,var filterList:List<ImageFilter>,var filterlst: filterListeners) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

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

        val myfilter = filterList[position]

        // sets the image to the imageview from our itemHolder class

        holder.textView.text=myfilter.name
        holder.imageView.setImageBitmap(myfilter.filterPreview)
        holder.imageView.setOnClickListener {
            filterlst.onFilterChange(myfilter)
        }


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return filterList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView),View.OnClickListener {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.filtertxt)
        override fun onClick(p0: View?) {
            var pos:Int=bindingAdapterPosition
            println(pos.toString())
        }


    }
}
