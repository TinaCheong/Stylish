package student.tina.stylish.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import student.tina.stylish.`object`.Product
import student.tina.stylish.`object`.ProductListItem
import student.tina.stylish.databinding.GirdViewLayoutBinding

class CatalogAdapter(private val onClickListener: OnClickListener) : ListAdapter<Product, CatalogAdapter.ViewHolder>(CatalogDiffCallback) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.itemView.setOnClickListener {
            Log.i("ONCLICK", "CLICKED")
            onClickListener.onClick(item)
        }
        Log.i("ONCLICK", "${holder.itemView.hasOnClickListeners()}")
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }



    class ViewHolder private constructor(val binding: GirdViewLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
            binding.product=item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GirdViewLayoutBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    companion object CatalogDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(val onClickListener: (product: Product) -> Unit) {
        fun onClick(product:Product) = onClickListener(product)
    }
}