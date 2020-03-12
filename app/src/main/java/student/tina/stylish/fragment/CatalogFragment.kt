package student.tina.stylish.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import student.tina.stylish.MainActivity
import student.tina.stylish.R
import student.tina.stylish.adapter.PageAdapter
import student.tina.stylish.databinding.FragmentCatalogBinding
import student.tina.stylish.databinding.FragmentHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CatalogFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CatalogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CatalogFragment : Fragment() {

    private lateinit var adapter: PagerAdapter
    private lateinit var binding: FragmentCatalogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).changeToolbar(MainActivity.ButtonItem.Catalog)

        binding= DataBindingUtil.inflate(
            inflater, R.layout.fragment_catalog, container, false)

        changePages()

        showToolbarAndBottomNavigation()

        return binding.root
    }

    fun changePages() {

        adapter = PageAdapter(childFragmentManager)
        binding.catalogViewPager.adapter = adapter
        binding.catalogViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))

    }

    fun showToolbarAndBottomNavigation(){
        (activity as MainActivity).binding.bottomNavigation.visibility = View.VISIBLE
        (activity as MainActivity).binding.toolbar.visibility=View.VISIBLE
    }
}
