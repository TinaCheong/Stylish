package student.tina.stylish.view

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import okhttp3.internal.waitMillis
import student.tina.stylish.MyApplication
import student.tina.stylish.database.CartItems
import student.tina.stylish.database.CartItemsDatabase
import student.tina.stylish.database.CartItemsDatabaseDao

class CartItemsViewModel(val database: CartItemsDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val allCartItems = database.getAllItems()


//    init {
//        initializeCartItems()
//    }
//
//    private fun initializeCartItems(){
//        uiScope.launch {
//            cartItem.value = getCartItemsFromDatabase()
//        }
//    }
//
//    private suspend fun getCartItemsFromDatabase() : CartItems?{
//        return withContext(Dispatchers.IO){
//            val item = database.getItem()
//            item
//        }
//
//    }


    private suspend fun remove(item: CartItems) {
        withContext(Dispatchers.IO) {
            database.clear(item.id, item.size, item.color)
        }
    }

    fun onRemove(item: CartItems) {
        Log.i("Tina", "onRemove = Click, item = $item")
        uiScope.launch {
            remove(item)
        }
    }


    private suspend fun update(item: CartItems) {
        withContext(Dispatchers.IO) {
            CartItemsDatabase.getInstance(MyApplication.appContext)
                .cartItemsDatabaseDao.update(item)
        }

    }

    fun plusOne(item: CartItems) {
        uiScope.launch {

            if (item.amount?.compareTo(item.stock) == -1) {
                item.amount = item.amount?.plus(1)
                update(item)
            }
        }
    }

    fun minusOne(item: CartItems) {
        uiScope.launch {
            if (item.amount?.compareTo(1) == 1)
                item.amount = item.amount?.minus(1)
            update(item)
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
