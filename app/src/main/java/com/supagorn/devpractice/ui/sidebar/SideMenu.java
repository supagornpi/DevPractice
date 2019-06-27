package com.supagorn.devpractice.ui.sidebar;


import androidx.fragment.app.Fragment;

public interface SideMenu {
    void openSideMenu();

    void closeSideMenu();

    void openFragment(Fragment fragment);
}
