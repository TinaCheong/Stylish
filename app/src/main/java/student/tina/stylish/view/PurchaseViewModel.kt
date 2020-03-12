package student.tina.stylish.view


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import student.tina.stylish.database.CartItems
import student.tina.stylish.database.CartItemsDatabaseDao

class PurchaseViewModel(val database: CartItemsDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    val allCartItems = database.getAllItems()

    var purchasePrice: Long = 0

    var shippingFee: Long = 60

    var totalAmount: Int = 0

    var totalPrice: Long = 0

    fun countPriceAndAmount() {
        allCartItems.value?.let {
            for (item in it) {

                item.amount?.apply {
                    purchasePrice += this * item.price
                    totalAmount = totalAmount.plus(1)
                }


                totalPrice = purchasePrice + shippingFee
            }
        }
    }

}