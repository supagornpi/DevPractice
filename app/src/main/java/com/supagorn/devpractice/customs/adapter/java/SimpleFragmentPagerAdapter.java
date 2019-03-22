package com.supagorn.devpractice.customs.adapter.java;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SimpleFragmentPagerAdapter<T> extends FragmentPagerAdapter {
    private int NUM_ITEMS = 0;
    private List<T> items = new ArrayList<>();
    private OnBindViewListener onBindViewListener;

    public SimpleFragmentPagerAdapter(FragmentManager fragmentManager, List<T> items, OnBindViewListener onBindViewListener) {
        super(fragmentManager);
        this.items = items;
        this.NUM_ITEMS = items.size();
        this.onBindViewListener = onBindViewListener;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        if (onBindViewListener != null) {
            return onBindViewListener.getItem(items.get(position), position);
        } else {
            return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return this.onBindViewListener == null ? "Page " + position : onBindViewListener.getPageTitle(position);
    }


    public interface OnBindViewListener {
        <T> Fragment getItem(T item, int position);

        CharSequence getPageTitle(int position);
    }
}