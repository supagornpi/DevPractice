package com.supagorn.devpractice.enums

import java.util.*

enum class FeedViewType {
    ProfileHeader,
    Image,
    Gallery,
    Video,
    Comment,
    Loading,
    Unknown;

    companion object {
        fun parse(type: Int): FeedViewType {
            val creatorMap = HashMap<Int, FeedViewType>()
            creatorMap[ProfileHeader.ordinal] = ProfileHeader
            creatorMap[Image.ordinal] = Image
            creatorMap[Gallery.ordinal] = Gallery
            creatorMap[Video.ordinal] = Video
            creatorMap[Comment.ordinal] = Comment
            creatorMap[Loading.ordinal] = Loading
            creatorMap[Unknown.ordinal] = Unknown
            return if (creatorMap[type] == null) Unknown else creatorMap[type]!!
        }
    }
}