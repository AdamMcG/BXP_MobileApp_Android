package com.allnone.app.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allnone.app.Controllers.DiaryController;
import com.allnone.app.Controllers.ListerController;
import com.allnone.app.Models.Appointment;
import com.allnone.app.Models.Listee;
import com.allnone.app.Models.Setting;
import com.allnone.app.Models.diary;
import com.allnone.app.allnone.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;


public class Tomorrow extends Activity {
    ListView tomorrowAppointment;
    Drawable imageBackground = null;
    ListView tomorrowListee;
    diary tomorrowDiary;
    ListerController myController;
    Context local;
    private SharedPreferences mycach;
    private String[] styles;

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
        mycach = this.getSharedPreferences("com.allnone.app", MODE_PRIVATE);
        styles = mycach.getString("StoringButtonStyling", "N/A").split(",");
        setContentView(R.layout.activity_tomorrow);
        local = this;
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
                Setting mysetting = new Setting();
                imageBackground = drawableFromUrl(mysetting.getStrInterface_Image_Background());
                myController = new ListerController();
                myController.fn_ListerPOSTRestCall("list_listee_due", "tomorrow");
                DiaryController fillingDiary = new DiaryController();
                String diaryFunction = "diary_tomorrow";
                fillingDiary.fn_diaryRestCallPost(diaryFunction);
                tomorrowDiary = fillingDiary.getMyDiary();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
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
            RelativeLayout layouts = (RelativeLayout) findViewById(R.id.relLayoutTomorrow);
            layouts.setBackground(imageBackground);
            tomorrowAppointment = (ListView) findViewById(R.id.tomorrow_AppointmentList);
            tomorrowListee = (ListView) findViewById(R.id.tomorrow_listeeList);
            Context local = getApplicationContext();
            ArrayAdapter<Appointment> myAdapter = getAppointmentArrayAdapter(local);
            tomorrowAppointment.setAdapter(myAdapter);
            ArrayAdapter<Listee> myAdapter2 = getListeeArrayAdapter(local);
            tomorrowListee.setAdapter(myAdapter2);
            View headerView = getLayoutInflater().inflate(R.layout.headerforlist, null);
            TextView text = (TextView) headerView.findViewById(R.id.headerView);
            text.setText("Lister");
            text.setTextColor(Color.parseColor(styles[1]));
            tomorrowListee.addHeaderView(headerView);
            headerView = getLayoutInflater().inflate(R.layout.headerforlist, null);
            text = (TextView) headerView.findViewById(R.id.headerView);
            text.setText("Diary");
            text.setTextColor(Color.parseColor(styles[1]));
            tomorrowAppointment.addHeaderView(headerView);
        }

        private ArrayAdapter<Listee> getListeeArrayAdapter(final Context local) {
            return new ArrayAdapter<Listee>(local, android.R.layout.simple_list_item_2, android.R.id.text1, myController.getLister().getListees()) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                    text1.setTextColor(Color.parseColor(styles[1]));
                    text2.setTextColor(Color.parseColor(styles[1]));
                    text1.setText(myController.getLister().getListees().get(position).strLister_Title);
                    text2.setText("22332");
                    return view;
                }
            };
        }

        private ArrayAdapter<Appointment> getAppointmentArrayAdapter(final Context local) {
            return new ArrayAdapter<Appointment>(local, android.R.layout.simple_list_item_2, android.R.id.text1, tomorrowDiary.colAppointment) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                    text1.setTextColor(Color.parseColor(styles[1]));
                    text2.setTextColor(Color.parseColor(styles[1]));
                    text1.setText(tomorrowDiary.colAppointment.get(position).returnDateStartString());
                    text2.setText(tomorrowDiary.colAppointment.get(position).toString());
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
    }

}
