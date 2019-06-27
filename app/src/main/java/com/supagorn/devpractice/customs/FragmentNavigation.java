package com.supagorn.devpractice.customs;

import androidx.fragment.app.Fragment;

public interface FragmentNavigation {

    void open(Fragment fragment);

    void replace(Fragment fragment, boolean addToBackStack);

    void navigateBack();
}
