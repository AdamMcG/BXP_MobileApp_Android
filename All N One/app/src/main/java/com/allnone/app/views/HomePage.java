package com.allnone.app.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.allnone.app.Models.Login;
import com.allnone.app.Models.Setting;
import com.allnone.app.allnone.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static com.allnone.app.allnone.R.layout.activity_home_page;


public class HomePage extends Activity {
    public Setting mysetting = new Setting();
    private Intent diaryIntent;
    private SharedPreferences myCaches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_home_page);
        myCaches = this.getSharedPreferences("com.allnone.app", Context.MODE_PRIVATE);
        fnStoreCache();
        String username = getIntent().getStringExtra("Hi");
        TextView tv = (TextView) findViewById(R.id.TVusername);
        tv.setText(username);
    }

    private void fnStoreCache() {
        Login myLogin = Login.getInstance();
        myCaches.edit().putString("URL", myLogin.getStrUrlUsed()).apply();
        myCaches.edit().putString("system", myLogin.getStrSystemUsed()).apply();
        myCaches.edit().putString("function", myLogin.getStrFunction()).apply();
        myCaches.edit().putString("userName", myLogin.getStrUserName()).apply();
        myCaches.edit().putInt("intClientId", myLogin.getIntClientid()).apply();
        myCaches.edit().putString("strClient_SessionField", myLogin.getStrClient_SessionField()).apply();

    }

    Drawable drawable_from_url(String url, String sourcename) throws IOException {
        return Drawable.createFromStream(((InputStream) new URL(url).getContent()), sourcename);
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
        myCaches.edit().clear().apply();
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


}
