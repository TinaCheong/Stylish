package student.tina.stylish.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import student.tina.stylish.MyApplication
import student.tina.stylish.`object`.User
import student.tina.stylish.`object`.UserManager
import student.tina.stylish.`object`.UserSignIn
import student.tina.stylish.network.RetrofitApi

class MainActivityViewModel : ViewModel() {
    private val _userSignInData = MutableLiveData<UserSignIn>()
    val userSignInData: LiveData<UserSignIn>
        get() = _userSignInData
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    fun getApiToken(fbToken: String) {
        coroutineScope.launch {
            var getPropertiesDeferred = RetrofitApi.retrofitService.setUserLogin(token = fbToken)
            try {
                val listResult = getPropertiesDeferred.await()
                _userSignInData.value = listResult.data
                Log.i("tina", "listResult=${listResult}")

            } catch (e: Exception) {
                Log.i("tina", "Exception=${e.message}")
                _userSignInData.value = null
            }
        }
    }
}