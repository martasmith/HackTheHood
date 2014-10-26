package com.codepath.hackthehood.fragments.forms;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.controller.ParseHelper;
import com.codepath.hackthehood.fragments.ImageCollectionFragment;
import com.codepath.hackthehood.models.ImageResource;
import com.codepath.hackthehood.models.PageResource;
import com.codepath.hackthehood.models.User;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class WebsitePageFragment extends ImageCollectionFragment {

    private WebpageFormListener mListener;

    private EditText etPageText, etDesignerNotes;
    private List<ImageView> imageViews;
    private List<ImageResource> imageResources;
    private com.codepath.hackthehood.models.WebsitePage page;

    private final static String TITLE = "title";
    private final static String PAGE_INDEX = "pageIndex";

    public static WebsitePageFragment newInstance(String title, int pageIndex) {
        WebsitePageFragment fragment = new WebsitePageFragment();

        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putInt(PAGE_INDEX, pageIndex);
        fragment.setArguments(args);

        return fragment;
    }

    public WebsitePageFragment() {
        // Required empty public constructor
    }

    @Override
    public void doFetch(boolean onlyIfNeeded) {
        super.doFetch(onlyIfNeeded);

        final int pageIndex = getArguments().getInt(PAGE_INDEX);
        final User user = (User) ParseUser.getCurrentUser();
        incrementNetworkActivityCount();
        ParseHelper.fetchObjectsInBackgroundInSerial(true,
                new Iterator<ParseObject>() {
                    private int index = 0;

                    @Override
                    public boolean hasNext() {
                        return index != 3;
                    }

                    @Override
                    public ParseObject next() {
                        index++;
                        switch (index) {
                            default:
                                return user;
                            case 2:
                                return user.getWebsite();
                            case 3:
                                return user.getWebsite().getWebsitePages().get(pageIndex);
                        }
                    }

                    @Override
                    public void remove() {
                    }
                }, new GetCallback() {
                    @Override
                    public void done(ParseObject parseObject, ParseException e) {
                        didReceiveNetworkException(e);
                        if (e != null) {
                            setFetchIsFinished();
                            decrementNetworkActivityCount();
                            return;
                        }

                        page = user.getWebsite().getWebsitePages().get(pageIndex);
                        final List<PageResource> pageResources = page.getPageResources();
                        ParseHelper.fetchObjectsInBackgroundInParallel(
                                true,
                                pageResources.toArray(new ParseObject[pageResources.size()]),
                                new GetCallback() {
                                    @Override
                                    public void done(ParseObject parseObject, ParseException e) {
                                        didReceiveNetworkException(e);
                                        if (e != null) {
                                            setFetchIsFinished();
                                            decrementNetworkActivityCount();
                                            return;
                                        }

                                        imageResources = new ArrayList<ImageResource>();
                                        for(PageResource pageResource : pageResources) {
                                            ImageResource imageResource = pageResource.getImageResource();
                                            if(imageResource != null)
                                                imageResources.add(imageResource);
                                        }

                                        ParseHelper.fetchObjectsInBackgroundInParallel(
                                                true,
                                                imageResources.toArray(new ParseObject[imageResources.size()]),
                                                new GetCallback() {
                                                    @Override
                                                    public void done(ParseObject parseObject, ParseException e) {
                                                        setFetchIsFinished();
                                                        decrementNetworkActivityCount();
                                                        didReceiveNetworkException(e);

                                                        if(e == null)
                                                            populateView();
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            onAttachFragment(getParentFragment());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_webpage_collection,container,false);

        etPageText = (EditText) v.findViewById(R.id.etPageText);
        etDesignerNotes = (EditText) v.findViewById(R.id.etDesignerNotes);

        imageViews = new ArrayList<ImageView>();
        imageViews.add((ImageView)v.findViewById(R.id.imgFile1));
        imageViews.add((ImageView)v.findViewById(R.id.imgFile2));
        imageViews.add((ImageView)v.findViewById(R.id.imgFile3));

        setupAddSiteListener((Button)v.findViewById(R.id.btnAddSite));
        for(int c = 0; c < 3; c++)
            setupImageUploadListener(imageViews.get(c), c);

        fetch(true);
        return v;
    }

    private void onAttachFragment(Fragment fragment) {
        try {
            mListener = (WebpageFormListener)fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(fragment.toString()
                    + " must implement WebpageFormListener");
        }
    }

    private void populateView() {
        for(int c = 0; c < 3; c++) {
            String imageUrl = imageResources.get(c).getImageUrl();
            if(imageUrl != null)
                Picasso.with(getActivity()).load(imageUrl).fit().into(imageViews.get(c));
        }

        etDesignerNotes.setText(page.getNotes());
        etPageText.setText(page.getText());
    }

    private void setupAddSiteListener(final Button bntAddSite) {

        bntAddSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bntAddSite.setEnabled(false);
                submit(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        bntAddSite.setEnabled(true);

                        if (e == null && mListener != null) {
                            mListener.onWebpageFormSubmit(getArguments().getInt(PAGE_INDEX));
                        }
                    }
                });
            }
        });
    }

    @Override
    public void submit(final SaveCallback saveCallback) {

        page.setNotes(etDesignerNotes.getText().toString());
        page.setTitle(getArguments().getString(TITLE));
        page.setText(etPageText.getText().toString());

        incrementNetworkActivityCount();
        page.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                decrementNetworkActivityCount();
                didReceiveNetworkException(e);
                saveCallback.done(e);
            }
        });
    }

    @Override
    protected ImageView imageViewForIndex(int index) {
        return imageViews.get(index);
    }

    @Override
    protected ImageResource imageResourceForIndex(int index) {
        return imageResources.get(index);
    }

    public interface WebpageFormListener {
        public void onWebpageFormSubmit(int pageIndex);
    }
}
