package com.example.android.newsapp;


import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiwi on 2017-07-15.
 */

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public static List<News> fetchNewsData(String requestUrl) {

        // Create url object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive JSON response
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem with HTTP request");
        }
        // Extract relevant fields from JSON response to list
        List<News> news = extractItemFromJson(jsonResponse);
        return news;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "error with creating url", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "problem retrieving json result", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    // Set InputStreamReader and BufferReader
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<News> extractItemFromJson (String newsJSON) {
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        // Create empty ArrayList
        List<News> news = new ArrayList<>();

        // Try to parse JSON response
        try {

            String webTitle;
            String sectionName;
            String publicationDate;
            String webUrl;
            String type;

            // create JSONObject from JSON response
            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            // extract JSONArray associated with the key "results"
            JSONObject jsonResults = baseJsonResponse.getJSONObject("response");
            JSONArray newsArray = jsonResults.getJSONArray("results");

            for (int i = 0; i < newsArray.length(); i++) {

                JSONObject currentNews = newsArray.getJSONObject(i);

                if (currentNews.has("webTitle")) {
                    webTitle = currentNews.getString("webTitle");
                } else {
                    webTitle = "Title not found";
                }

                if (currentNews.has("sectionName")) {
                    sectionName = currentNews.getString("sectionName");
                } else {
                    sectionName = "Unknown section";
                }

                if (currentNews.has("webPublicationDate")) {
                    publicationDate = currentNews.getString("webPublicationDate");
                } else {
                    publicationDate = "";
                }

                if (currentNews.has("webUrl")) {
                    webUrl = currentNews.getString("webUrl");
                } else {
                    webUrl = "";
                }

                if (currentNews.has("type")) {
                    type = currentNews.getString("type");
                } else {
                    type = "";
                }

                News newsConstructor = new News(webTitle, sectionName, type, publicationDate, webUrl);

                news.add(newsConstructor);

            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "QueryUtils Problem parsing JSON results", e);
        }

        return news;
    }
}