package com.codepath.hackthehood.fragments.info;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.codepath.hackthehood.R;


/**
 * Created by ravi on 10/26/14.
 *
 */public class WebviewFragment extends Fragment {
    public static final String PAGE_URL = "pageURL";
    public static final String PAGE_TITLE = "pageTitle";

    public WebviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_webview, container, false);
        WebView webView = (WebView) rootView.findViewById(R.id.wvPage);
        webView.loadUrl(getArguments().getString(PAGE_URL));
        ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle(getArguments().getString(PAGE_TITLE));
        return rootView;
    }


}
