package student.tina.stylish.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import student.tina.stylish.`object`.Color
import student.tina.stylish.databinding.ItemColorAddToCartBinding
import student.tina.stylish.view.AddToCartViewModel

class AddToCartColorAdapter(val viewModel: AddToCartViewModel) :
    ListAdapter<Color, AddToCartColorAdapter.ColorSelectViewHolder>(ColorSelectDiffCallback) {
    override fun onBindViewHolder(holder: ColorSelectViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            viewModel.chooseColor(item)
            notifyDataSetChanged()
        }
        holder.bind(item, viewModel.selectedColor.value?.code ?: "")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorSelectViewHolder {
        return ColorSelectViewHolder.from(parent)
    }

    class ColorSelectViewHolder private constructor(val binding: ItemColorAddToCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Color, code: String) {
            binding.color = item
            binding.selected = (item.code == code)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ColorSelectViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemColorAddToCartBinding.inflate(layoutInflater, parent, false)

                return ColorSelectViewHolder(binding)
            }
        }
    }

    companion object ColorSelectDiffCallback : DiffUtil.ItemCallback<Color>() {
        override fun areItemsTheSame(oldItem: Color, newItem: Color): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Color, newItem: Color): Boolean {
            return oldItem.code == newItem.code
        }
    }

}