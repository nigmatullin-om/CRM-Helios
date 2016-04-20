package com.becomejavasenior.controller;

import com.becomejavasenior.model.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TagSplitter {
    private static final Logger log = LogManager.getLogger(TagSplitter.class);

    public static List<Tag> getTagListFromString(String tagField){
        List<Tag> tags = new ArrayList<>();
        for (String tagName : tagField.split(",")) {
            tagName = tagName.trim();
            log.info("Tag: " + tagName);
            Tag tag = new Tag();
            tag.setName(tagName);
            tags.add(tag);
        }
        return tags;
    }

    public static String getStringFromTagList(List<Tag> tags){
        String result = "";
        for (Tag tag : tags){
            result += tag.getName() + ", ";
        }
        return result;
    }

    public TagSplitter() {
    }
}
