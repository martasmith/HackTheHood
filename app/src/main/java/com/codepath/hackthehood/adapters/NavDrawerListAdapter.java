package com.codepath.hackthehood.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.hackthehood.R;
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
    private View.OnClickListener mLogoutClickedListener;


    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems) {
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
    public NavDrawerItem getItem(int position) {
        if (position == 0) {
            return null;
        }
        return navDrawerItems.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        switch (position) {
            case 0:
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.drawer_user_item, parent, false);
                }
                TextView tvEmail = (TextView) convertView.findViewById(R.id.tvEmail);
                tvEmail.setText(ParseUser.getCurrentUser().getEmail());
                ImageView ivLogout = (ImageView) convertView.findViewById(R.id.ivLogout);
                ivLogout.setOnClickListener(mLogoutClickedListener);
                break;

            default:
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.drawer_nav_item, parent, false);
                }
                NavDrawerItem navDrawerItem = navDrawerItems.get(position - 1);
                TextView txtTitle = (TextView) convertView.findViewById(R.id.tvSlideText);
                txtTitle.setText(navDrawerItem.getTitle());
                if (navDrawerItem.getSelected()) {
                    txtTitle.setTextColor(mContext.getResources().getColor(R.color.theme_color));
                }
                else {
                    txtTitle.setTextColor(mContext.getResources().getColor(R.color.drawer_list_item));
                }

                View llSeparatorView = (View) convertView.findViewById(R.id.llSeparator);
                llSeparatorView.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
                break;

        }
        return convertView;
    }

    public void setLogoutClickedListener(View.OnClickListener logoutClickedListener) {
        mLogoutClickedListener = logoutClickedListener;
    }
}