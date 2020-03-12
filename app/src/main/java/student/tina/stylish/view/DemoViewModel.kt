package student.tina.stylish.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import student.tina.stylish.network.RetrofitApi
import student.tina.stylish.adapter.DataItem


/**
 * TODO(07)
 * The [ViewModel] that is attached to the [DemoFragment].
 *
 * implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.0"
 * implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.1.0"
 */
class DemoViewModel : ViewModel() {

    // Internally, we use a MutableLiveData, because we will be updating the List of DataItem
    // with new values
    private val _products = MutableLiveData<List<DataItem>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val products: LiveData<List<DataItem>>
        get() = _products

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Call getProperties() on init so we can display status immediately.
     */
    init {
        getProperties()
    }

    /**
     * TODO(08)
     * Gets hots property information from the Marketing Hots API Retrofit service and
     * updates the [Property Object]. The Retrofit service
     * returns a coroutine Deferred, which we await to get the result of the transaction.
     */
     private fun getProperties() {
        coroutineScope.launch {

            var getPropertiesDeferred = RetrofitApi.retrofitService.getMarketingHots()
            try {
                // this will run on a thread managed by Retrofit
                val listResult = getPropertiesDeferred.await()
                _products.value = listResult.toHomeItems()
                Log.i("I/Demo","${listResult.toHomeItems()}")
            } catch (e: Exception) {
                Log.i("Demo", "exception=${e.message}")
                _products.value = ArrayList()
            }
        }
    }

    /**
     * TODO(09)
     * For ViewModel and LiveData
     * implementation "androidx.lifecycle:lifecycle-extensions:2.0.0"
     */
}
