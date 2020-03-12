package student.tina.stylish.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import student.tina.stylish.`object`.Product
import student.tina.stylish.view.CatalogProductViewModel

class CatalogProductViewModelFactory(
    private val product: Product,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatalogProductViewModel::class.java)) {
            return CatalogProductViewModel(product, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}