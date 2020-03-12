package student.tina.stylish.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import student.tina.stylish.R
import student.tina.stylish.adapter.CatalogAdapter
import student.tina.stylish.databinding.FragmentAccessoryBinding
import student.tina.stylish.databinding.FragmentMenBinding
import student.tina.stylish.view.CatalogAccessoryViewModel
import student.tina.stylish.view.CatalogMenViewModel
import student.tina.stylish.view.ProfileViewModel
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import student.tina.stylish.NavigationDirections


/**
 * A simple [Fragment] subclass.
 */
class AccessoryFragment : Fragment() {

    //    private val catalogAccessoryViewModel: CatalogAccessoryViewModel by lazy {
//        ViewModelProviders.of(this).get(CatalogAccessoryViewModel::class.java)
//    }
//
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAccessoryBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_accessory, container, false
        )
        val adapter = CatalogAdapter(CatalogAdapter.OnClickListener {
            findNavController().navigate(NavigationDirections.actionGlobalProductFragment(it))
        })
        binding.recyclerViewGrid.adapter = adapter
        binding.viewModel = CatalogAccessoryViewModel()
        binding.lifecycleOwner = this

        binding.recyclerViewGrid.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                binding.viewModel?.let {
                    if (!recyclerView.canScrollVertically(1) && it.hasNextPage() && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        it.getAccessoryCatalogProduct()
                    }
                }
            }
        })

        // Inflate the layout for this fragment
        return binding.root
    }


}
