package com.allnone.app.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allnone.app.Controllers.SettingController;
import com.allnone.app.Models.Login;
import com.allnone.app.Models.Setting;
import com.allnone.app.allnone.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.allnone.app.allnone.R.layout.activity_home_page;


public class HomePage extends Activity {
    public Setting mysetting = new Setting();
    String[] styles;
    Drawable imageBackground = null;
    private Intent diaryIntent;
    private SharedPreferences myCaches;

    public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input;
        input = connection.getInputStream();
        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(x);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myCaches = this.getSharedPreferences("com.allnone.app", Context.MODE_PRIVATE);
        fnStoreCache();
       backgrounfunctionality funct = new backgrounfunctionality();
        funct.execute();

    }

    private void fnSettingbuttonColour(String style, String style2) {
        PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.parseColor(style), PorterDuff.Mode.SRC_ATOP);
        TextView myText = (TextView) findViewById(R.id.textView10);
        myText.setTextColor(Color.parseColor(style2));
        TextView myText2 = (TextView) findViewById(R.id.TVusername);
        myText2.setTextColor(Color.parseColor(style2));
        Button myButton = (Button) findViewById(R.id.button_contacts);
        Drawable myTest = myButton.getBackground();
        myTest.setColorFilter(filter);
        myButton.setBackground(myTest);
        myButton.setTextColor(Color.parseColor(style2));

        Button myButton2 = (Button) findViewById(R.id.button_critical);
        Drawable myTest2 = myButton2.getBackground();
        myTest2.setColorFilter(filter);
        myButton2.setBackground(myTest2);
        myButton2.setTextColor(Color.parseColor(style2));

        Button myButton3 = (Button) findViewById(R.id.button_todo);
        Drawable myTest3 = myButton3.getBackground();
        myTest3.setColorFilter(filter);
        myButton3.setBackground(myTest3);
        myButton3.setTextColor(Color.parseColor(style2));

        Button myButton4 = (Button) findViewById(R.id.button_ticket);
        Drawable myTest4 = myButton4.getBackground();
        myTest4.setColorFilter(filter);
        myButton4.setBackground(myTest4);
        myButton4.setTextColor(Color.parseColor(style2));

        Button myButton5 = (Button) findViewById(R.id.button_today);
        Drawable myTest5 = myButton5.getBackground();
        myTest5.setColorFilter(filter);
        myButton5.setBackground(myTest5);
        myButton5.setTextColor(Color.parseColor(style2));

        Button myButton6 = (Button) findViewById(R.id.button_tomorrow);
        Drawable myTest6 = myButton6.getBackground();
        myTest6.setColorFilter(filter);
        myButton6.setBackground(myTest6);
        myButton6.setTextColor(Color.parseColor(style2));

        Button myButton7 = (Button) findViewById(R.id.button_wixi);
        Drawable myTest7 = myButton7.getBackground();
        myTest7.setColorFilter(filter);
        myButton7.setBackground(myTest7);
        myButton7.setTextColor(Color.parseColor(style2));

        Button myButton8 = (Button) findViewById(R.id.button_web);
        Drawable myTest8 = myButton8.getBackground();
        myTest8.setColorFilter(filter);
        myButton8.setBackground(myTest8);
        myButton8.setTextColor(Color.parseColor(style2));

        Button myButton9 = (Button) findViewById(R.id.button_settings);
        Drawable myTest9 = myButton9.getBackground();
        myTest9.setColorFilter(filter);
        myButton9.setBackground(myTest9);
        myButton9.setTextColor(Color.parseColor(style2));

        Button myButton10 = (Button) findViewById(R.id.button_system);
        Drawable myTest10 = myButton10.getBackground();
        myTest10.setColorFilter(filter);
        myButton10.setBackground(myTest10);
        myButton10.setTextColor(Color.parseColor(style2));
    }

    private void fnStoreCache() {
        Login myLogin = Login.getInstance();
        if(Setting.myListOfButtons.size() !=0)
        myCaches.edit().putString("StoringButtonStyling", Setting.myListOfButtons.get(0).getStrInterface_Button_Styling()).apply();
        myCaches.edit().putString("Logo", mysetting.getStrInterface_Image_LogoURL()).apply();
        myCaches.edit().putString("BackgroundImage", mysetting.getStrInterface_Image_Background()).apply();
        myCaches.edit().putString("URL", myLogin.getStrUrlUsed()).apply();
        myCaches.edit().putString("system", myLogin.getStrSystemUsed()).apply();
        myCaches.edit().putString("function", myLogin.getStrFunction()).apply();
        myCaches.edit().putString("userName", myLogin.getStrUserName()).apply();
        myCaches.edit().putInt("intClientId", myLogin.getIntClientid()).apply();
        myCaches.edit().putString("strClient_SessionField", myLogin.getStrClient_SessionField()).apply();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    public void logout(View view) {
        Login myLogin = Login.getInstance();
        myCaches.edit().remove("intClientId").commit();
        myCaches.edit().remove("strClient_SessionField").commit();
        Intent intent = new Intent(this, RssActivity.class);
        startActivity(intent);
    }

    public void loadToDo(View view) {
        Intent intent = new Intent(this, ToDo.class);
        startActivity(intent);
    }

    public void loadTicket(View view) {
        Intent intent = new Intent(this, Ticket.class);
        startActivity(intent);
    }

    public void loadToday(View view) {
        diaryIntent = new Intent(this, Today.class);
        startActivity(diaryIntent);
    }

    public void loadTomorrow(View view) {
        diaryIntent = new Intent(this, Tomorrow.class);
        startActivity(diaryIntent);

    }

    public void loadContacts(View view) {
        Intent intent = new Intent(this, Contacts.class);
        startActivity(intent);
    }

    public void loadCritical(View view) {
        Intent intent = new Intent(this, Critical.class);
        startActivity(intent);
    }

    public void loadCSettings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void loadWixi(View view) {
        Uri uri = Uri.parse("http://www.bxpsoftware.com/wixi/index.php?title=Main_Page");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void loadWebsite(View view) {
        Uri uri = Uri.parse("http://www.bxpsoftware.com/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void loadSystem(View view) {
        Uri uri = Uri.parse("https://ww3.allnone.ie/client/client_allnone/main/main.asp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private class backgrounfunctionality extends AsyncTask<String, String, String> {
        SettingController myController = new SettingController();

        @Override
        protected String doInBackground(String... params) {
            try {
                imageBackground = drawableFromUrl(mysetting.getStrInterface_Image_Background());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "success";
    }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            setContentView(activity_home_page);

            RelativeLayout mylayout = (RelativeLayout) findViewById(R.id.RelLayoutHome);
            mylayout.setBackground(imageBackground);
            styles = myCaches.getString("StoringButtonStyling", "N/A").split(",");
            fnSettingbuttonColour(styles[0], styles[1]);
            TextView username = (TextView) findViewById(R.id.TVusername);
            username.setText(myCaches.getString("userName", "N/A"));


        }
    }


}
