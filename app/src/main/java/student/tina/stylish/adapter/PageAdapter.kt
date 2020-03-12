package student.tina.stylish.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import student.tina.stylish.fragment.AccessoryFragment
import student.tina.stylish.fragment.MenFragment
import student.tina.stylish.fragment.WomenFragment

const val TAB_NUMBER = 3

class PageAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return WomenFragment()
            1 -> return MenFragment()
            else -> return AccessoryFragment()

        }

    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> return "女裝"
            1 -> return "男裝"
            else -> return "配件"

        }
    }

    override fun getCount(): Int {
        return TAB_NUMBER
    }


}