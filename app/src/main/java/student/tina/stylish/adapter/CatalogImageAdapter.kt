package student.tina.stylish.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import student.tina.stylish.`object`.Product
import student.tina.stylish.databinding.GirdViewLayoutBinding
import student.tina.stylish.databinding.ProductImageBinding

class CatalogImageAdapter : ListAdapter<String, CatalogImageAdapter.ImageViewHolder>(ImageDiffCallback) {
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder.from(parent)
    }



    class ImageViewHolder private constructor(val binding: ProductImageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.image=item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ImageViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ProductImageBinding.inflate(layoutInflater, parent, false)

                return ImageViewHolder(binding)
            }
        }
    }

    companion object ImageDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem.length == newItem.length
        }
    }
}