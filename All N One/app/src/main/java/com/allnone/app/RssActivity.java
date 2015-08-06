package com.allnone.app;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.allnone.app.config.R;
import com.allnone.app.data.RssItem;
import com.allnone.app.listeners.ListListener;
import com.allnone.app.util.RssReader;

import java.util.List;


public class RssActivity extends Activity {
    private RssActivity local;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);

        local = this;

        GetRSSDataTask task = new GetRSSDataTask();

        task.execute("http://ww3.allnone.ie/client/client_allnone/message/atomfeed.asp");

        Log.d("All N One", Thread.currentThread().getName());
    }

    private class GetRSSDataTask extends AsyncTask<String, Void, List<RssItem> > {
        @Override
        protected List<RssItem> doInBackground(String... urls) {

            Log.d("All N One", Thread.currentThread().getName());

            try {
                RssReader rssReader = new RssReader(urls[0]);

                return rssReader.getItems();

            } catch (Exception e) {
                Log.e("All N One", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<RssItem> result) {

            ListView allnoneItems = (ListView) findViewById(R.id.listMainView);

            ArrayAdapter<RssItem> adapter = new ArrayAdapter<RssItem>(local,android.R.layout.simple_list_item_1, result);
        //    allnoneItems.setAdapter(adapter);

//            allnoneItems.setOnItemClickListener(new ListListener(result, local));
        }
    }

    public void toLogin(View view) {
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }

}