package student.tina.stylish.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import student.tina.stylish.`object`.Product
import student.tina.stylish.network.RetrofitApi
import student.tina.stylish.network.StylishApiStatus

class CatalogMenViewModel : ViewModel(){
    private val _product = MutableLiveData<List<Product>>()
    val product: LiveData<List<Product>>
        get() = _product
    private val _status = MutableLiveData<StylishApiStatus>()
    val status: LiveData<StylishApiStatus>
        get() = _status
    var paging: Int? = null
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getMenCatalogProduct()
    }

    fun getMenCatalogProduct() {
        coroutineScope.launch {
            var getPropertiesDeferred = RetrofitApi.retrofitService.getCatagoryItem("men",paging)
            try {
                _status.value = StylishApiStatus.LOADING
                val listResult = getPropertiesDeferred.await()
//                if (paging == null) {
//                    _product.value = listResult.data
//                } else{
                val newData = mutableListOf<Product>()
                _product.value?.let {
                    newData.addAll(it)
                }
                listResult.data?.let { newData.addAll(it) }
                _product.value = newData

                paging = listResult.nextPaging
                _status.value = StylishApiStatus.DONE
                Log.i("tina", "listResult=${listResult}")

            } catch (e: Exception) {
                Log.i("tina", "Exception=${e.message}")
                _product.value = null
                paging = null
                _status.value = StylishApiStatus.ERROR
            }
        }
    }

    fun hasNextPage(): Boolean {
        return paging != null
    }
}