package com.supagorn.devpractice

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import com.supagorn.devpractice.constants.AppEventsConstants
import com.supagorn.devpractice.customs.BaseActivity
import com.supagorn.devpractice.customs.FragmentStateManager
import com.supagorn.devpractice.singleton.AppEventLogger
import com.supagorn.devpractice.ui.sidebar.SideMenu
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), SideMenu {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var fragmentStateManager: FragmentStateManager? = null

        fun start() {
            val intent = Intent(MyApplication.instance, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            MyApplication.instance.startActivity(intent)
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val position = getNavPositionFromMenuItem(item)
        //lock side bar
        drawerLayout.setDrawerLockMode(if (position == 0)
            DrawerLayout.LOCK_MODE_UNLOCKED else DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

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
                    0 -> HolderFragment.newInstance(Tabs.Home)
                    1 -> HolderFragment.newInstance(Tabs.Category)
                    2 -> HolderFragment.newInstance(Tabs.Library)
                    3 -> HolderFragment.newInstance(Tabs.Setting)
                    else -> HolderFragment.newInstance(Tabs.Home)
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

    override fun openSideMenu() {
        AppEventLogger.logEvent(AppEventsConstants.EVENT_NAME_OPEN_SIDE_BAR)
        drawerLayout.openDrawer(Gravity.END)
    }

    override fun closeSideMenu() {
        drawerLayout.closeDrawer(Gravity.END)
    }

    override fun openFragment(fragment: Fragment?) {

    }
}
