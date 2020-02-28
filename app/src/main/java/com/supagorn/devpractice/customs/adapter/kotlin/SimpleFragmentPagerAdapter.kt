package com.supagorn.devpractice.customs.adapter.kotlin

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SimpleFragmentPagerAdapter<T> : FragmentPagerAdapter {
    private var NUM_ITEMS = 0
    private var items: List<T> = ArrayList()
    private var onBindViewListener: OnBindViewListener? = null

    constructor(
        fragmentManager: FragmentManager,
        items: List<T>,
        onBindViewListener: OnBindViewListener
    ) : super(fragmentManager) {
        this.items = items
        this.NUM_ITEMS = items.size
        this.onBindViewListener = onBindViewListener

    }

    // Returns total number of pages
    override fun getCount(): Int {
        return NUM_ITEMS
    }

    // Returns the fragment to display for that page
    override fun getItem(position: Int): Fragment {
        return if (onBindViewListener != null) {
            onBindViewListener!!.getItem(items[position], position)
        } else {
            Fragment()
        }
    }

    // Returns the page title for the top indicator
    override fun getPageTitle(position: Int): CharSequence? {
        return if (this.onBindViewListener == null) "Page $position" else onBindViewListener!!.getPageTitle(
            position
        )
    }


    interface OnBindViewListener {
        fun <T> getItem(item: T, position: Int): Fragment

        fun getPageTitle(position: Int): CharSequence
    }
}