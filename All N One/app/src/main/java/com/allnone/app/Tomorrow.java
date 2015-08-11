package com.allnone.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.allnone.app.Controllers.DiaryController;
import com.allnone.app.Models.Appointment;
import com.allnone.app.Models.diary;
import com.allnone.app.allnone.R;

import java.text.ParseException;


public class Tomorrow extends Activity {
    ListView tomorrowAppointment;
    ListView tomorrowListee;
    diary tomorrowDiary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diaryFunctionality tomorrowFunctionality = new diaryFunctionality();
        tomorrowFunctionality.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tomorrow, menu);
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

    private class diaryFunctionality extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                DiaryController fillingDiary = new DiaryController();
                String diaryFunction = "diary_tomorrow";
                fillingDiary.fn_diaryPostCall(diaryFunction);
                tomorrowDiary = fillingDiary.getMyDiary();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return "success";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            setContentView(R.layout.activity_tomorrow);
            tomorrowAppointment = (ListView) findViewById(R.id.tomorrow_AppointmentList);
            tomorrowListee = (ListView) findViewById(R.id.tomorrow_listeeList);
            ArrayAdapter<Appointment> myAdapter;
            Context local = getApplicationContext();
            myAdapter = new ArrayAdapter<Appointment>(local, android.R.layout.simple_list_item_1, tomorrowDiary.colAppointment);
            tomorrowAppointment.setAdapter(myAdapter);

        }
    }

}
