package com.codepath.hackthehood.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.models.WebsiteTemplate;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ravi on 10/12/14.
 */
public class WebsiteTemplatesListAdapter extends ArrayAdapter<WebsiteTemplate> {

    public static class ViewHolder {
        @InjectView(R.id.ivTemplateImage) ImageView ivTemplateImage;
        @InjectView(R.id.ivTemplateBlurredImage) ImageView ivTemplateBlurredImage;
        @InjectView(R.id.tvTemplateTitle) TextView tvTemplateTitle;

        Target target;
        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public WebsiteTemplatesListAdapter(Context context, List<WebsiteTemplate> objects) {
        super(context, R.layout.website_template, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WebsiteTemplate websiteTemplate = getItem(position);
        final ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.website_template, parent, false);
            viewHolder = new ViewHolder(convertView);
            viewHolder.target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                    // viewHolder.ivTemplateImage.setImageBitmap(bitmap);
                    // Bitmap blurredBitmap = Bitmap.createBitmap(bitmap);
                    viewHolder.ivTemplateImage.setImageBitmap(bitmap);
                    // viewHolder.ivTemplateBlurredImage.setImageBitmap(BlurBitmap.getBlurredBitmap(bitmap, getContext()));
                }

                @Override
                public void onBitmapFailed(Drawable drawable) {

                }

                @Override
                public void onPrepareLoad(Drawable drawable) {

                }
            };
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        // Title
        viewHolder.tvTemplateTitle.setText(websiteTemplate.getTitle());

        // Template Image
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);

        // Calculate new width and height while keeping the same aspect ratio
        int width = dm.widthPixels;
        // Hard coding these based on what I saw..ideally we would have to set these per image in the
        // data set
        int height = width * 538 / 1100;

        // Reset height and image source
        viewHolder.ivTemplateImage.getLayoutParams().height = height;
        viewHolder.ivTemplateImage.setImageResource(0);


        Picasso.with(getContext()).load(websiteTemplate.getImageURL()).resize(width, height).into(viewHolder.ivTemplateImage);
        return convertView;
    }
}
