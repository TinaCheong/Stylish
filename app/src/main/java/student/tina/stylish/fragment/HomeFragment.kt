package student.tina.stylish.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import student.tina.stylish.*
import student.tina.stylish.`object`.Product
import student.tina.stylish.adapter.DataItem
import student.tina.stylish.adapter.StylishAdapter
import student.tina.stylish.databinding.FragmentHomeBinding
import student.tina.stylish.view.DemoViewModel


/**
 * A simple [Fragment] subclass.
 *
 */


class HomeFragment : Fragment() {

    private val viewModel: DemoViewModel by lazy{
        ViewModelProviders.of(this).get(DemoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //binding the xml file to get the adapter(in order to link our StylishAdapter
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        //change tool bar title function(chane the activity to MainActivity to get the function)
        (activity as MainActivity).changeToolbar(MainActivity.ButtonItem.Home)
        //create an Adapter Object
        val adapter = StylishAdapter()

        //create some mock data let submitList to read (define which layouts is
//        val list = listOf<DataItem>(
//            DataItem.Header("Good"),
//            DataItem.ProductItem1(
//                Product(1,
//                    "a",
//                    "a",
//                    1,
//                    "a",
//                    "a",
//                    "a",
//                    "a",
//                    "a",
//                    listOf(),
//                    listOf(),
//                    listOf(),
//                    "a",
//                    listOf())), DataItem.ProductItem2(
//                Product(2,
//                        "b",
//                        "b",
//                        1,
//                        "a",
//                        "a",
//                        "a",
//                        "a",
//                        "a",
//                        listOf(),
//                        listOf(),
//                        listOf(),
//                        "a",
//                        listOf()
//            )
//            ))

        //implement the recycleView adapter to our custom Adapter
        binding.recyclerViewLinear.adapter=adapter
        //use submitList to access the mock data
        binding.viewModel = viewModel


        return binding.root
    }


}
