package com.supagorn.devpractice

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.drawerlayout.widget.DrawerLayout
import android.view.Gravity
import android.view.MenuItem
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
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
            androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED else androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

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
            override fun getItem(position: Int): androidx.fragment.app.Fragment {
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

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.setOnNavigationItemReselectedListener(mOnNavigationItemReselectedListener)

        navigation.enableShiftingMode(false)
        navigation.enableItemShiftingMode(false)
        navigation.setTextVisibility(false)
        // set icon size
        val iconSize = 32f
        navigation.setIconSize(iconSize, iconSize)
        // set item height
        navigation.itemHeight = BottomNavigationViewEx.dp2px(this, (iconSize + 16))

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

    override fun openSideMenu() {
        AppEventLogger.logEvent(AppEventsConstants.EVENT_NAME_OPEN_SIDE_BAR)
        drawerLayout.openDrawer(Gravity.END)
    }

    override fun closeSideMenu() {
        drawerLayout.closeDrawer(Gravity.END)
    }

    override fun openFragment(fragment: androidx.fragment.app.Fragment?) {

    }
}
