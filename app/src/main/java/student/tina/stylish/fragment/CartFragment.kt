package student.tina.stylish.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import student.tina.stylish.MainActivity
import student.tina.stylish.NavigationDirections
import student.tina.stylish.R
import student.tina.stylish.adapter.CartItemsAdapter
import student.tina.stylish.database.CartItemsDatabase
import student.tina.stylish.databinding.FragmentCartBinding
import student.tina.stylish.factory.CartItemsViewModelFactory
import student.tina.stylish.view.AddToCartViewModel
import student.tina.stylish.view.CartItemsViewModel

/**
 * A simple [Fragment] subclass.
 */
class CartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var binding : FragmentCartBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)

        (activity as MainActivity).changeToolbar(MainActivity.ButtonItem.Cart)

        val application = requireNotNull(this.activity).application

        val dataSources = CartItemsDatabase.getInstance(application).cartItemsDatabaseDao

        val viewModelFactory = CartItemsViewModelFactory(dataSources, application)

//        val cartItemsViewModel = ViewModelProviders.of(this, viewModelFactory).get(CartItemsViewModel::class.java)

        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(CartItemsViewModel::class.java)

        binding.cartItemsViewModel = viewModel

        binding.lifecycleOwner = this

        binding.recyclerViewCart.adapter = CartItemsAdapter(viewModel)

        viewModel.allCartItems.observe(this, Observer {
            Log.i("Tina", "product.remove, it = ${it}")
        })

        binding.paymentButton.setOnClickListener { findNavController().navigate(NavigationDirections.actionGlobalPurchaseFragment()) }



        return binding.root
    }


}
