package student.tina.stylish.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import student.tina.stylish.database.CartItems
import student.tina.stylish.databinding.LinerViewCartItemsBinding
import student.tina.stylish.view.CartItemsViewModel

class CartItemsAdapter (val viewModel: CartItemsViewModel) : ListAdapter<CartItems, CartItemsAdapter.ItemsViewHolder>(CartItemsDiffCallback) {
    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        return ItemsViewHolder.from(parent, viewModel)
    }



    class ItemsViewHolder private constructor(val binding: LinerViewCartItemsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItems) {
            binding.cartItems = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(
                parent: ViewGroup,
                viewModel: CartItemsViewModel
            ): ItemsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LinerViewCartItemsBinding.inflate(layoutInflater, parent, false)
                binding.cartItemsViewModel = viewModel
                return ItemsViewHolder(binding)
            }
        }
    }

    companion object CartItemsDiffCallback : DiffUtil.ItemCallback<CartItems>() {
        override fun areItemsTheSame(
            oldItem: CartItems,
            newItem: CartItems
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CartItems,
            newItem: CartItems
        ): Boolean {
            return oldItem === newItem
        }
    }
}