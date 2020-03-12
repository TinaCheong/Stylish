package student.tina.stylish.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import student.tina.stylish.`object`.Color

@Entity(tableName = "cart_items_table")
data class CartItems(

    @PrimaryKey(autoGenerate = true)
    var  autoId: Long = 0L,

    @ColumnInfo(name = "id")
    var id: Long = 0L, //Product id.

    @ColumnInfo(name = "product_title")
    var title: String = "", //Product title

    @ColumnInfo(name = "product_price")
    var price: Long = 0L, //Product price

    @ColumnInfo(name = "product_amount")
    var amount: Int? = 0,

    @ColumnInfo(name = "product_color")
    var color: String = "",

    @ColumnInfo(name = "product_size")
    var size: String = "",

    @ColumnInfo(name = "product_image")
    var mainImage: String = "",

    @ColumnInfo(name = "product_stock")
    var stock : Int = 0
)