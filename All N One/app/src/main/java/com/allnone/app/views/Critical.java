package com.allnone.app.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.allnone.app.Controllers.GridViewAdapter;
import com.allnone.app.Controllers.HamsterController;
import com.allnone.app.Models.Setting;
import com.allnone.app.allnone.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;


public class Critical extends Activity {

    GridView hamsterBoard;
    HamsterController myController;
    Context local;
    Drawable imageBackground = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_critical);
        local = this;
        backgrounfunctionality funct = new backgrounfunctionality();
        funct.execute();
        hamsterBoard= (GridView) this.findViewById(R.id.GridForHamsters);
        hamsterFunctionality myHamsterBoard = new hamsterFunctionality();
        myHamsterBoard.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_critical, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }


    public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input;
        input = connection.getInputStream();
        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(x);

    }

    private class backgrounfunctionality extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            Setting mysetting = new Setting();
            try {
                imageBackground = drawableFromUrl(mysetting.getStrInterface_Image_Background());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "success";
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            RelativeLayout mylayout = (RelativeLayout) findViewById(R.id.relLayoutCritical);
            mylayout.setBackground(imageBackground);
        }
    }



    private class hamsterFunctionality extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                myController = new HamsterController();
                myController.fn_HamsterRESTPostCall();
                myController.fn_parseListerXMLContents(myController.fn_createParser(myController.myHttp.fnStrGetResponseFromCall()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return "success";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            GridViewAdapter myAdapter = new GridViewAdapter(local, myController.getHamLists().getListOfHamsters());
            hamsterBoard.setAdapter(myAdapter);
        }
    }

}
