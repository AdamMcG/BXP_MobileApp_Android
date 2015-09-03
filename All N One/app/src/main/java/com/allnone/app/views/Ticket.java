package com.allnone.app.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.allnone.app.Controllers.CampaignController;
import com.allnone.app.Models.Setting;
import com.allnone.app.allnone.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Ticket extends Activity {
    static String strTicketSubject;
    static String StrTicketDescription;
    Drawable imageBackground = null;
    Context local = null;
    EditText ticketSubject;
    EditText ticketDescription;
    CampaignController myController = new CampaignController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        local = this;
        backgrounfunctionality functionality = new backgrounfunctionality();
        functionality.execute();
        ticketSubject = (EditText) this.findViewById(R.id.editText_ticketsub);
        ticketDescription = (EditText) this.findViewById(R.id.editText_ticketdes);
        Button myButton = (Button) this.findViewById(R.id.button_ticket);
        TicketButtonClickListener(myButton);
        if (strTicketSubject != null)
            ticketSubject.setText(strTicketSubject);
        ticketDescription.setText(StrTicketDescription);
    }

    private void TicketButtonClickListener(Button buttonListee) {
        buttonListee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactRetrievalFunctionality myFunction = new contactRetrievalFunctionality();
                myFunction.execute();
            }
        });
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

    private class backgrounfunctionality extends AsyncTask<String, String, String>
    {
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
            setContentView(R.layout.activity_ticket);
            RelativeLayout mylayout = (RelativeLayout) findViewById(R.id.relLayoutTicket);
            mylayout.setBackground(imageBackground);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        strTicketSubject = ticketSubject.getText().toString();
        StrTicketDescription = ticketDescription.getText().toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ticket, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    private class contactRetrievalFunctionality extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            strTicketSubject = ticketSubject.getText().toString();
            StrTicketDescription = ticketDescription.getText().toString();
        }

        @Override
        protected String doInBackground(String... params) {
            myController.fn_InjectCampaignRecordToCampaign(strTicketSubject, StrTicketDescription, 292);
            return "success";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            fn_createSuccessDialog("Successful Injection!");

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
