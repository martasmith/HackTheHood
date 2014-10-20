package com.codepath.hackthehood.models;

import com.parse.ParseObject;

/**
 * Created by thomasharte on 19/10/2014.
 */
public class NullForNothingParseObject extends ParseObject {

    @Override
    public Object get(String key) {
        try {
            return super.get(key);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getString(String key) {
        try {
            return super.getString(key);
        } catch (Exception e) {
            return null;
        }
    }
}
