package com.example.android.newsapp;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.media.CamcorderProfile.get;


/**
 * Created by kiwi on 2017-07-15.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, ArrayList<News> news) {

        super(context, 0, news);
    }

    class ViewHolder {

        private TextView mWebTitle;
        private TextView mSectionName;
        private TextView mType;
        private TextView mPublicationDate;

        public ViewHolder(View view) {

            this.mWebTitle = (TextView) view.findViewById(R.id.title_text_view);
            this.mSectionName = (TextView) view.findViewById(R.id.section_text_view);
            this.mType = (TextView) view.findViewById(R.id.type_text_view);
            this.mPublicationDate = (TextView) view.findViewById(R.id.date_text_view);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        News currentNews = getItem(position);

        // Set shape from drawable as colored background for section name TextView
        GradientDrawable sectionTheme = (GradientDrawable) viewHolder.mSectionName.getBackground();
        int sectionColor = getSectionColor(currentNews.getSectionName());
        sectionTheme.setColor(sectionColor);

        viewHolder.mWebTitle.setText(currentNews.getWebTitle());
        viewHolder.mSectionName.setText(currentNews.getSectionName());
        viewHolder.mType.setText(currentNews.getType());
        viewHolder.mPublicationDate.setText(currentNews.getDatePublished().substring(0, 10));

        return convertView;

    }

    private int getSectionColor(String sectionName) {

        Log.e("section",": " + sectionName);
        // Assign proper colors according to "Section Name"
        int sectionNameColorID;
        switch (sectionName) {
            case "Politics":
                sectionNameColorID = R.color.politics;
                break;
            case "Education":
                sectionNameColorID = R.color.education;
                break;
            case "Technology":
                sectionNameColorID = R.color.technology;
                break;
            case "Business":
                sectionNameColorID = R.color.business;
                break;
            case "Sport":
                sectionNameColorID = R.color.sport;
                break;
            case "Environment":
                sectionNameColorID = R.color.Environment;
                break;
            case "News":
                sectionNameColorID = R.color.News;
                break;
            case "Opinion":
                sectionNameColorID = R.color.Opinion;
                break;
            case "Teacher Network":
                sectionNameColorID = R.color.teacher;
                break;
            case "US news":
                sectionNameColorID = R.color.us;
                break;
            case "Football":
                sectionNameColorID = R.color.football;
                break;
            case "Film":
                sectionNameColorID = R.color.film;
                break;
            case "Television & radio":
                sectionNameColorID = R.color.tv;
                break;
            case "Australia news":
                sectionNameColorID = R.color.aus;
                break;
            default:
                sectionNameColorID = R.color.android;
        }
        return ContextCompat.getColor(getContext(), sectionNameColorID);
    }

}