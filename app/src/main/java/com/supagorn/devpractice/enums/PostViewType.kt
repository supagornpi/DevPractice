package com.supagorn.devpractice.enums

import java.util.*

enum class PostViewType {
    Caption,
    Image,
    Empty,
    Loading;

    companion object {
        fun parse(type: Int): PostViewType {
            val creatorMap = HashMap<Int, PostViewType>()
            creatorMap[Caption.ordinal] = Caption
            creatorMap[Image.ordinal] = Image
            creatorMap[Empty.ordinal] = Empty
            creatorMap[Loading.ordinal] = Loading
            return if (creatorMap[type] == null) Empty else creatorMap[type]!!
        }
    }
}