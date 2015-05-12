package com.github.pwittchen.networkevents.library;

import java.net.MalformedURLException;
import java.net.URL;

public class Validator {
    public void checkNotNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public void checkNotSet(Object object, String message) {
        if (object != null) {
            throw new IllegalStateException(message);
        }
    }

    public void checkSet(Object object, String message) {
        if (object == null) {
            throw new IllegalStateException(message);
        }
    }

    public void checkPositive(int value, String message) {
        if(value <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public void checkUrl(String pingUrl, String message) {
        try {
            new URL(pingUrl);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(message);
        }
    }
}
