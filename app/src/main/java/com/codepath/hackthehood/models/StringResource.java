package com.codepath.hackthehood.models;

import com.parse.ParseClassName;

/**
 * Created by thomasharte on 12/10/2014.
 */
@ParseClassName("StringResource")
public class StringResource extends NullForNothingParseObject {

    public StringResource() {}

    /*
        Exposed properties:

            String text
     */

    private final String textKey = "text";
    public void setText(String text) {
        put(textKey, text);
    }
    public String getText() {
        return getString(textKey);
    }
}
