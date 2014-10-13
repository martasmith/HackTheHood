package com.codepath.hackthehood.models;

import android.content.Context;

import com.codepath.hackthehood.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravi on 10/12/14.
 */
public class WebsiteTemplate {

    private String imageURL;
    private String title;
    private String imageSrc;

    public WebsiteTemplate(String title, String imageURL, String imageSrc) {
        this.imageURL = imageURL;
        this.title = title;
        this.imageSrc = imageSrc;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static List<WebsiteTemplate> getInitialData(Context context) {
        // Could possibly fetch this from server
        InputStream is = context.getResources().openRawResource(R.raw.website_templates_data);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String readLine = null;
        ArrayList<WebsiteTemplate> websiteTemplates = new ArrayList<WebsiteTemplate>();

        try {
            while ((readLine = br.readLine()) != null) {
                String[] websiteTemplateLine = readLine.split(",");
                if (websiteTemplateLine.length == 3) {
                    WebsiteTemplate websiteTemplate = new WebsiteTemplate(websiteTemplateLine[0], "http://www.hackthehood.org" + websiteTemplateLine[1], websiteTemplateLine[2]);
                    websiteTemplates.add(websiteTemplate);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return websiteTemplates;
    }
}
