package com.vitech.moodfeed.utils;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

public class Utils {

    private static final Random RANDOM = new Random();

    private Utils() {
    }

    public static <T> T getRandom(Iterable<T> source) {
        List<T> sourceList = Lists.newArrayList(source);
        return sourceList.get(RANDOM.nextInt(sourceList.size()));
    }

}
