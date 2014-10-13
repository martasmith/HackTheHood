package com.codepath.hackthehood.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.hackthehood.R;

/**
 * ImageSlideFragment displays a full screen background image and a title
 * for the pitch deck activity
 * Use the {@link ImageSlideFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * Created by ravi on 10/12/14.
 */
public class ImageSlideFragment extends Fragment {
    private static final String IMG_SRC = "imageSrc";
    private static final String TITLE_TEXT = "titleText";

    private ImageView ivBackgroundImage;
    private TextView tvTitleTextView;

    private String mImageSrc;
    private String mTitleText;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param imageSrc Source of the image to be used
     * @param titleText Title text to show
     * @return A new instance of fragment ImageSlideFragment.
     */
    public static ImageSlideFragment newInstance(String imageSrc, String titleText) {
        ImageSlideFragment fragment = new ImageSlideFragment();
        Bundle args = new Bundle();
        args.putString(IMG_SRC, imageSrc);
        args.putString(TITLE_TEXT, titleText);
        fragment.setArguments(args);
        return fragment;
    }
    public ImageSlideFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mImageSrc = getArguments().getString(IMG_SRC);
            mTitleText = getArguments().getString(TITLE_TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_image_slide, container, false);
        tvTitleTextView = (TextView)rootView.findViewById(R.id.tvTitle);
        ivBackgroundImage = (ImageView)rootView.findViewById(R.id.ivBackgroundImage);
        tvTitleTextView.setText(mTitleText);
        return rootView;
    }

}
