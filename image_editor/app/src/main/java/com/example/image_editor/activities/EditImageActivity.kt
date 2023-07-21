package com.example.image_editor

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.image_editor.activities.MainActivity
import com.example.image_editor.databinding.ActivityEditImageBinding
import com.example.image_editor.viewmodel.EditImageViewModel
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.filter.GPUImageAddBlendFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBilateralBlurFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBoxBlurFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageCrosshatchFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageDilationFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageEmbossFilter
import org.koin.androidx.viewmodel.ext.android.viewModel


class EditImageActivity : AppCompatActivity() {
    val filterList= mutableListOf<filter>(filter("Crosshatch Filter ",GPUImageCrosshatchFilter()),
        filter("Dilation Filter ",GPUImageDilationFilter()),
        filter("Emboss Filter",GPUImageEmbossFilter()),
        filter("AddBlend Filter",GPUImageAddBlendFilter()),
        filter("Bilateral BlurFilter",GPUImageBilateralBlurFilter()),
        filter("BoxBlur Filter",GPUImageBoxBlurFilter()),

        )
    private  lateinit var binding: ActivityEditImageBinding
    private  val myViewModel:EditImageViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityEditImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOserver()
     prepareImg()

    }
    private  fun  setOserver()
    {
        myViewModel.imageLiveState.observe(this) { it ->
            val datastate = it
            if (datastate.isLoading) {
                binding.progressBar.visibility=View.VISIBLE
            } else {
                binding.progressBar.visibility=View.GONE
            }
            datastate.imageBitMap.let {bitmap->
                binding.previewimg.setImageBitmap(bitmap)


            }
        }
    }
    private fun  prepareImg()
    {
        intent.getParcelableExtra<Uri>(MainActivity.imgurl).let { uri ->
            myViewModel.PrepareImage(uri!!);
        }
    }

}


class CustomAdapter(var cxt:Context,var filterList:MutableList<filter>,var binding: ActivityEditImageBinding) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

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
        var bit=BitmapFactory.decodeResource(cxt.resources,R.drawable.girlmodel)
        var gpu=GPUImage(cxt)
        gpu.setImage(bit)
        gpu.setFilter(myfilter.filtertype)
        var newbitmb= gpu.bitmapWithFilterApplied

        holder.textView.text=myfilter.name
        holder.imageView.setImageBitmap(newbitmb)




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
