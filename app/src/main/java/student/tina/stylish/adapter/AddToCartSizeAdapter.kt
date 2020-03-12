package student.tina.stylish.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import student.tina.stylish.`object`.Variant
import student.tina.stylish.databinding.ItemSizeAddToCartBinding
import student.tina.stylish.view.AddToCartViewModel

class AddToCartSizeAdapter(val viewModel: AddToCartViewModel) :
    ListAdapter<Variant, AddToCartSizeAdapter.SizeSelectViewHolder>(SizeSelectDiffCallback) {
    override fun onBindViewHolder(holder: SizeSelectViewHolder, position: Int) {
        val item = getItem(position)
        if (item.stock > 0) {
            holder.itemView.setOnClickListener {
                viewModel.chooseSize(item.size)
                viewModel.updateStock(item.size)
                notifyDataSetChanged()
            }
        } else {
            holder.itemView.setOnClickListener(null)
        }
        holder.bind(item, viewModel.size.value ?: "")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeSelectViewHolder {
        return SizeSelectViewHolder.from(parent)
    }


    class SizeSelectViewHolder private constructor(val binding: ItemSizeAddToCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Variant, size: String) {
            binding.variant = item
            binding.selected = (item.size == size)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SizeSelectViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemSizeAddToCartBinding.inflate(layoutInflater, parent, false)

                return SizeSelectViewHolder(binding)
            }
        }
    }

    companion object SizeSelectDiffCallback : DiffUtil.ItemCallback<Variant>() {
        override fun areItemsTheSame(
            oldItem: Variant,
            newItem: Variant
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: Variant,
            newItem: Variant
        ): Boolean {
            return oldItem.size == newItem.size
        }
    }

}