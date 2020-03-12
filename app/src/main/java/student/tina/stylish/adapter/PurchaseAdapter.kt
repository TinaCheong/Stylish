package student.tina.stylish.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import student.tina.stylish.database.CartItems
import student.tina.stylish.databinding.*
import student.tina.stylish.view.PurchaseViewModel


//set constants to represent each xml files
private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_1 = 1
private val ITEM_VIEW_TYPE_2 = 2
private val ITEM_VIEW_TYPE_3 = 3

class PurchaseAdapter(val viewModel: PurchaseViewModel) :
    ListAdapter<PurchaseItem, RecyclerView.ViewHolder>(
        PurchaseDiffCallback
    ) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndLayouts(list: List<CartItems>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(PurchaseItem.Header(""))
                else -> listOf(PurchaseItem.Header("")) + list.map { PurchaseItem.productLayout(it) } + listOf(
                    PurchaseItem.userInfoLayout
                ) + listOf(PurchaseItem.paymentLayout)
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    //telling it which data should it take
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                val item = getItem(position) as PurchaseItem.Header
                holder.bind(item.title)
            }
            is ProductViewHolder -> {
                val item = getItem(position) as PurchaseItem.productLayout
                holder.bind(item.purchase)
            }
            is UserViewHolder -> {
                val item = getItem(position) as PurchaseItem.userInfoLayout
                holder.bind()
            }
            is PaymentViewHolder -> {
                val item = getItem(position) as PurchaseItem.paymentLayout
                holder.bind(viewModel)
            }
        }


    }

    class HeaderViewHolder(private var binding: HeaderPurchaseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(title: String) {
//            binding.title=title
            binding.executePendingBindings()
        }
    }

    class ProductViewHolder(private var binding: LayoutProductPurchaseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(purchase: CartItems) {
            binding.cartItems = purchase
            binding.executePendingBindings()
        }
    }


    class UserViewHolder(private var binding: LayoutUserInformationPurchaseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
//            binding.product=product
            binding.executePendingBindings()
        }
    }

    class PaymentViewHolder(private var binding: LayoutPaymentPurchaseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: PurchaseViewModel) {
            binding.purchaseViewModel = viewModel
            binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    binding.tpdForm.visibility = View.GONE
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> {
                            binding.tpdForm.visibility = View.VISIBLE
                        }
                        else -> {
                            binding.tpdForm.visibility = View.GONE
                        }
                    }
                }

            }
            binding.executePendingBindings()
        }
    }

    //then according the constants to inflate each layout in sequence order
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder(
                HeaderPurchaseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            ITEM_VIEW_TYPE_1 -> ProductViewHolder(
                LayoutProductPurchaseBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ITEM_VIEW_TYPE_2 -> UserViewHolder(
                LayoutUserInformationPurchaseBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ITEM_VIEW_TYPE_3 -> PaymentViewHolder(
                LayoutPaymentPurchaseBinding.inflate(
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
            is PurchaseItem.Header -> ITEM_VIEW_TYPE_HEADER
            is PurchaseItem.productLayout -> ITEM_VIEW_TYPE_1
            is PurchaseItem.userInfoLayout -> ITEM_VIEW_TYPE_2
            is PurchaseItem.paymentLayout -> ITEM_VIEW_TYPE_3
        }
    }

    //DiffCallback
    companion object PurchaseDiffCallback : DiffUtil.ItemCallback<PurchaseItem>() {
        override fun areItemsTheSame(oldItem: PurchaseItem, newItem: PurchaseItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PurchaseItem, newItem: PurchaseItem): Boolean {
            return oldItem === newItem
        }
    }

}

sealed class PurchaseItem {
    data class productLayout(val purchase: CartItems) : PurchaseItem() {
        override val id = purchase.id
    }

    data class Header(val title: String) : PurchaseItem() {
        override val id = Long.MIN_VALUE
    }

    object userInfoLayout : PurchaseItem() {
        override val id: Long = Long.MAX_VALUE
    }

    object paymentLayout : PurchaseItem() {
        override val id: Long = 0L
    }


    abstract val id: Long
}