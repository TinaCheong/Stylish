package student.tina.stylish.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import student.tina.stylish.database.CartItemsDatabaseDao
import student.tina.stylish.view.CartItemsViewModel
import student.tina.stylish.view.PurchaseViewModel

@Suppress("UNCHECKED_CAST")
class PurchaseViewModelFactory (private val dataSource : CartItemsDatabaseDao, private val application : Application) : ViewModelProvider.Factory{
    @Suppress("uncheck_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PurchaseViewModel::class.java)){
            return PurchaseViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}