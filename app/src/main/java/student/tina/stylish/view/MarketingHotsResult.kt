package student.tina.stylish.view

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import student.tina.stylish.adapter.DataItem
import student.tina.stylish.`object`.Hots

/**
 * Created by Wayne Chen in Jul. 2019.
 */
@Parcelize
data class MarketingHotsResult(
    val error: String? = null,
    @Json(name = "data") val hotsList: List<Hots>? = null
) : Parcelable {

    fun toHomeItems(): List<DataItem> {
        val items = mutableListOf<DataItem>()

        hotsList?.let {
            for ((title, products) in it) {
                items.add(DataItem.Header(title))
                for ((index, product) in products.withIndex()) {
                    when (index % 2) {
                        0 -> items.add(DataItem.ProductItem1(product))
                        1 -> items.add(DataItem.ProductItem2(product))
                    }
                }
            }
        }
        return items
    }
}