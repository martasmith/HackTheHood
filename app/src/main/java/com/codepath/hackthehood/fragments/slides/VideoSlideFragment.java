package com.codepath.hackthehood.fragments.slides;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.hackthehood.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class VideoSlideFragment extends Fragment {


    public VideoSlideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_video_slide, container, false);
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        YouTubePlayerSupportFragment youTubePlayerFragment =  YouTubePlayerSupportFragment.newInstance();
        youTubePlayerFragment.initialize("AIzaSyCUF_daNoFxeE0hUm-6z2GMoeo3y9EhW2g", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("h8kWyj1k8uY");
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

        fragmentTransaction.replace(R.id.flVideoPlayer, youTubePlayerFragment);
        fragmentTransaction.commit();

        return rootView;
    }


}
