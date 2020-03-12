package student.tina.stylish.`object`

import android.content.Context
import android.content.SharedPreferences
import com.facebook.login.LoginResult
import android.content.Context.MODE_PRIVATE
import student.tina.stylish.MyApplication


object UserManager {

    const val SETTING = "appSetting"
    const val ACCESS_TOKEN = ""
    const val USER_NAME = ""
    const val PROFILE_PICTURE=""

    var userToken: String?
        get() {
            val sharedPreferences =
                MyApplication.appContext.getSharedPreferences(SETTING, MODE_PRIVATE)
            return sharedPreferences.getString(ACCESS_TOKEN, null)
        }
        set(value) {
            val setting = MyApplication.appContext.getSharedPreferences(SETTING, 0)
            setting.edit()
                .putString(ACCESS_TOKEN, value)
                .apply()
        }

    fun isLogin() : Boolean{
        if (userToken != null)
           return true
        else return false}



}