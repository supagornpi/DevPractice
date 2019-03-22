package com.supagorn.devpractice.customs;

import android.support.v4.view.ViewPager;
import android.view.View;

public class VerticalPageTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View page, float position) {
        if (position < -1) {
            // This page is way off-screen to the left
            page.setAlpha(0);
        } else if (position <= 0) {
            page.setAlpha(1 + position);

            // Counteract the default slide transition
            page.setTranslationX(page.getWidth() * -position);

            // set Y position to swipe in from bottom
            float yPosition = position * page.getHeight();
            page.setTranslationY(yPosition);

        } else if (position <= 1) {
            page.setAlpha(1 - position);

            // Counteract the default slide transition
            page.setTranslationX(page.getWidth() * -position);

            // set Y position to swipe in from top
            float yPosition = position * page.getHeight();
            page.setTranslationY(yPosition);
        } else {
            // This page is way off screen to the right
            page.setAlpha(0);
        }
    }
}