package student.tina.stylish

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.facebook_login_page.*
import student.tina.stylish.databinding.ActivityMainBinding
import java.util.*
import com.facebook.AccessToken
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Handler
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProviders
import student.tina.stylish.`object`.UserManager
import student.tina.stylish.`object`.UserSignIn
import student.tina.stylish.fragment.ProfileFragment
import student.tina.stylish.network.RetrofitApi
import student.tina.stylish.network.RetrofitApiService
import student.tina.stylish.view.DemoViewModel
import student.tina.stylish.view.MainActivityViewModel
import student.tina.stylish.view.StylishAccessResult


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    enum class ButtonItem { Home, Catalog, Cart, Profile }

    lateinit var context: Context

    val callbackManager = CallbackManager.Factory.create()

    private val mainViewModel: MainActivityViewModel by lazy {
        ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        //binding the activityLayout to get properties easier
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
//        val navController = findNavController(R.id.myNavHostFragment)
//        binding.bottomNavigation.setupWithNavController(navController)

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.profileFragment -> {
                    when (UserManager.isLogin()) {
                        true -> {
                            findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_profileFragment)
                            true
                        }
                        false -> {
                            showBottomSheetDialog()
                            false
                        }
                    }
                }
                R.id.homeFragment -> {
                    findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_homeFragment)
                    true
                }
                R.id.cartFragment -> {
                    findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_cartFragment)
                    true
                }
                R.id.catalogFragment -> {
                    findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_catalogFragment)
                    true
                }
                else -> false
            }
        }


        mainViewModel.userSignInData.observe(this, androidx.lifecycle.Observer
        {
            if (it != null) {
                UserManager.userToken = it.accessToken
                Log.i("tina", "getStylishToken=${UserManager.userToken}")
            }
        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun showBottomSheetDialog() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.facebook_login_page, null)
        val close = view.findViewById<ImageView>(R.id.close_image)
        val loginButton = view.findViewById<Button>(R.id.facebook_button)
        loginButton.setOnClickListener {

            LoginManager.getInstance()
                .logInWithReadPermissions(
                    this,
                    Arrays.asList("public_profile", "user_birthday", "email")
                )
        }

        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    Log.i("tina", "FB Login Success, result=${result!!.accessToken.token}")
                    result.accessToken.token?.let {
                        // call user sign in api
                        mainViewModel.getApiToken(it)
                    }
                    dialog.dismiss()
                    showLoginSuccessDialog()
                    Handler().postDelayed({ //After 1 second, jump to the Profile Fragment
                        jumpToProfileFragment()
                    }, 1000)


                }

                override fun onCancel() {
                    Toast.makeText(this@MainActivity, "Cancel Login", Toast.LENGTH_SHORT).show()
                }

                override fun onError(error: FacebookException?) {
                    Toast.makeText(this@MainActivity, "Login Error!", Toast.LENGTH_SHORT).show()
                }
            })
        //when the close button press, dismiss the dialog
        close.setOnClickListener { dialog.dismiss() }
        dialog.setContentView(view)
        dialog.show()
    }

    


    //change toolbar title
    fun changeToolbar(type: ButtonItem) {
        when (type) {
            ButtonItem.Home -> {
                binding.textView.visibility = View.INVISIBLE //when user press the home button then hide the textView
                binding.stylishTitle.visibility = View.VISIBLE //and show the ImageView(StylishTitle)
                binding.imageBackButton.visibility = View.INVISIBLE
            }


            ButtonItem.Catalog -> {
                binding.stylishTitle.visibility = View.INVISIBLE // same logic as above
                binding.textView.visibility = View.VISIBLE
                binding.textView.text = getString(R.string.toolbar_catalog) //change the TextView title
                binding.imageBackButton.visibility = View.INVISIBLE

            }
            ButtonItem.Cart -> {
                binding.stylishTitle.visibility = View.INVISIBLE
                binding.textView.visibility = View.VISIBLE
                binding.textView.text = getString(R.string.toolbar_cart)
                binding.imageBackButton.visibility = View.INVISIBLE
                binding.drawableIcon.visibility = View.VISIBLE
            }
            ButtonItem.Profile -> {
                binding.stylishTitle.visibility = View.INVISIBLE
                binding.textView.visibility = View.VISIBLE
                binding.textView.text = getString(R.string.toolbar_profile)
                binding.imageBackButton.visibility = View.INVISIBLE

            }
        }
    }
    fun showLoginSuccessDialog(){
        val successDialog= AppCompatDialog(this)
        successDialog.setContentView(R.layout.facebook_login_success)
        successDialog.setCancelable(false) //cannot cancel by click
        successDialog.show()
        Handler().postDelayed({  //After 1 second, dialog dismiss
            successDialog.dismiss()
        }, 1000)
    }
    fun jumpToProfileFragment(){
        binding.bottomNavigation.selectedItemId = R.id.profileFragment //selected button
        findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_profileFragment)
    }

}



