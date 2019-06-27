package com.supagorn.devpractice.ui.home.pager.adapter;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.supagorn.devpractice.R;
import com.supagorn.devpractice.model.SamplePagerEntity;
import com.supagorn.devpractice.utils.GlideLoader;
import com.supagorn.devpractice.utils.ResolutionUtils;

import java.util.ArrayList;

public class SamplePagerAdapter extends PagerAdapter {

    private ArrayList<SamplePagerEntity> samplePagerEntities = new ArrayList<>();
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
        View view = inflater.inflate(R.layout.layout_item_sample_pager, null);
        ImageView imgBanner = view.findViewById(R.id.imgBanner);
        //set banner height
        imgBanner.getLayoutParams().height = ResolutionUtils.getBannerHeightFromRatio((mContext));
        final SamplePagerEntity entity = samplePagerEntities.get(position);
        GlideLoader.Companion.load(mContext, entity.getImageUrl(),
                new RequestOptions().centerCrop(), imgBanner);
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

    public void setSamplePagerEntities(ArrayList<SamplePagerEntity> samplePagerEntities) {
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