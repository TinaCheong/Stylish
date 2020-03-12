package student.tina.stylish.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import student.tina.stylish.NavigationDirections
import student.tina.stylish.R
import student.tina.stylish.adapter.CatalogAdapter
import student.tina.stylish.databinding.FragmentHomeBinding
import student.tina.stylish.databinding.FragmentWomenBinding
import student.tina.stylish.view.CatalogWomenViewModel

/**
 * A simple [Fragment] subclass.
 */
class WomenFragment : Fragment() {


    private lateinit var adapter: CatalogAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentWomenBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_women, container, false
        )
        adapter = CatalogAdapter(CatalogAdapter.OnClickListener {
            this.findNavController().navigate(NavigationDirections.actionGlobalProductFragment(it))
        })
        binding.recyclerViewGrid.adapter = adapter
        binding.viewModel = CatalogWomenViewModel()
        binding.lifecycleOwner = this

        binding.recyclerViewGrid.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                binding.viewModel?.let {
                    Log.i("Catalog", "PRODUCT = ${it.paging}")
                    if (!recyclerView.canScrollVertically(1) && it.hasNextPage() && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        it.getWomenCatalogProduct()
                    }
                }
            }
        })


        // Inflate the layout for this fragment
        return binding.root
    }


}
