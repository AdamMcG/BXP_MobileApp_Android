package com.allnone.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.allnone.app.Models.diary;
import com.allnone.app.allnone.R;

public class Today extends Activity {
    ListView todayAppointment;
    ListView todayListee;
    diary todayDiary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        Intent i = getIntent();
        todayDiary = (diary) i.getSerializableExtra("Today's diary");
        todayAppointment = (ListView) findViewById(R.id.today_AppointmentList);
        todayListee = (ListView) findViewById(R.id.today_listeeList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_today, menu);
        return true;
    }
    public void loadToDo(View view) {
        Intent intent = new Intent(this, ToDo.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }
}
