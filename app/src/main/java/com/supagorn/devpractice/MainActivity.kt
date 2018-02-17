package com.supagorn.devpractice

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.util.Log
import android.view.MenuItem
import com.supagorn.devpractice.customs.FragmentStateManager
import com.supagorn.devpractice.ui.SampleFragment
import com.supagorn.devpractice.ui.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var fragmentStateManager: FragmentStateManager? = null
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val position = getNavPositionFromMenuItem(item)

        if (position != -1) {
            fragmentStateManager?.changeFragment(getNavPositionFromMenuItem(item))
            return@OnNavigationItemSelectedListener true
        }

        false
    }

    private val mOnNavigationItemReselectedListener = BottomNavigationView.OnNavigationItemReselectedListener { item ->
        val position = getNavPositionFromMenuItem(item)
        if (position != -1) {
            fragmentStateManager?.removeFragment(position)
            fragmentStateManager?.changeFragment(position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentStateManager = object : FragmentStateManager(main_content, supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> HomeFragment()
                    1 -> SampleFragment.newInstance(baseContext.resources.getString(R.string.title_customs))
                    2 -> SampleFragment.newInstance(baseContext.resources.getString(R.string.title_library))
                    3 -> SampleFragment.newInstance(baseContext.resources.getString(R.string.title_setting))
                    else -> SampleFragment.newInstance(baseContext.resources.getString(R.string.title_home))
                }
            }
        }

        if (savedInstanceState == null) {
            (fragmentStateManager as FragmentStateManager).changeFragment(0)
        }

        removeShiftMode(navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.setOnNavigationItemReselectedListener(mOnNavigationItemReselectedListener)
    }

    private fun getNavPositionFromMenuItem(menuItem: MenuItem): Int {
        return when (menuItem.itemId) {
            R.id.navigation_home -> 0
            R.id.navigation_customs -> 1
            R.id.navigation_library -> 2
            R.id.navigation_setting -> 3
            else -> -1
        }
    }

    @SuppressLint("RestrictedApi")
    private fun removeShiftMode(view: BottomNavigationView) {
        val menuView = view.getChildAt(0) as BottomNavigationMenuView
        try {
            val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
            shiftingMode.isAccessible = true
            shiftingMode.setBoolean(menuView, false)
            shiftingMode.isAccessible = false
            for (i in 0 until menuView.childCount) {
                val item = menuView.getChildAt(i) as BottomNavigationItemView
                item.setShiftingMode(false)
                // set once again checked value, so view will be updated
                item.setChecked(item.itemData.isChecked)
            }
        } catch (e: NoSuchFieldException) {
            Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field")
        } catch (e: IllegalAccessException) {
            Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode")
        }

    }
}
