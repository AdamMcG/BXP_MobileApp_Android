package com.allnone.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.allnone.app.Models.Login;
import com.allnone.app.allnone.R;


public class HomePage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Login myLogin = Login.getInstance();
        setContentView(R.layout.activity_home_page);
        String username = getIntent().getStringExtra("Hi");

        TextView tv = (TextView) findViewById(R.id.TVusername);
        tv.setText(username);
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

    public void loadToDo(View view) {
        Intent intent = new Intent(this, ToDo.class);
        startActivity(intent);
    }

    public void loadTicket(View view) {
        Intent intent = new Intent(this, Ticket.class);
        startActivity(intent);
    }

    public void loadToday(View view) {
        Intent intent = new Intent(this, Today.class);
        startActivity(intent);
    }

    public void loadTomorrow(View view) {
        Intent intent = new Intent(this, Tomorrow.class);
        startActivity(intent);
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
