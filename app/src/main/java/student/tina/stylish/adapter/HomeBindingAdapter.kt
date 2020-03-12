package student.tina.stylish.adapter

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.Shape
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import student.tina.stylish.MyApplication
import student.tina.stylish.R
import student.tina.stylish.`object`.Color
import student.tina.stylish.`object`.Product
import student.tina.stylish.`object`.Variant
import student.tina.stylish.database.CartItems

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions()
                .placeholder(R.mipmap.image_placeholder)
                .error(R.mipmap.image_placeholder))
            .into(imgView)
    }
}



@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<DataItem>?) {
    val adapter = recyclerView.adapter as StylishAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataGrid")
fun bindRecyclerViewGrid(recyclerView: RecyclerView, data: List<Product>?) {
    val adapter = recyclerView.adapter as CatalogAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataImage")
fun bindRecyclerViewImage(recyclerView: RecyclerView, data: List<String>?) {
    val adapter = recyclerView.adapter as CatalogImageAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataColor")
fun bindRecyclerViewColor(recyclerView: RecyclerView, data: List<Color>?) {
    val adapter = recyclerView.adapter as CatalogColorAdapter
    adapter.submitList(data)
}

@BindingAdapter("displayPrice")
fun bindPrice(textView: TextView, price: Long) {
    textView.text = MyApplication.appContext.getString(R.string.dollar, price)
}

@BindingAdapter("displayColor")
fun bindColor(view : View, colorCode: String) {
    val setColor = android.graphics.Color.parseColor("#${colorCode}")
    view.setBackgroundColor(setColor)
}

@BindingAdapter("listDataColorSelect")
fun bindRecyclerViewColorSelect(recyclerView: RecyclerView, data: List<Color>?) {
    val adapter = recyclerView.adapter as AddToCartColorAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataSizeSelect")
fun bindRecyclerViewSizeSelect(recyclerView: RecyclerView, data: List<Variant>?) {
    val adapter = recyclerView.adapter as AddToCartSizeAdapter
    adapter.submitList(data)
}

@BindingAdapter("displayStock")
fun bindStock(textView: TextView, stock: Int) {
    textView.text = MyApplication.appContext.getString(R.string.stock_amount, stock)
}

@BindingAdapter("colorAddBorder")
fun bindColorBorder(view: View, color: Color) {
    view.background = ShapeDrawable(object : Shape() {
        override fun draw(canvas: Canvas, paint: Paint) {
            paint.color = android.graphics.Color
                .parseColor("#${color.code}")
            paint.style = Paint.Style.FILL
            canvas.drawRect(0f, 0f, this.width, this.height, paint)
            paint.color = android.graphics.Color.BLACK
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 2.toFloat()
            canvas.drawRect(0f, 0f, this.width, this.height, paint)
        }
    })
}

@BindingAdapter("listDataCartItems")
fun bindRecyclerViewCartItems(recyclerView: RecyclerView, data: List<CartItems>?) {
    val adapter = recyclerView.adapter as CartItemsAdapter
    adapter.submitList(data)
}

@BindingAdapter("colorAddBorders")
fun bindColorBorders(view: View, color: String) {
    view.background = ShapeDrawable(object : Shape() {
        override fun draw(canvas: Canvas, paint: Paint) {
            paint.color = android.graphics.Color
                .parseColor("#${color}")
            paint.style = Paint.Style.FILL
            canvas.drawRect(0f, 0f, this.width, this.height, paint)
            paint.color = android.graphics.Color.BLACK
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 2.toFloat()
            canvas.drawRect(0f, 0f, this.width, this.height, paint)
        }
    })
}

@BindingAdapter("listDataPurchase")
fun bindRecyclerViewPurchase(recyclerView: RecyclerView, data: List<CartItems>?) {
    val adapter = recyclerView.adapter as PurchaseAdapter
    adapter.addHeaderAndLayouts(data)
}

@BindingAdapter("displayAmount")
fun bindAmount(textView: TextView, amount: Int) {
    textView.text = MyApplication.appContext.getString(R.string.total_amount, amount)
}
