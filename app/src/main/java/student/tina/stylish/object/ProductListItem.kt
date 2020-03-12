package student.tina.stylish.`object`

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductListItem(
    var data : List<Product>?,
    var error : String?,
    @Json(name = "next_paging")var nextPaging : Int?
) : Parcelable