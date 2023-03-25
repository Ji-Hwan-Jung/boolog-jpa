package com.stoph.boolog.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.owasp.validator.html.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebUtils {

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
        int startPage = INDEXES_PER_PAGE * (int) Math.ceil((double) page/INDEXES_PER_PAGE) - INDEXES_PER_PAGE + 1;
        int endPage = Math.min(startPage + INDEXES_PER_PAGE - 1, total);

        List<Integer> pageList = new ArrayList<>();
        for(int i = startPage; i <= endPage; i++) {
            pageList.add(i);
        }

        return pageList;
    }

    public static String XssValidation(String s){
        try{
            AntiSamy as = new AntiSamy();
            CleanResults scan = as.scan(s, Policy.getInstance());
            return scan.getCleanHTML();
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
