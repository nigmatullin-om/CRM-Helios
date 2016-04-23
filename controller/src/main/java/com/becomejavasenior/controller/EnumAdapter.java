package com.becomejavasenior.controller;

public class EnumAdapter {
    private int id;
    private String name;

    public EnumAdapter(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public EnumAdapter() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
