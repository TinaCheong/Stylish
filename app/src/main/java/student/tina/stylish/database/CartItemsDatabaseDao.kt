package student.tina.stylish.database

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface CartItemsDatabaseDao {

    @Insert
    fun insert(items: CartItems)

    @Update
    fun update(items: CartItems)

    @Query("SELECT * from cart_items_table WHERE id = :key AND product_size = :size AND product_color = :color")
    fun get(key: Long, size: String, color: String) :CartItems?

    @Query("DELETE FROM cart_items_table WHERE id = :key AND product_size = :size AND product_color = :color")
    fun clear(key: Long, size: String, color: String)

    @Query("SELECT * FROM cart_items_table ORDER BY id DESC")
    fun getAllItems(): LiveData<List<CartItems>>

    @Query("SELECT * FROM cart_items_table ORDER BY id DESC LIMIT 1")
    fun getItem() : CartItems?
}