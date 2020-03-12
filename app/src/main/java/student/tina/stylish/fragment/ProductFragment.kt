package student.tina.stylish.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import student.tina.stylish.MainActivity
import student.tina.stylish.R
import student.tina.stylish.adapter.CatalogColorAdapter
import student.tina.stylish.adapter.CatalogImageAdapter
import student.tina.stylish.databinding.FragmentProductBinding
import student.tina.stylish.factory.CatalogProductViewModelFactory
import student.tina.stylish.view.CatalogProductViewModel

/**
 * A simple [Fragment] subclass.
 */
class ProductFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProductBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_product, container, false
        )
        binding.detailProductLinear.adapter=CatalogImageAdapter()
        binding.colorBlockLinear.adapter=CatalogColorAdapter()

        val application = requireNotNull(activity).application
        val product = ProductFragmentArgs.fromBundle(arguments!!).selectedProduct
        val viewModelFactory = CatalogProductViewModelFactory(product, application)

        binding.viewModel = ViewModelProviders.of(
            this, viewModelFactory).get(CatalogProductViewModel::class.java)

        binding.lifecycleOwner = this

        //when the button is clicked, back to the previous page
        binding.backButton.setOnClickListener { findNavController().navigateUp() }

        //check the scrolling position to decide showing next object or not
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.detailProductLinear)

        hideToolbarAndBottomNavigation()

        binding.addCart.setOnClickListener {
            val detailDialog = AddToCartDialogFragment(product)
            detailDialog.show(fragmentManager!!, "AddToCartDialogFragment")
        }




        // Inflate the layout for this fragment
        return binding.root
    }

    fun hideToolbarAndBottomNavigation(){
        (activity as MainActivity).binding.stylishTitle.visibility=View.GONE
        (activity as MainActivity).binding.toolbar.visibility=View.GONE
        (activity as MainActivity).binding.bottomNavigation.visibility=View.GONE
    }

}
