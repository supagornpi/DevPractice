package com.supagorn.devpractice.ui.setting

import java.util.*

enum class SettingType constructor(val id: Int){

    Normal(0),
    Toggle(1),
    Text(2),
    Custom(3);

    companion object {
        fun parse(typeId: Int): SettingType? {
            val creatorMap = HashMap<Int, SettingType>()
            creatorMap.put(SettingType.Normal.id, SettingType.Normal)
            creatorMap.put(SettingType.Toggle.id, SettingType.Toggle)
            creatorMap.put(SettingType.Text.id, SettingType.Text)
            creatorMap.put(SettingType.Custom.id, SettingType.Custom)
            return creatorMap[typeId]
        }
    }

}