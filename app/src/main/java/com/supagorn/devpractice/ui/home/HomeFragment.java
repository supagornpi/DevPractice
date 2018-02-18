package com.supagorn.devpractice.ui.home;

import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.supagorn.devpractice.R;
import com.supagorn.devpractice.customs.AbstractFragment;
import com.supagorn.devpractice.model.SamplePagerEntity;
import com.supagorn.devpractice.ui.home.pager.SamplePagerView;
import com.supagorn.devpractice.utils.JsonUtils;
import com.supagorn.devpractice.utils.ResolutionUtils;

import java.util.ArrayList;

/**
 * Created by apple on 2/18/2018 AD.
 */

public class HomeFragment extends AbstractFragment {

    private FrameLayout samplePagerView;

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void setupView() {
        bindUI(getView());
        setTitle(R.string.title_home);

        setSampleViewPager();
    }

    private void bindUI(View view) {
        if (view == null) {
            return;
        }
        samplePagerView = view.findViewById(R.id.samplePagerView);
    }

    private void setSampleViewPager() {
        SamplePagerView samplePagerView = new SamplePagerView(getActivity());
        //get json from asset
        String jsonString = new JsonUtils().loadJSONFromAsset(getActivity(), "json/banner.json");
        ArrayList<SamplePagerEntity> samplePagerEntities = new Gson().fromJson(jsonString, new TypeToken<ArrayList<SamplePagerEntity>>() {
        }.getType());
        //set fragmentNavigation
        samplePagerView.setNavigation(getFragmentNavigation());
        samplePagerView.setSampleEntity(samplePagerEntities);
        //set banner Height
        this.samplePagerView.getLayoutParams().height = ResolutionUtils.getBannerHeightFromRatio((getActivity()));
        this.samplePagerView.addView(samplePagerView);
    }
}
