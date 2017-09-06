package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION_CODES.N;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private NewsAdapter mNewsAdapter;
    private ProgressBar mProgressBar;
    private TextView mEmptyTextView;

    private String request_url = "http://content.guardianapis.com/search?api-key=test";
    private final static int NEWS_LOADER_ID = 1;

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle bundle) {

        return new NewsLoader(this, request_url);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {

        // Clear adapter from previous data
        mNewsAdapter.clear();

        // If valid list of news was found then add adapter's data
        if (news != null && !news.isEmpty()) {
            mNewsAdapter.addAll(news);
        }

        // Set empty state text to display "No news found."
        mEmptyTextView.setText(R.string.no_news);
        // Hide progress bar when loading is completed
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

        mNewsAdapter.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView newsListView = (ListView) findViewById(R.id.news_list_layout);

        // Create empty textView for displaying 'connection error'
        mEmptyTextView = (TextView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyTextView);

        // Create Loading indicator
        mProgressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        mProgressBar.setVisibility(View.VISIBLE);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // If connection was found init loader (fetch data)
            android.app.LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);

        } else {
            // Otherwise display error in empty textView and hide loading spinner
            mProgressBar.setVisibility(View.GONE);
            mEmptyTextView.setText(R.string.no_internet);
        }

        // Create adapter and attach it to the newsListView
        mNewsAdapter = new NewsAdapter(this, new ArrayList<News>());
        newsListView.setAdapter(mNewsAdapter);

        // Create OnItemListener for sending intent to guardian page
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Get the object at the given position (user clicked on)
                News currentNews = mNewsAdapter.getItem(position);

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(currentNews.getWebUrl()));
                startActivity(i);
            }
        });

    }

    // Create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.categories, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // Handle item selection
        if (networkInfo != null && networkInfo.isConnected()) {
            // If connection was found fetch data
            switch (item.getItemId()) {
                case R.id.all:
                    request_url = "http://content.guardianapis.com/search?api-key=test";
                    getLoaderManager().restartLoader(NEWS_LOADER_ID, null, MainActivity.this).forceLoad();
                    return true;

                case R.id.politics:
                    request_url = "https://content.guardianapis.com/politics?api-key=test";
                    getLoaderManager().restartLoader(NEWS_LOADER_ID, null, MainActivity.this).forceLoad();
                    return true;

                case R.id.business:
                    request_url = "https://content.guardianapis.com/business?api-key=test";
                    getLoaderManager().restartLoader(NEWS_LOADER_ID, null, MainActivity.this).forceLoad();
                    return true;

                case R.id.education:
                    request_url = "https://content.guardianapis.com/education?api-key=test";
                    getLoaderManager().restartLoader(NEWS_LOADER_ID, null, MainActivity.this).forceLoad();
                    return true;

                case R.id.technology:
                    request_url = "https://content.guardianapis.com/technology?api-key=test";
                    getLoaderManager().restartLoader(NEWS_LOADER_ID, null, MainActivity.this).forceLoad();
                    return true;

                case R.id.android:
                    request_url = "https://content.guardianapis.com/search?q=android&api-key=test";
                    getLoaderManager().restartLoader(NEWS_LOADER_ID, null, MainActivity.this).forceLoad();
                    return true;

                case R.id.sport:
                    request_url = "https://content.guardianapis.com/sport?api-key=test";
                    getLoaderManager().restartLoader(NEWS_LOADER_ID, null, MainActivity.this).forceLoad();
                    return true;

                case R.id.uknews:
                    request_url = "https://content.guardianapis.com/uk-news?api-key=test";
                    getLoaderManager().restartLoader(NEWS_LOADER_ID, null, MainActivity.this).forceLoad();
                    return true;

                case R.id.us:
                    request_url = "https://content.guardianapis.com/us-news?api-key=test";
                    getLoaderManager().restartLoader(NEWS_LOADER_ID, null, MainActivity.this).forceLoad();
                    return true;

                case R.id.aus:
                    request_url = "https://content.guardianapis.com/australia-news?api-key=test";
                    getLoaderManager().restartLoader(NEWS_LOADER_ID, null, MainActivity.this).forceLoad();
                    return true;

                case R.id.fashion:
                    request_url = "https://content.guardianapis.com/search?q=fashion&api-key=test";
                    getLoaderManager().restartLoader(NEWS_LOADER_ID, null, MainActivity.this).forceLoad();
                    return true;

                case R.id.books:
                    request_url = "https://content.guardianapis.com/search?q=books&api-key=test";
                    getLoaderManager().restartLoader(NEWS_LOADER_ID, null, MainActivity.this).forceLoad();
                    return true;

                case R.id.health:
                    request_url = "https://content.guardianapis.com/search?q=health&api-key=test";
                    getLoaderManager().restartLoader(NEWS_LOADER_ID, null, MainActivity.this).forceLoad();
                    return true;

                case R.id.food:
                    request_url = "https://content.guardianapis.com/search?q=food&api-key=test";
                    getLoaderManager().restartLoader(NEWS_LOADER_ID, null, MainActivity.this).forceLoad();
                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }

        } else {
            // Otherwise display error in empty textView and hide loading spinner
            mProgressBar.setVisibility(View.GONE);
            mNewsAdapter.clear();

            mEmptyTextView.setText(R.string.no_internet);
        }
        return super.onOptionsItemSelected(item);
    }
}