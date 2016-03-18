package com.becomejavasenior.dao;

import java.util.ArrayList;

public class DatabaseException extends java.lang.Exception {
    private ArrayList<Exception> exceptions;

    public DatabaseException() {
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(ArrayList<Exception> exceptions) {
        this.exceptions = exceptions;
    }

    public ArrayList<Exception> getExceptions() {
        return exceptions;
    }

    public void setExceptions(ArrayList<Exception> exceptions) {
        this.exceptions = exceptions;
    }
}

