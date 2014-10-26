package com.codepath.hackthehood.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.activities.PitchDeckActivity;
import com.codepath.hackthehood.models.NavDrawerItem;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by ravi on 10/25/14.
 */
public class NavDrawerListAdapter extends BaseAdapter {

    private final ArrayList<NavDrawerItem> navDrawerItems;
    private final LayoutInflater mInflater;
    private final Context mContext;


    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems){
        mContext = context;
        this.navDrawerItems = navDrawerItems;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return navDrawerItems.size() + 1 /* one for spacer and one for user email address*/;
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            switch (position) {
                case 0:
                    convertView = mInflater.inflate(R.layout.drawer_user_item, null);
                    TextView tvEmail = (TextView) convertView.findViewById(R.id.tvEmail);
                    tvEmail.setText(ParseUser.getCurrentUser().getEmail());
                    ImageView ivLogout = (ImageView) convertView.findViewById(R.id.ivLogout);
//                    ivLogout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            ParseUser.logOut();
//                            mContext.startActivity(new Intent(mContext, PitchDeckActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                        }
//                    });
                    break;

                default:
                    convertView = mInflater.inflate(R.layout.drawer_nav_item, null);
                    TextView txtTitle = (TextView) convertView.findViewById(R.id.tvSlideText);
                    txtTitle.setText(navDrawerItems.get(position - 1).getTitle());
                    break;

            }
        }
        return convertView;
    }

}