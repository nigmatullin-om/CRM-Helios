package com.becomejavasenior.controller;

import com.becomejavasenior.model.Tag;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class InputDataManager {

    public static List<Tag> stringToTags(String tagsString) {
        List<Tag> tags = new ArrayList<>();
        Tag tag;
        for (String tagName : tagsString.split(",")) {
            tagName = tagName.trim();
            tag = new Tag();
            tag.setName(tagName);
            tags.add(tag);
        }
        return tags;
    }

    public static Locale getLocale(HttpServletRequest req) {
        Locale userLocale;

        Cookie localeCookie = null;
        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals("localeCookie")) {
                localeCookie = cookie;
                break;
            }
        }
        if (localeCookie == null) {
            localeCookie = new Cookie("localeCookie", "en_US");
        }
        String language = localeCookie.getValue().substring(0, 2);
        String country = localeCookie.getValue().substring(3, 5);
        userLocale = new Locale(language, country);

        return userLocale;
    }

    public static Cookie getLocaleCookie(Locale locale) {
        return new Cookie("localeCookie", locale.getLanguage() + "_" + locale.getCountry());
    }

}
