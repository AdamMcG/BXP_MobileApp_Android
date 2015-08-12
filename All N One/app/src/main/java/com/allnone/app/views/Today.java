package com.allnone.app.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.allnone.app.Controllers.DiaryController;
import com.allnone.app.Models.Appointment;
import com.allnone.app.Models.diary;
import com.allnone.app.allnone.R;

import java.text.ParseException;

public class Today extends Activity {
    ListView todayAppointment;
    ListView todayListee;
    diary todayDiary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diaryFunctionality dFunct = new diaryFunctionality();
        dFunct.execute();

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

    private class diaryFunctionality extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                DiaryController fillingDiary = new DiaryController();
                String diaryFunction = "diary_today";
                fillingDiary.fn_diaryRestCallPost(diaryFunction);
                todayDiary = fillingDiary.getMyDiary();
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
            setContentView(R.layout.activity_today);
            todayAppointment = (ListView) findViewById(R.id.today_AppointmentList);
            todayListee = (ListView) findViewById(R.id.today_listeeList);
            Context local = getApplicationContext();
            ArrayAdapter<Appointment> myAdapter = new ArrayAdapter<Appointment>(local, android.R.layout.simple_list_item_2, android.R.id.text1, todayDiary.colAppointment) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                    text1.setText(todayDiary.colAppointment.get(position).returnDateStartString());
                    text2.setText(todayDiary.colAppointment.get(position).toString());
                    return view;
                }
            };
            View headerView = getLayoutInflater().inflate(R.layout.headerforlist, null);
            TextView text = (TextView) headerView.findViewById(R.id.headerView);
            text.setText("Appointment List");
            todayAppointment.addHeaderView(headerView);
            todayAppointment.setAdapter(myAdapter);
        }
    }
}
