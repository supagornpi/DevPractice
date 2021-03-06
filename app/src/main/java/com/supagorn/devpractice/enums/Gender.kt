package com.supagorn.devpractice.enums

import com.google.gson.annotations.SerializedName
import java.util.*

enum class Gender {

    @SerializedName("0")
    None,
    @SerializedName("1")
    Male,
    @SerializedName("2")
    Female;

    companion object {
        fun parse(type: Int): Gender {
            val creatorMap = HashMap<Int, Gender>()
            creatorMap[Male.ordinal] = Male
            creatorMap[Female.ordinal] = Female
            return if (creatorMap[type] == null) None else creatorMap[type]!!
        }
    }
}