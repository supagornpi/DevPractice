package com.supagorn.devpractice;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by apple on 2/18/2018 AD.
 */

public enum Tabs {
    Home,
    Category,
    Library,
    Setting;

    public static Tabs parse(int type) {
        Map<Integer, Tabs> creatorMap = new HashMap<>();
        creatorMap.put(Home.ordinal(), Home);
        creatorMap.put(Category.ordinal(), Category);
        creatorMap.put(Library.ordinal(), Library);
        creatorMap.put(Setting.ordinal(), Setting);
        return creatorMap.get(type);
    }
}
