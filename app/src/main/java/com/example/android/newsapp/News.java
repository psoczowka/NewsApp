package com.example.android.newsapp;

import static android.R.attr.type;

/**
 * Created by kiwi on 2017-07-15.
 */

public class News {

    private String mType;
    private String mWebTitle;
    private String mSectionName;
    private String mDatePublished;
    private String mWebUrl;

    // Create constructor for news
    public News (String webTitle, String sectionName, String type, String datePublished, String webURL) {
        mType = type;
        mWebTitle = webTitle;
        mSectionName = sectionName;
        mDatePublished = datePublished;
        mWebUrl = webURL;
    }

    public String getType() {
        return mType;
    }

    public String getWebTitle() {
        return mWebTitle;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public String getDatePublished() {
        return mDatePublished;
    }

    public String getWebUrl() {
        return mWebUrl;
    }
}