package com.fresh.temp.demo.pt5;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class LanguageMatcher {

    private static final String SINGLETON_CHAR_REGEX = "^[0-9A-WXY-Za-wxy-z]$";


    public static List<LanguageTag> filter(List<LanguageRange> priorityRanges, List<LanguageTag> languageTags, MatchingSchema matchingSchema) {
        for(LanguageRange range : priorityRanges) {
            List<LanguageTag> result = filter(range, languageTags, matchingSchema);
            if(result != null && result.size() != 0) return result;
        }
        return null;
    }

    private static List<LanguageTag> filter(LanguageRange languageRange, List<LanguageTag> languageTags, MatchingSchema matchingSchema) {
        if(matchingSchema == MatchingSchema.BASIC) {
            if(languageRange.range.equals("*")) return languageTags;

            return languageTags.stream().filter(tag -> tag.toLanguageTag().toLowerCase().startsWith(languageRange.range.toLowerCase())).collect(Collectors.toList());
        } else if(matchingSchema == MatchingSchema.EXTENDED) {

            return languageTags.stream().filter(tag -> filter(languageRange, tag)).collect(Collectors.toList());
        } else {
            throw new IllegalMatchingSchema("illegal MatchingSchema");
        }
    }

    //Extended Filtering defined in RFC-4647
    private static boolean filter(LanguageRange languageRange, LanguageTag languageTag) {
        String[] ranges = languageRange.range.split(LanguageTagParser.SPLIT);
        String[] tags = languageTag.toLanguageTag().split(LanguageTagParser.SPLIT);

        int rangeIndex = 0;
        int tagIndex = 0;
        if(!match(ranges[rangeIndex++], tags[tagIndex++])) return false;

        while(rangeIndex < ranges.length) {
            if(ranges[rangeIndex].equals("*")) {
                rangeIndex++;
                continue;
            }

            if(tagIndex >= tags.length) return false;

            if(ranges[rangeIndex].equalsIgnoreCase(tags[tagIndex])) {
                rangeIndex++;
                tagIndex++;
                continue;
            }

            if(tags[tagIndex].matches(SINGLETON_CHAR_REGEX)) return false;

            tagIndex++;
        }

        return true;
    }

    private static boolean ASCII_alphanumeric(char c) {
        return (c>='0' && c<='9') || (c>='a' && c<='z') || (c>='A' && c<='Z');
    }

    private static boolean match(String subrange, String subtag) {
        return subrange.equals("*") || subrange.equalsIgnoreCase(subtag);
    }
    private static boolean match(String[] ranges, int end, String[] tags) {
        for(int i=0; i<end; i++) {
            if(!match(ranges[i],tags[i])) return false;
        }
        return true;
    }

    public static LanguageTag lookup(List<LanguageRange> priorityRanges, List<LanguageTag> languageTags) {
        for(LanguageRange range : priorityRanges) {
            LanguageTag result = lookup(range, languageTags);
            if(result != null) return result;
        }
        return defaultLookup(priorityRanges, languageTags);
    }

    private static LanguageTag lookup(LanguageRange languageRange, List<LanguageTag> languageTags) {
        if(languageRange.range.equals("*")) return null;

        String[] ranges = languageRange.range.split(LanguageTagParser.SPLIT);

        for(LanguageTag languageTag: languageTags) {
            String[] tags = languageTag.toLanguageTag().split(LanguageTagParser.SPLIT);

            int p = ranges.length;
            while(p >= 0) {
                if(p < tags.length) break;
                else if(p == tags.length && match(ranges, tags.length, tags)) return languageTag;
                else if(p > tags.length) {
                    p--;
                    if(p >= 0 && ranges[p].matches(SINGLETON_CHAR_REGEX)) {
                        p--;
                    }
                }
            }
        }

        return null;
    }

    private static LanguageTag defaultLookup(List<LanguageRange> priorityRanges, List<LanguageTag> languageTags) {
        return null;
    }

}


class LanguageRange {
    private static final String RANGE_REGEX = "^[a-zA-Z]{1,8}(-[0-9a-zA-Z]{1,8})*$";
    final String range;
    final double weight;

    public LanguageRange(String range, double weight) {
        this.range = range;
        this.weight = weight;
    }

    public static List<LanguageRange> parse(String languageRanges) {
        return Locale.LanguageRange.parse(languageRanges).stream().map(rg -> new LanguageRange(rg.getRange(), rg.getWeight())).collect(Collectors.toList());
    }
}

enum MatchingSchema {
    BASIC, EXTENDED;
}

class IllegalMatchingSchema extends RuntimeException {
    public IllegalMatchingSchema(String message) {
        super(message);
    }
}