package student.tina.stylish.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import student.tina.stylish.MainActivity
import student.tina.stylish.R
import student.tina.stylish.adapter.PurchaseAdapter
import student.tina.stylish.database.CartItemsDatabase
import student.tina.stylish.databinding.FragmentPurchaseBinding
import student.tina.stylish.databinding.FragmentWomenBinding
import student.tina.stylish.factory.CartItemsViewModelFactory
import student.tina.stylish.factory.PurchaseViewModelFactory
import student.tina.stylish.view.CartItemsViewModel
import student.tina.stylish.view.PurchaseViewModel

/**
 * A simple [Fragment] subclass.
 */
class PurchaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPurchaseBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_purchase, container, false
        )

        val application = requireNotNull(this.activity).application

        val dataSources = CartItemsDatabase.getInstance(application).cartItemsDatabaseDao

        val viewModelFactory = PurchaseViewModelFactory(dataSources, application)

        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(PurchaseViewModel::class.java)

        binding.purchaseViewModel = viewModel

        binding.lifecycleOwner = this

        binding.recyclerViewPurchase.adapter = PurchaseAdapter(viewModel)

        changeTitleAndHideBottomNavigation()

        (activity as MainActivity).binding.imageBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.allCartItems.observe(this, Observer {
            viewModel.countPriceAndAmount()
        })

        return binding.root
    }

    fun changeTitleAndHideBottomNavigation(){
        (activity as MainActivity).binding.stylishTitle.visibility = View.INVISIBLE
        (activity as MainActivity).binding.bottomNavigation.visibility = View.GONE
        (activity as MainActivity).binding.drawableIcon.visibility = View.INVISIBLE
        (activity as MainActivity).binding.imageBackButton.visibility =View.VISIBLE
        (activity as MainActivity).binding.textView.text = getString(R.string.total_cost)
    }
}
