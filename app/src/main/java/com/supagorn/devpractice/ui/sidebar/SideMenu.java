package com.supagorn.devpractice.ui.sidebar;


import android.support.v4.app.Fragment;

public interface SideMenu {
    void openSideMenu();

    void closeSideMenu();

    void openFragment(Fragment fragment);
}
