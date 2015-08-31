package com.allnone.app.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.allnone.app.Controllers.ListerController;
import com.allnone.app.Models.Appointment;
import com.allnone.app.Models.Listee;
import com.allnone.app.Models.diary;
import com.allnone.app.allnone.R;

import java.text.ParseException;

public class Today extends Activity {
    ListView todayAppointment;
    ListView todayListee;
    diary todayDiary;
    ListerController myController;
    Context local;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        local = this;
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
                myController = new ListerController();
                myController.fn_ListerPOSTRestCall("list_listee_due", "today");
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
            ArrayAdapter<Appointment> myAdapter = getAppointmentArrayAdapter(local);
            todayAppointment.setAdapter(myAdapter);
            View headerView2 = getLayoutInflater().inflate(R.layout.headerforlist, null);
            ArrayAdapter<Listee> myAdapter2 = getListeeArrayAdapter(local);
            todayListee.addHeaderView(headerView2);
            todayListee.setAdapter(myAdapter2);

            fn_createSuccessDialog("Successful retrieval!");

        }

        private ArrayAdapter<Appointment> getAppointmentArrayAdapter(final Context local) {
            return new ArrayAdapter<Appointment>(local, android.R.layout.simple_list_item_2, android.R.id.text1, todayDiary.colAppointment) {
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
        }

        private ArrayAdapter<Listee> getListeeArrayAdapter(final Context local) {
            return new ArrayAdapter<Listee>(local, android.R.layout.simple_list_item_2, android.R.id.text1, myController.getLister().getListees()) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                    text1.setText(myController.getLister().getListees().get(position).strLister_Title);
                    text2.setText("22332");
                    return view;
                }
            };
        }

        public void fn_createSuccessDialog(String successDialog) {
            Context context = local;
            AlertDialog.Builder noNetwork = new AlertDialog.Builder(context);
            noNetwork.setMessage(successDialog);
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

        public void fn_createErrorDialog() {
            Context context = local;
            AlertDialog.Builder noNetwork = new AlertDialog.Builder(context);
            noNetwork.setMessage(todayDiary.getStrError());
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
    }
}
