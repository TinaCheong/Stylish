package student.tina.stylish.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import student.tina.stylish.`object`.Color
import student.tina.stylish.databinding.ColorBlockLinearBinding
import student.tina.stylish.databinding.ProductImageBinding

class CatalogColorAdapter : ListAdapter<Color, CatalogColorAdapter.ColorViewHolder>(ColorDiffCallback) {
    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder.from(parent)
    }



    class ColorViewHolder private constructor(val binding: ColorBlockLinearBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Color) {
            binding.color=item
//            val setColor = android.graphics.Color.parseColor("#${item.code}")
//            binding.colorBlock.setBackgroundColor(setColor)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ColorViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ColorBlockLinearBinding.inflate(layoutInflater, parent, false)

                return ColorViewHolder(binding)
            }
        }
    }

    companion object ColorDiffCallback : DiffUtil.ItemCallback<Color>() {
        override fun areItemsTheSame(
            oldItem: Color,
            newItem: Color
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: Color,
            newItem: Color
        ): Boolean {
            return oldItem.code == newItem.code
        }
    }
}