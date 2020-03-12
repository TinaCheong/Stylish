package student.tina.stylish.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import student.tina.stylish.`object`.Product
import student.tina.stylish.databinding.HeaderBinding
import student.tina.stylish.databinding.LinearViewLayouts2Binding
import student.tina.stylish.databinding.LinearViewLayoutsBinding

//set constants to represent each xml files
private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_1 = 1
private val ITEM_VIEW_TYPE_2 = 2


class StylishAdapter : ListAdapter<DataItem, RecyclerView.ViewHolder>(
    DiffCallback
){


    //telling it which data should it take
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is TextViewHolder ->{
                val item = getItem(position) as DataItem.Header
                holder.bind(item.title)
            }
            is LayoutViewHolder ->{
                val item = getItem(position) as DataItem.ProductItem1
                holder.bind(item.product)
            }
            is LayoutViewHolder2 ->{
                val item = getItem(position) as DataItem.ProductItem2
                holder.bind(item.product)
            }
        }


    }
    //inflate the header
    class TextViewHolder(private var binding: HeaderBinding): RecyclerView.ViewHolder(binding.root) {
//        companion object {
//            fun from(parent: ViewGroup): TextViewHolder {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val view = layoutInflater.inflate(R.layout.header, parent, false)
//                return TextViewHolder(view)
//            }
//        }
        fun bind(title: String){
             binding.title=title
}
    }
    //binding the xml variable to the Product data, in oder to get
    class LayoutViewHolder(private var binding: LinearViewLayoutsBinding): RecyclerView.ViewHolder(binding.root) {
      //  companion object {
      //      fun from(parent: ViewGroup): LayoutViewHolder {
      //          val layoutInflater = LayoutInflater.from(parent.context)
      //          val view = layoutInflater.inflate(R.layout.linear_view_layouts, parent, false)
      //          return LayoutViewHolder(view)
      //      }
      //  }
        fun bind(product: Product) {
            binding.product = product
          binding.executePendingBindings()
        }
    }

    //binding the xml variable to the Product data
    class LayoutViewHolder2(private var binding: LinearViewLayouts2Binding): RecyclerView.ViewHolder(binding.root) {
      //  companion object {
      //      fun from(parent: ViewGroup): LayoutViewHolder2 {
      //          val layoutInflater = LayoutInflater.from(parent.context)
      //          val view = layoutInflater.inflate(R.layout.linear_view_layouts2, parent, false)
      //          return LayoutViewHolder2(view)
      //      }
      //  }
        fun bind(product: Product){
          binding.product=product
          binding.executePendingBindings()
      }
    }

    //then according the constants to inflate each layout in sequence order
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder(
                HeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            ITEM_VIEW_TYPE_1 -> LayoutViewHolder(
                LinearViewLayoutsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            ITEM_VIEW_TYPE_2 -> LayoutViewHolder2(
                LinearViewLayouts2Binding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    //define which object to which constants
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.ProductItem1 -> ITEM_VIEW_TYPE_1
            is DataItem.ProductItem2 -> ITEM_VIEW_TYPE_2
        }
    }

    //DiffCallback
    companion object DiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }
    }

}
//create 3 Objects (represnt 3 xml file) and get the itemView
sealed class DataItem {
    data class ProductItem1(val product: Product): DataItem() {
        override val id = product.id
    }

    data class Header(val title: String): DataItem() {
        override val id = Long.MIN_VALUE
    }

    data class ProductItem2(val product : Product): DataItem(){
        override val id: Long = product.id
    }

    abstract val id: Long
}


