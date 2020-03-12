package student.tina.stylish.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import student.tina.stylish.`object`.Product
import student.tina.stylish.view.AddToCartViewModel

class DetailSelectViewModelFactory(private val product: Product) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddToCartViewModel::class.java)) {
            return AddToCartViewModel(product) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}