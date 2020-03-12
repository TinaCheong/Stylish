package student.tina.stylish.view

import android.util.Log
import android.view.View
import androidx.databinding.InverseMethod
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.item_color_add_to_cart.view.*
import kotlinx.coroutines.*
import student.tina.stylish.MyApplication
import student.tina.stylish.`object`.Color
import student.tina.stylish.`object`.Product
import student.tina.stylish.`object`.UserSignIn
import student.tina.stylish.`object`.Variant
import student.tina.stylish.database.CartItems
import student.tina.stylish.database.CartItemsDatabase
import student.tina.stylish.database.CartItemsDatabaseDao

class AddToCartViewModel(val product: Product) : ViewModel() {

    private val _selectedProduct = MutableLiveData<Product>()
    val selectedProduct: LiveData<Product>
        get() = _selectedProduct

    private val _selectedColor = MutableLiveData<Color>()
    val selectedColor: LiveData<Color>
        get() = _selectedColor

    private val _selectedSize = MutableLiveData<List<Variant>>()
    val selectedSize: LiveData<List<Variant>>
        get() = _selectedSize

    private val _size = MutableLiveData<String>()
    val size: LiveData<String>
        get() = _size

    private val _stockAmount = MutableLiveData<Int>()
    val stockAmount: LiveData<Int>
        get() = _stockAmount

    var inputAmount = MutableLiveData<Int>()

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val items =
        CartItemsDatabase.getInstance(MyApplication.appContext).cartItemsDatabaseDao.getAllItems()

    var itemAddToCart = MutableLiveData<CartItems>()


    init {
        _selectedProduct.value = product
        inputAmount.value = 1
    }

    fun chooseColor(color: Color) {
        _selectedColor.value = color
        _size.value = null
        _stockAmount.value = null
        updateVariant(color.code)
    }

    private fun updateVariant(color: String) {
        _selectedSize.value = _selectedProduct.value?.variants?.filter {
            it.colorCode == color
        }
    }

    fun chooseSize(size: String) {
        _size.value = size
    }

    fun updateStock(size: String) {
        val totalStock = MutableLiveData<Int>()
        totalStock.value = 0
        selectedSize.value?.let {
            for (item in it) {
                if (item.size == size)
                    totalStock.value = totalStock.value?.plus(item.stock)
            }
//            it.forEach{item ->
//                if (item.size == size)
//                    totalStock.value = totalStock.value?.plus(item.stock)
//            }
        }
        _stockAmount.value = totalStock.value
    }

    fun plusAmount() {
        _stockAmount.value?.let {
            if (inputAmount.value?.compareTo(it) == -1)
                inputAmount.value = inputAmount.value?.plus(1)
        }
    }

    fun minusAmount() {
        if (inputAmount.value?.compareTo(1) == 1) {
            inputAmount.value = inputAmount.value?.minus(1)
        }
    }

    @InverseMethod("convertIntToString")
    fun convertStringToInt(value: String): Int {
        return try {
            value.toInt()
        } catch (e: NumberFormatException) {
            1
        }
    }

    fun convertIntToString(value: Int): String {

        return value.toString()
    }

    private suspend fun insert(item: CartItems) {
        withContext(Dispatchers.IO) {
            Log.i("Tina", "insert.item = $item")
            CartItemsDatabase.getInstance(MyApplication.appContext)
                .cartItemsDatabaseDao.insert(item)
        }
    }

    private suspend fun getCartItemsFromDatabase(): CartItems? {
        return withContext(Dispatchers.IO) {
            val item = CartItemsDatabase.getInstance(MyApplication.appContext)
                .cartItemsDatabaseDao.getItem()
            item
        }

    }

    private suspend fun get(id: Long, size: String, color: String): CartItems? {
        return withContext(Dispatchers.IO) {
            CartItemsDatabase.getInstance(MyApplication.appContext)
                .cartItemsDatabaseDao.get(id, size, color)
        }
    }


    fun onStartTracking() {
        uiScope.launch {

            get(product.id, _size.value ?: "", _selectedColor.value?.code ?: "").let {
                if (it != null) {

                    it.amount = inputAmount.value

                    update(it)
                } else {


//                    val newItem = CartItems()

                    //assigned the data to the database
//                    newItem.id = product.id
//                    newItem.title = product.title
//                    newItem.price =product.price
//                    newItem.mainImage = product.mainImage
//
//                    product.apply {
//                        newItem.id = id
//                        newItem.title = title
//                        newItem.price = price
//                        newItem.mainImage = mainImage
//                    }
//
//                    newItem.color = _selectedColor.value?.code ?: ""
//
//                    newItem.size =_size.value?:""
//
//                    newItem.stock = _stockAmount.value?:-1
//
//                    newItem.amount = inputAmount.value
//
//                    insert(newItem)
//

                    CartItems().apply {
                        id = product.id
                        title = product.title
                        price = product.price
                        mainImage = product.mainImage

                        color = _selectedColor.value?.code ?: ""

                        size = _size.value ?: ""

                        stock = _stockAmount.value ?: -1

                        amount = inputAmount.value

                        insert(this)
                    }

                }

            }
        }

    }

    private suspend fun update(item: CartItems) {
        withContext(Dispatchers.IO) {
            CartItemsDatabase.getInstance(MyApplication.appContext)
                .cartItemsDatabaseDao.update(item)
        }
    }

}



