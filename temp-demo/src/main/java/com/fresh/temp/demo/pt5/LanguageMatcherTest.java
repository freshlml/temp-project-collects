package com.fresh.temp.demo.pt5;

import java.util.ArrayList;
import java.util.List;

public class LanguageMatcherTest {

    public static void main(String[] argv) {

        List<LanguageRange> priorityRanges = LanguageRange.parse("zh-CN;q=0.1,zh-*-*-*-CN;q=0.4,zh-*-*-CN;q=0.3,zh-*-CN;q=0.2");

        List<LanguageTag> languageTags = new ArrayList<>();
        languageTags.add(LanguageTagParser.parse("zh-CN"));
        languageTags.add(LanguageTagParser.parse("zh-cmn-CN"));
        languageTags.add(LanguageTagParser.parse("zh-cmn-Hant-CN-a-CN"));
        languageTags.add(LanguageTagParser.parse("zh-HK-a-CN"));

        List<LanguageTag> result = LanguageMatcher.filter(priorityRanges, languageTags, MatchingSchema.EXTENDED);

    }
}
