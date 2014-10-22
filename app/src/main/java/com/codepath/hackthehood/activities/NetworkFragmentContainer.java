package com.codepath.hackthehood.activities;

/**
 * Created by thomasharte on 21/10/2014.
 */
public interface NetworkFragmentContainer {
    public void incrementActivityCount();
    public void decrementActivityCount();
    public void didReceiveException(Exception e);
}
