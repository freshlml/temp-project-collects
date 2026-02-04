package com.fresh.temp.demo.pt5;


public class LanguageTagParserTest {

    public static void main(String[] argv) {

        LanguageTag languageTag = LanguageTagParser.parse("zh-cmn-cn");
        System.out.println(languageTag);

    }
}
