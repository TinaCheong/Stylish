package student.tina.stylish.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import student.tina.stylish.`object`.Product

class CatalogProductViewModel(product: Product, app: Application) : AndroidViewModel(app) {
    private val _selectedProduct = MutableLiveData<Product>()
    val selectedProduct: LiveData<Product>
        get() = _selectedProduct

    init {
        _selectedProduct.value = product
    }

    var displayClothSize = Transformations.map(selectedProduct) {
        when (it.sizes.count()) {
            0 -> ""
            1 -> it.sizes.first()
            else -> "${it.sizes.first()} - ${it.sizes.last()}"
        }
    }

    val coutProductStock = Transformations.map(selectedProduct) {
        var stock = 0
        for (item in it.variants) {
            stock += item.stock
        }
        "$stock"
    }
}
