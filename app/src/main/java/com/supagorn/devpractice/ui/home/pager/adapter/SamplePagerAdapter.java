package com.supagorn.devpractice.ui.home.pager.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.supagorn.devpractice.R;
import com.supagorn.devpractice.model.SamplePagerEntity;
import com.supagorn.devpractice.utils.GlideLoader;
import com.supagorn.devpractice.utils.ResolutionUtils;

import java.util.ArrayList;
import java.util.List;

public class SamplePagerAdapter extends PagerAdapter {

    private List<SamplePagerEntity> samplePagerEntities = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public SamplePagerAdapter() {
        super();
    }

    @Override
    public int getCount() {
        return samplePagerEntities.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        Context mContext = container.getContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view  = inflater.inflate(R.layout.layout_item_sample_pager, container);
        ImageView imgBanner = view.findViewById(R.id.imgBanner);
        //set banner height
        imgBanner.getLayoutParams().height = ResolutionUtils.getBannerHeightFromRatio((mContext));
        final SamplePagerEntity entity = samplePagerEntities.get(position);
        GlideLoader.Companion.load(mContext, entity.getImageUrl(), imgBanner);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener == null) {
                    return;
                }
                onItemClickListener.onItemClicked(entity);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void setSamplePagerEntities(List<SamplePagerEntity> samplePagerEntities) {
        this.samplePagerEntities = samplePagerEntities;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClicked(SamplePagerEntity samplePagerEntity);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}