package com.codepath.hackthehood.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.codepath.hackthehood.activities.NetworkFragmentContainer;
import com.parse.SaveCallback;

/**
 * Created by thomasharte on 21/10/2014.
 */
public abstract class NetworkFragment extends Fragment {

    private int localNetworkActivityCount = 0;

    protected void didReceiveNetworkException(com.parse.ParseException e) {
        Activity activity = getActivity();
        if(activity instanceof NetworkFragmentContainer)
            ((NetworkFragmentContainer) activity).didReceiveException(e);
    }

    protected void incrementNetworkActivityCount() {
        localNetworkActivityCount++;
        Activity activity = getActivity();
        if(activity instanceof NetworkFragmentContainer)
            ((NetworkFragmentContainer) activity).incrementActivityCount();
    }

    protected void decrementNetworkActivityCount() {
        Activity activity = getActivity();
        if(activity instanceof NetworkFragmentContainer)
            ((NetworkFragmentContainer) activity).decrementActivityCount();
        localNetworkActivityCount--;
    }

    @Override
    public void onDestroy() {
        while(localNetworkActivityCount > 0) {
            decrementNetworkActivityCount();
        }

        super.onDestroy();
    }

    public void refresh() {
        fetch(false);
    }

    private boolean isLoading = false;
    public void fetch(boolean onlyIfNeeded) {
        if(isLoading) return;
        isLoading = true;
        doFetch(onlyIfNeeded);
    }

    protected void doFetch(boolean onlyIfNeeded) {
    }

    protected void setFetchIsFinished() {
        isLoading = false;
    }

    public void submit(SaveCallback saveCallback) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetch(true);
    }
}
