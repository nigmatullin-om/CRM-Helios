package com.becomejavasenior.controller;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleService {
    private ResourceBundle resources;

    public LocaleService(Locale locale) {
        resources = ResourceBundle.getBundle("LocaleText", locale);
    }

    public String getString(String paramName) {
        return resources.getString(paramName);
    }

    public ResourceBundle getResources() {
        return resources;
    }
}
