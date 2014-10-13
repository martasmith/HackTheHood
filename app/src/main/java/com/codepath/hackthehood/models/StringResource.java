package com.codepath.hackthehood.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * Created by thomasharte on 12/10/2014.
 */
@Table(name="StringResources")
public class StringResource extends Model implements Serializable {
    @Column(name = "text")
    private String text;

    /**
     * @category setters
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @category getters
     */
    public String getText() {
        return text;
    }
}
