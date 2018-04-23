package com.supagorn.devpractice.utils

object YoutubeLinkUtils {

    fun getImageUrl(url: String): String {
        var youtubeId = url
        val link = youtubeId.split("/")
        youtubeId = link[link.size - 1]
        return "https://img.youtube.com/vi/$youtubeId/mqdefault.jpg"
    }

    fun getYoutubeId(url: String): String {
        var youtubeId = url
        val link = youtubeId.split("/")
        youtubeId = link[link.size - 1]
        return youtubeId
    }
}