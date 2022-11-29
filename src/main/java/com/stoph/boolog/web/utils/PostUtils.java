package com.stoph.boolog.web.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostUtils {

    public final static int NUMBERS_PER_PAGE = 8;
    public final static int INDEXES_PER_PAGE = 5;

    public static String tagParsing(String tags) {
        List<String> parsedTagList = Stream.of(tags.trim().replaceAll("#+", "#").split("#"))
                .map(String::trim)
                .filter(s -> s.length() > 0)
                .distinct()
                .collect(Collectors.toList());

        return "#".concat(String.join("#", parsedTagList));
    }

    public static List<Integer> getPageIndexes(int page, int total) {

        int startPage = (INDEXES_PER_PAGE * ((page / INDEXES_PER_PAGE) + 1)) - (INDEXES_PER_PAGE - 1);
        int endPage = Math.min(INDEXES_PER_PAGE * ((page / INDEXES_PER_PAGE) + 1), total);

        List<Integer> pageList = new ArrayList<>();
        for(int i = startPage; i <= endPage; i++) {
            pageList.add(i);
        }

        return pageList;
    }
}
