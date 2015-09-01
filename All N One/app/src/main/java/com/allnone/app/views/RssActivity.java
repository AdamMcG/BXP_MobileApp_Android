package com.allnone.app.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.allnone.app.Models.Login;
import com.allnone.app.allnone.R;
import com.allnone.app.data.RssItem;
import com.allnone.app.listeners.ListListener;
import com.allnone.app.util.RssReader;

import java.util.List;

public class RssActivity extends Activity {
    private RssActivity local;
    private SharedPreferences retrievalCache;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);
        local = this;
        retrievalCache = this.getSharedPreferences("com.allnone.app", Context.MODE_PRIVATE);
        GetRSSDataTask task;
        boolean check = fn_checkConnectivity();
        if (!check) {
            Button login = (Button) findViewById(R.id.button_login1);
            login.setEnabled(false);
            Context context = local;
            AlertDialog.Builder noNetwork = new AlertDialog.Builder(context);
            noNetwork.setMessage("No Network Connection");
            noNetwork.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            noNetwork.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            noNetwork.create();
            noNetwork.show();
        }

        try {
            task = new GetRSSDataTask();
            task.execute("http://ww3.allnone.ie/client/client_demo/message/atomfeed.asp");
            Log.d("All N One", Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean fn_checkConnectivity() {
        boolean connected = true;
        try {
            Context context = local;
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo();
            connected = (activeNetwork != null) && activeNetwork.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connected;

    }

    public void toLogin(View view) {
        Intent intent = new Intent(this, HomePage.class);
        String check = retrievalCache.getString("strClient_SessionField", "N/A");
        int idCheck = retrievalCache.getInt("intClientId", 0);
        if (check.equals("N/A")) {
            intent = new Intent(this, LoginPage.class);
        } else {
            Login.getInstance().setStrUrlUsed(retrievalCache.getString("URL", "N/W"));
            Login.getInstance().setStrClient_SessionField(check);
            Login.getInstance().setIntClientid(idCheck);
            Login.getInstance().setStrUserName(retrievalCache.getString("userName", "N/W"));
            Login.getInstance().setStrSystemUsed(retrievalCache.getString("system", "N/W"));
            Login.getInstance().setStrFunction(retrievalCache.getString("system", "N/W"));
            intent.putExtra("Hi", Login.getInstance().getStrUserName() + "!");
        }
        startActivity(intent);
    }
    private class GetRSSDataTask extends AsyncTask<String, Void, List<RssItem>> {
        @Override
        protected List<RssItem> doInBackground(String... urls) {
            Log.d("All N One", Thread.currentThread().getName());

            try {
                RssReader rssReader = new RssReader(urls[0]);
                System.out.print(rssReader.toString());
                return rssReader.getItems();
            } catch (Exception e) {
                Log.e("All N One", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<RssItem> result) {
            try {
                ListView allnoneItems = (ListView) local.findViewById(R.id.listMView);
                ArrayAdapter<RssItem> adapter = new ArrayAdapter<RssItem>(local, android.R.layout.simple_list_item_activated_1, result);
                allnoneItems.setAdapter(adapter);
                allnoneItems.setOnItemClickListener(new ListListener(result, local));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}