package com.allnone.app.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allnone.app.Controllers.ListerController;
import com.allnone.app.Models.Listee;
import com.allnone.app.Models.Setting;
import com.allnone.app.allnone.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;


public class ToDo extends Activity {
    static String strToDoName;
    EditText enterListee;
    ListView myListView;
    Drawable imageBackground = null;
    ListerController myController = new ListerController();
    String listeeString = null;
    SharedPreferences mycach;
    String[] styles;

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
        setContentView(R.layout.activity_to_do);
        mycach = this.getSharedPreferences("com.allnone.app", MODE_PRIVATE);
        styles = mycach.getString("StoringButtonStyling", "N/A").split(",");
        backgrounfunctionality funct = new backgrounfunctionality();
        funct.execute();
        myListView = (ListView) findViewById(R.id.ListOfToDoListees);
        enterListee = (EditText) findViewById(R.id.editText_listee);
        if (strToDoName != null)
            enterListee.setText(strToDoName);
        Button buttonListee = (Button)findViewById(R.id.button_add);
        settingButtonClickListener(buttonListee);
        ListeeFunctionality lister = new ListeeFunctionality();
        listeeString = enterListee.getText().toString();
        lister.execute();
    }

    private void settingButtonClickListener(Button buttonListee) {
        buttonListee.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                insertListFunctionality myFunction = new insertListFunctionality();
                myFunction.execute();

            }
        });
    }

    public void fn_createSuccessDialog(String successDialog) {
        Context context = this;
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

    @Override
    public void onPause() {
        super.onPause();
        strToDoName = enterListee.getText().toString();
    }

    private class ListeeFunctionality extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String a = "list_listee_incomplete";
            try {
                myController.fn_ListerPOSTRestCall(a, "today");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return "success";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Context local = getApplicationContext();
            ArrayAdapter<Listee> myAdapter = getListeeArrayAdapter(local);
            View headerView = getLayoutInflater().inflate(R.layout.headerforlist, null);
            TextView text = (TextView) headerView.findViewById(R.id.headerView);
            text.setTextColor(Color.parseColor(styles[1]));
            text.setText("ToDo List");
            myListView.addHeaderView(headerView);
            myListView.setAdapter(myAdapter);
        }

        private ArrayAdapter<Listee> getListeeArrayAdapter(final Context local) {
            return new ArrayAdapter<Listee>(local, android.R.layout.simple_list_item_2, android.R.id.text1, myController.getLister().getListees()) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    text1.setTextColor(Color.parseColor(styles[1]));
                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                    text2.setTextColor(Color.parseColor(styles[1]));
                    text1.setText(myController.getLister().getListees().get(position).strLister_Title);
                    text2.setText(myController.getLister().getListees().get(position).strLister_FromDate);
                    return view;
                }
            };
        }
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
            RelativeLayout mylayout = (RelativeLayout) findViewById(R.id.relLayoutToDo);
            mylayout.setBackground(imageBackground);
        }
    }


    private class insertListFunctionality extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                String a = listeeString;
                myController.fn_ListerPOSTRestCall("insert_listee", a);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return "success";
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            fn_createSuccessDialog("Successfully added a Listee.");
        }
    }
}
