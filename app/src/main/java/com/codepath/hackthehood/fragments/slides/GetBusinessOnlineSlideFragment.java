package com.codepath.hackthehood.fragments.slides;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.hackthehood.R;

/**
 * A simple {@link Fragment} subclass.
 *
 * Created by ravi on 10/24/14.
 */
public class GetBusinessOnlineSlideFragment extends Fragment {


    public GetBusinessOnlineSlideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_get_business_online_slide, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvStep1 = (TextView) view.findViewById(R.id.tvStep1);
        TextView tvStep2 = (TextView) view.findViewById(R.id.tvStep2);
        TextView tvStep3 = (TextView) view.findViewById(R.id.tvStep3);
        tvStep1.setAlpha(0.0f);
        tvStep2.setAlpha(0.0f);
        tvStep3.setAlpha(0.0f);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(
                ObjectAnimator.ofFloat(tvStep1, "alpha", 0.0f, 1.0f)
                        .setDuration(800),
                ObjectAnimator.ofFloat(tvStep2, "alpha", 0.0f, 1.0f)
                        .setDuration(600),
                ObjectAnimator.ofFloat(tvStep3, "alpha", 0.0f, 1.0f)
                        .setDuration(400)
        );
        set.setStartDelay(1000);
        set.start();
    }
}
