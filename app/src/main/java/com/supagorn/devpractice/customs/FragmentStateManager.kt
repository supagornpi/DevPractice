package com.supagorn.devpractice.customs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import android.view.ViewGroup

abstract class FragmentStateManager(private var container: ViewGroup, private val mFragmentManager: androidx.fragment.app.FragmentManager) {

    /**
     * Return the Fragment associated with a specified position.
     */
    abstract fun getItem(position: Int): androidx.fragment.app.Fragment

    /**
     * Shows fragment at position and detaches previous fragment if exists. If fragment is found in
     * fragment manager, it is reattached else added.
     *
     * @param position
     * @return fragment at position
     */
    fun changeFragment(position: Int): androidx.fragment.app.Fragment {
        val tag = makeFragmentName(container.id, getItemId(position))
        val fragmentTransaction = mFragmentManager.beginTransaction()

        /*
          If fragment manager doesn't have an instance of the fragment, get an instance
          and add it to the transaction. Else, attach the instance to transaction.
         */
        var fragment: androidx.fragment.app.Fragment? = mFragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            fragment = getItem(position)
            fragmentTransaction.add(container.id, fragment, tag)
        } else {
            //show fragment
            fragmentTransaction.show(fragment)
        }

        // Detach existing primary fragment
        val curFrag = mFragmentManager.primaryNavigationFragment
        if (curFrag != null) {
            //hide current fragment
            fragmentTransaction.hide(curFrag)
        }

        // Set fragment as primary navigator for child manager back stack to be handled by system
        fragmentTransaction.setPrimaryNavigationFragment(fragment)
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.commitNowAllowingStateLoss()

        return fragment
    }

    /**
     * Removes Fragment from Fragment Manager and clears all saved states. Call to changeFragment()
     * will restart fragment from fresh state.
     *
     * @param position
     */
    fun removeFragment(position: Int) {
        val fragmentTransaction = mFragmentManager.beginTransaction()
        mFragmentManager
                .findFragmentByTag(makeFragmentName(container.id, getItemId(position)))?.let { fragmentTransaction.remove(it) }
        fragmentTransaction.commitNowAllowingStateLoss()
    }

    /**
     * Return a unique identifier for the item at the given position.
     *
     *
     *
     * The default implementation returns the given position.
     * Subclasses should override this method if the positions of items can change.
     *
     * @param position Position within this adapter
     * @return Unique identifier for the item at position
     */
    fun getItemId(position: Int): Long {
        return position.toLong()
    }

    companion object {
        private val TAG = "FragmentStateManager"
        private val DEBUG = false

        private fun makeFragmentName(viewId: Int, id: Long): String {
            return "android:switcher:$viewId:$id"
        }
    }
}
