package com.supagorn.devpractice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.supagorn.devpractice.customs.FragmentNavigation;
import com.supagorn.devpractice.ui.SampleFragment;
import com.supagorn.devpractice.ui.home.HomeFragment;
import com.supagorn.devpractice.ui.library.LibraryFragment;
import com.supagorn.devpractice.ui.setting.SettingFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HolderFragment extends Fragment implements FragmentNavigation {
    private final static String TAG = HolderFragment.class.getName();

    FrameLayout holderFrame;

    public static HolderFragment newInstance(Tabs tabs) {
        HolderFragment fragment = new HolderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Tabs.class.getSimpleName(), tabs.ordinal());
        fragment.setArguments(bundle);
        return fragment;
    }

    public HolderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_holder, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        holderFrame = view.findViewById(R.id.holderFrame);
        Tabs tabs = Tabs.parse(getArguments().getInt(Tabs.class.getSimpleName(), 0));

        if (getChildFragmentManager().findFragmentById(R.id.holderFrame) == null) {
            Fragment fragment = null;
            switch (tabs) {
                case Home:
                    fragment = new HomeFragment();
                    break;
                case Category:
                    fragment = SampleFragment.Companion.newInstance(getActivity().getResources().getString(R.string.title_customs));
                    break;
                case Library:
                    fragment = new LibraryFragment();
                    break;
                case Setting:
                    fragment = new SettingFragment();
                    break;
                default:
                    break;
            }

            getChildFragmentManager().beginTransaction()
                    .replace(R.id.holderFrame, fragment)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void open(Fragment fragment) {
        getChildFragmentManager().beginTransaction()
                .addToBackStack(null)
                .setReorderingAllowed(true)
                .replace(R.id.holderFrame, fragment)
                .commit();
    }

    @Override
    public void replace(Fragment fragment, boolean addToBackStack) {
        if (addToBackStack) {
            getChildFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .setReorderingAllowed(true)
                    .replace(R.id.main_content, fragment)
                    .commitAllowingStateLoss();
        } else {
            getChildFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.main_content, fragment)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void navigateBack() {
        getActivity().onBackPressed();
    }
}
