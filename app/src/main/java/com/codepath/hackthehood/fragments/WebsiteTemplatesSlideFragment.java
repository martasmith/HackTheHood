package com.codepath.hackthehood.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.adapters.WebsiteTemplatesListAdapter;
import com.codepath.hackthehood.models.WebsiteTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WebsiteTemplatesSlideFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * Created by ravi on 10/12/14.
 */
public class WebsiteTemplatesSlideFragment extends Fragment {

    private WebsiteTemplatesListAdapter websiteTemplatesListAdapter;
    private ListView lvWebsiteTemplates;
    private ArrayList<WebsiteTemplate> websiteTemplates;

    public static WebsiteTemplatesSlideFragment newInstance() {
        WebsiteTemplatesSlideFragment fragment = new WebsiteTemplatesSlideFragment();
        return fragment;
    }
    public WebsiteTemplatesSlideFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_website_templates_slide, container, false);
        lvWebsiteTemplates = (ListView) rootView.findViewById(R.id.lvWebsiteTemplates);
        websiteTemplates = new ArrayList<WebsiteTemplate>(WebsiteTemplate.getInitialData(getActivity()));
        websiteTemplatesListAdapter = new WebsiteTemplatesListAdapter(getActivity(), websiteTemplates);
        lvWebsiteTemplates.setAdapter(websiteTemplatesListAdapter);
        return rootView;
    }
}
