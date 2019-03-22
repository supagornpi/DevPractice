package com.supagorn.devpractice.enums

import com.supagorn.devpractice.R

enum class Categories(var icon: Int, var title: Int) {

    Video(R.drawable.ic_youtube, R.string.category_video),
    ViewPagerVertical(0, R.string.category_view_pager_vertical),
    FragmentViewPagerVertical(0, R.string.category_fragment_view_pager_vertical)

}