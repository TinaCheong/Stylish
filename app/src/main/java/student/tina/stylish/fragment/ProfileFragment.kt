package student.tina.stylish.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_profile.*
import student.tina.stylish.MainActivity
import student.tina.stylish.MyApplication
import student.tina.stylish.R
import student.tina.stylish.`object`.UserManager
import student.tina.stylish.`object`.UserSignIn
import student.tina.stylish.databinding.FragmentHomeBinding
import student.tina.stylish.databinding.FragmentProfileBinding
import student.tina.stylish.view.MainActivityViewModel
import student.tina.stylish.view.ProfileViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ProfileFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by lazy {
        ViewModelProviders.of(this).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProfileBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_profile, container, false
        )

        (activity as MainActivity).changeToolbar(MainActivity.ButtonItem.Profile)

        binding.profileFragment = this

        profileViewModel.userProfileData.observe(this, Observer {
            val imgUri = it.picture.toUri().buildUpon().scheme("https").build()
            context?.let {
                Glide.with(it)
                    .load(imgUri)
                    .apply(RequestOptions.circleCropTransform())
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.bottom_profile_selector)
                            .error(R.drawable.bottom_profile_selector)
                    )
                    .into(binding.profilePic)

            }
            binding.userName.text = it.name
        })

        return binding.root
    }


    fun onButtonClick(view: View) {
        showToast(
            when (view.id) {
                R.id.purse_icon, R.id.purse_text -> purse_text.text

                R.id.box_icon, R.id.box_text -> box_text.text

                R.id.car_icon, R.id.car_text -> car_text.text

                R.id.comment_icon, R.id.comment_text -> comment_text.text

                R.id.product_icon, R.id.product_text -> product_text.text

                R.id.star_icon, R.id.star_text -> star_text.text

                R.id.ring_icon, R.id.ring_text -> ring_text.text

                R.id.money_icon, R.id.money_text -> money_text.text

                R.id.place_icon, R.id.place_text -> place_text.text

                R.id.headphone_icon, R.id.headphone_text -> headphone_text.text

                R.id.feedback_icon, R.id.feedback_text -> feedback_text.text

                R.id.cellphone_icon, R.id.cellphone_text -> cellphone_text.text

                R.id.setting_icon, R.id.setting_text -> setting_text.text

                R.id.all_function, R.id.arrow -> all_function.text

                else -> ""
            }
        )
    }


    fun showToast(title: CharSequence) {
        Toast.makeText(context, "<$title> COMING SOON", Toast.LENGTH_LONG).show()
    }


}




