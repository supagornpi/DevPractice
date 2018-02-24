package com.supagorn.devpractice.ui.sidebar;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.supagorn.devpractice.R;
import com.supagorn.devpractice.databinding.FragmentSidebarBinding;
import com.supagorn.devpractice.utils.GlideLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class SidebarFragment extends Fragment {

    FragmentSidebarBinding binding;

    public SidebarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sidebar, container, false);
        binding = DataBindingUtil.bind(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startAnimation();
    }

    private void startAnimation() {
        binding.imageviewNewPendingGift.startAnimation(
                AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_indefinitely));
        //load image profile in circle
        GlideLoader.Companion.loadImageCircle(
                getActivity().getApplicationContext(),
                R.mipmap.ic_launcher, binding.imgProfileImage);
    }
}
