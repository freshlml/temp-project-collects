package com.fresh.temp.demo.pt5;

import java.util.*;

public class LanguageTagParser {
    static final String SPLIT = "-";
    private static final String WELL_FORMED = "^[0-9a-zA-Z\\-]+$";
    private static final String LANGUAGE_REGEX = "^[a-zA-Z]{2,8}$";
    private static final String EXT_LANGUAGE_REGEX = "^[a-zA-Z]{3}$";
    private static final String SCRIPT_REGEX = "^[a-zA-Z]{4}$";
    private static final String REGION_REGEX = "^[a-zA-Z]{2}|[0-9]{3}$";
    private static final String VARIANT_REGEX = "^[a-zA-Z]{5,8}|[0-9][0-9a-zA-Z]{3}$";
    private static final String PRIVATE_USE_VALUE_REGEX = "^[0-9a-zA-Z]{1,8}$";
    private static final String EXTENSION_USE_KEY_REGEX = "^[0-9A-WY-Za-wy-z]$";
    private static final String EXTENSION_USE_VALUE_REGEX = "^[0-9a-zA-Z]{2,8}$";

    private static final Map<String, String> GRAND_FATHERED;

    static {
        Map<String, String> map = new HashMap<>();
        map.put("en-GB-oed", "en-GB-oed");
        map.put("i-ami", "i-ami");
        map.put("i-bnn", "i-bnn");
        map.put("i-default", "i-default");
        map.put("i-enochain", "i-enochain");
        map.put("i-hak", "i-hak");
        map.put("i-klingon", "i-kligon");
        map.put("i-lux", "i-lux");
        map.put("i-mingo", "i-mingo");
        map.put("i-navajo", "i-navajo");
        map.put("i-pwn", "i-pwn");
        map.put("i-tao", "i-tao");
        map.put("i-tay", "i-tay");
        map.put("i-tsu", "i-tsu");
        map.put("sgn-BE-FR", "sgn-BE-FR");
        map.put("sgn-BE-NL", "sgn-BE-NL");
        map.put("sgn-CH-DE", "sgn-CH-DE");

        map.put("art-lojban", "arg-lojban");
        map.put("cel-gaulish", "cel-gaulish");
        map.put("no-bok", "no-bok");
        map.put("no-nyn", "no-nyn");
        map.put("zh-guoyu", "zh-guoyu");
        map.put("zh-hakka", "zh-hakka");
        map.put("zh-xiang", "zh-xiang");

        GRAND_FATHERED = Collections.unmodifiableMap(map);
    }

    public static LanguageTag parse(String languageTag) {
        checkWellFormed(languageTag);

        LanguageTag result = new LanguageTag();
        if(handleGrandFathered(languageTag, result)) return result;

        String[] subTags = languageTag.split(SPLIT);
        int index = 0;

        if(isPrivateUse(subTags)) {
            index = handlePrivateUse(subTags, index, result);
        } else {
            index = handleLanguage(subTags, index, result);
            index = handleScript(subTags, index, result);
            index = handleRegion(subTags, index, result);
            index = handleVariant(subTags, index, result);
            index = handleExtension(subTags, index, result);
            index = handlePrivateUse(subTags, index, result);
        }

        if(index < subTags.length) throw new IllFormedLanguageTag("ill formed language tag [" + index + "]");
        return result;
    }

    private static void checkWellFormed(String languageTagStr) {
        //if(!languageTagStr.matches(WELL_FORMED)) throw new IllFormedLanguageTag("only ALPHA | DIGIT | - allowed");
        if(languageTagStr.length() == 0)
            throw new IllFormedLanguageTag("empty language tag");
        if(languageTagStr.charAt(languageTagStr.length()-1) == '-')
            throw new IllFormedLanguageTag("ill formed language tag [" + (languageTagStr.length()-1) + "]");
        boolean exists = false;
        for(int i=0; i<languageTagStr.length(); i++) {
            char c = languageTagStr.charAt(i);
            if(exists && c == '-') {
                throw new IllFormedLanguageTag("ill formed language tag [" + i + "]");
            } else exists = c == '-';
        }
    }

    private static boolean isPrivateUse(String[] subTags) {
        return subTags.length > 0 && subTags[0].equals("x");
    }

    private static int handleLanguage(String[] subTags, int index, LanguageTag languageTag) {
        if(index >= subTags.length) return index;

        String subTag = subTags[index];
        if(subTag.matches(LANGUAGE_REGEX)) {
            languageTag.language = subTag;
            List<String> extLanguages = new ArrayList<>();
            while(++index < subTags.length && subTags[index].matches(EXT_LANGUAGE_REGEX)) {
                extLanguages.add(subTags[index]);
            }

            if(extLanguages.size() > 0) languageTag.extLanguages = extLanguages;
        } else {
            throw new IllFormedLanguageTag("no language subtag [" + index + "]");
        }

        return index;
    }

    private static int handleScript(String[] subTags, int index, LanguageTag languageTag) {
        if(index >= subTags.length) return index;

        String subTag = subTags[index];
        if(subTag.matches(SCRIPT_REGEX)) {
            languageTag.script = subTag;
            index++;
        }

        return index;
    }

    private static int handleRegion(String[] subTags, int index, LanguageTag languageTag) {
        if(index >= subTags.length) return index;

        String subTag = subTags[index];
        if(subTag.matches(REGION_REGEX)) {
            languageTag.region = subTag;
            index++;
        }

        return index;
    }

    private static int handleVariant(String[] subTags, int index, LanguageTag languageTag) {
        if(index >= subTags.length) return index;

        String subTag = subTags[index];
        if(subTag.matches(VARIANT_REGEX)) {
            languageTag.variant = subTag;
            index++;
        }

        return index;
    }

    private static int handleExtension(String[] subTags, int index, LanguageTag languageTag) {
        if(index >= subTags.length) return index;

        String subTag = subTags[index];
        if(subTag.matches(EXTENSION_USE_KEY_REGEX)) {
            List<String> extensions = new ArrayList<>();
            while(++index < subTags.length && subTags[index].matches(EXTENSION_USE_VALUE_REGEX)) {
                extensions.add(subTags[index]);
            }

            if(extensions.size() == 0) throw new IllFormedLanguageTag("no extension value subtag [" + index + "]");
            languageTag.extensionKey = subTag.charAt(0);
            languageTag.extensions = extensions;
        }
        return index;
    }

    private static int handlePrivateUse(String[] subTags, int index, LanguageTag languageTag) {
        if(index >= subTags.length) return index;

        String subTag = subTags[index];
        if(subTag.equals("x")) {
            List<String> privateUses = new ArrayList<>();
            while(++index < subTags.length && subTags[index].matches(PRIVATE_USE_VALUE_REGEX)) {
                privateUses.add(subTags[index]);
            }

            if(privateUses.size() == 0) throw new IllFormedLanguageTag("no private-use value subtag [" + index + "]");
            languageTag.privateUseKey = 'x';
            languageTag.privateUses = privateUses;
        }
        return index;
    }

    private static boolean handleGrandFathered(String languageTagStr, LanguageTag languageTag) {
        if(isGrandFathered(languageTagStr)) {
            languageTag.grandFathered=languageTagStr;
            return true;
        }
        return false;
    }

    private static boolean isGrandFathered(String languageTagStr) {
        return GRAND_FATHERED.containsKey(languageTagStr);
    }

}

class LanguageTag {
    String language;
    List<String> extLanguages;
    String script;
    String region;
    String variant;
    Character extensionKey;
    List<String> extensions;
    Character privateUseKey;
    List<String> privateUses;
    String grandFathered;

    private volatile String languageTag;

    public String toLanguageTag() {
        if(this.languageTag == null) {
            StringBuilder sb = new StringBuilder();

            if(language != null) {
                sb.append(language);
            }
            if(extLanguages != null) {
                extLanguages.forEach(ext -> sb.append(LanguageTagParser.SPLIT).append(ext));
            }

            if (script != null) sb.append(LanguageTagParser.SPLIT).append(script);
            if (region != null) sb.append(LanguageTagParser.SPLIT).append(region);
            if (variant != null) sb.append(LanguageTagParser.SPLIT).append(variant);

            if (extensionKey != null) {
                sb.append(LanguageTagParser.SPLIT).append(extensionKey);
                extensions.forEach(ext -> sb.append(LanguageTagParser.SPLIT).append(ext));
            }

            if (privateUseKey != null) {
                sb.append(LanguageTagParser.SPLIT).append(privateUseKey);
                privateUses.forEach(ext -> sb.append(LanguageTagParser.SPLIT).append(ext));
            }

            if(grandFathered != null) sb.append(grandFathered);

            this.languageTag = sb.toString();
        }

        return this.languageTag;
    }

    @Override
    public String toString() {
        return "LanguageTag{" +
                "language='" + language + '\'' +
                ", extLanguages=" + extLanguages +
                ", script='" + script + '\'' +
                ", region='" + region + '\'' +
                ", variant='" + variant + '\'' +
                ", extensionKey=" + extensionKey +
                ", extensions=" + extensions +
                ", privateUseKey=" + privateUseKey +
                ", privateUses=" + privateUses +
                ", grandFathered='" + grandFathered + '\'' +
                '}';
    }
}


class IllFormedLanguageTag extends RuntimeException {
    public IllFormedLanguageTag(String message) {
        super(message);
    }
}


