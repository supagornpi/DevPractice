package com.supagorn.devpractice.ui.home.pager.detail;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.request.RequestOptions;
import com.supagorn.devpractice.R;
import com.supagorn.devpractice.customs.AbstractFragment;
import com.supagorn.devpractice.customs.FragmentNavigation;
import com.supagorn.devpractice.databinding.FragmentSamplePagerDetailBinding;
import com.supagorn.devpractice.model.SamplePagerEntity;
import com.supagorn.devpractice.utils.GlideLoader;
import com.supagorn.devpractice.utils.ResolutionUtils;

import org.parceler.Parcels;

/**
 * Created by apple on 2/18/2018 AD.
 */

public class SamplePagerDetailFragment extends AbstractFragment {

    private final int CONST_TOOLBAR_HEIGHT_DP = 56; //Height of tabBar dp;
    private FragmentSamplePagerDetailBinding dataBinding;
    private SamplePagerEntity samplePagerEntity;
    private FragmentNavigation fragmentNavigation;

    public static SamplePagerDetailFragment newInstance(SamplePagerEntity samplePagerEntity) {
        SamplePagerDetailFragment fragment = new SamplePagerDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(SamplePagerEntity.class.getSimpleName(), Parcels.wrap(samplePagerEntity));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_sample_pager_detail;
    }

    @Override
    protected void setupView() {
        dataBinding = DataBindingUtil.bind(getView());
        if (getArguments() != null) {
            samplePagerEntity = Parcels.unwrap(getArguments().getParcelable(
                    SamplePagerEntity.class.getSimpleName()));
        }
        if (samplePagerEntity == null) return;
        bindAction();

        dataBinding.tvTitle.setText("Detail");
        dataBinding.appbarLayout.tvTitle.setText("Detail");

        dataBinding.appbarLayout.imgBanner.getLayoutParams().height =
                ResolutionUtils.getBannerHeightFromRatio((getActivity()));
        GlideLoader.Companion.load(getContext(), samplePagerEntity.getImageUrl(),new RequestOptions().centerCrop(),
                dataBinding.appbarLayout.imgBanner);

        setAppBarScrolling();
    }

    private void setAppBarScrolling() {
        dataBinding.appbarLayout.appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int minCollapseValue = appBarLayout.getTotalScrollRange();
                int computerValue = verticalOffset < -1 ? (verticalOffset * -1) : verticalOffset;

                float percent = ((float) (minCollapseValue - computerValue) / minCollapseValue);
                setHeaderContentAlpha(percent);
            }
        });
    }

    private void setHeaderContentAlpha(float percent) {
        dataBinding.tvTitle.setAlpha(1f - percent);
        dataBinding.appbarLayout.tvTitle.setAlpha(percent);
    }

    private void lockContentBelowAppBar() {
        //Set Height toolbar for lock parallax scroll when run time
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final ViewGroup.LayoutParams layoutToolbar = dataBinding.appbarLayout.toolbar.getLayoutParams();
        dataBinding.layoutContent.post(new Runnable() {
            public void run() {
                double layoutUserFriendHeight = dataBinding.layoutContent.getHeight();
                int heightOfTabbar = (int) ResolutionUtils.dip2px(getActivity().getApplicationContext(),
                        CONST_TOOLBAR_HEIGHT_DP);
                int minusContentHeight = (int) (layoutUserFriendHeight + heightOfTabbar);
                layoutToolbar.height = displayMetrics.heightPixels - minusContentHeight;
                dataBinding.appbarLayout.toolbar.setLayoutParams(layoutToolbar);
            }
        });
    }

    private void bindAction() {
        dataBinding.appbarLayout.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentNavigation.navigateBack();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getParentFragment() != null && getParentFragment() instanceof FragmentNavigation) {
            fragmentNavigation = (FragmentNavigation) getParentFragment();
        }
    }
}
