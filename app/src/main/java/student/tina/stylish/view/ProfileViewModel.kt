package student.tina.stylish.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import student.tina.stylish.`object`.User
import student.tina.stylish.`object`.UserManager
import student.tina.stylish.`object`.UserProfile
import student.tina.stylish.`object`.UserSignIn
import student.tina.stylish.network.RetrofitApi

class ProfileViewModel : ViewModel(){
    private val _userProfileData = MutableLiveData<User>()
    val userProfileData: LiveData<User>
        get() = _userProfileData
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getStylishProfileToken()
    }

    fun getStylishProfileToken() {
        coroutineScope.launch {
            var getPropertiesDeferred = RetrofitApi.retrofitService.getUserProfile("Bearer ${UserManager.userToken}")
            try {
                val listResult = getPropertiesDeferred.await()
                _userProfileData.value = listResult.data
                Log.i("tina", "listResult=${listResult}")

            } catch (e: Exception) {
                Log.i("tina", "Exception=${e.message}")
                _userProfileData.value = null
            }
        }
    }
}
