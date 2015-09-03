package com.allnone.app.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allnone.app.Controllers.CampaignController;
import com.allnone.app.Models.CampaignItem;
import com.allnone.app.Models.Setting;
import com.allnone.app.allnone.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Contacts extends Activity {
    static String strContactField;
    EditText strContact;
    ListView ContactView;
    Drawable imageBackground = null;
    CampaignController myController = new CampaignController();
    Context local = null;
    String searchterm = null;

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
        setContentView(R.layout.activity_contacts);
        backgrounfunctionality funct = new backgrounfunctionality();
        funct.execute();
        ContactView = (ListView) findViewById(R.id.contactListView);
        local = this;
        strContact = (EditText) this.findViewById(R.id.editText_contact);
        if (strContact != null) {
            strContact.setText(strContactField);
        }
        SharedPreferences mycach = local.getSharedPreferences("com.allnone.app", MODE_PRIVATE);
        String[] styles = mycach.getString("StoringButtonStyling", "N/A").split(",");
        Button contactButton = (Button) this.findViewById(R.id.button_searchcontact);
        Drawable d = contactButton.getBackground();
        PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.parseColor(styles[0]), PorterDuff.Mode.SRC_ATOP);
        d.setColorFilter(filter);
        contactButton.setTextColor(Color.parseColor(styles[1]));
        contactButtonClickListener(contactButton);

    }

    private void contactButtonClickListener(Button buttonListee) {
        buttonListee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchterm = strContact.getText().toString();
                contactRetrievalFunctionality myFunction = new contactRetrievalFunctionality();
                myFunction.execute();

            }
        });
    }

    public ArrayAdapter<CampaignItem> GetCampaignItems(final Context local) {

        return new ArrayAdapter<CampaignItem>(local, android.R.layout.simple_list_item_2, android.R.id.text1, myController.getMyCampaign().getListOfCampaigns()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                SharedPreferences mycach = local.getSharedPreferences("com.allnone.app", MODE_PRIVATE);
                String[] styles = mycach.getString("StoringButtonStyling", "N/A").split(",");
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                text1.setTextColor(Color.parseColor(styles[1]));
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text2.setTextColor(Color.parseColor(styles[1]));
                String strPhone = myController.getMyCampaign().getListOfCampaigns().get(position).itemsOfCampaign.get(2);
                String strEmail = myController.getMyCampaign().getListOfCampaigns().get(position).itemsOfCampaign.get(3);
                if (myController.getMyCampaign().getListOfCampaigns().get(position).itemsOfCampaign.get(3) == null)
                    strEmail = "none provided";
                if (myController.getMyCampaign().getListOfCampaigns().get(position).itemsOfCampaign.get(2) == null)
                    strPhone = "None provided";
                text1.setText("" + myController.getMyCampaign().getListOfCampaigns().get(position).getIntId());
                String s = (myController.getMyCampaign().getListOfCampaigns().get(position).itemsOfCampaign.get(0)
                        + " " + myController.getMyCampaign().getListOfCampaigns().get(position).itemsOfCampaign.get(1))
                        + "\nWork Phone:" + strPhone + "\nEmail:" + strEmail + "\n\n";
                text2.setText(s);
                return view;
            }
        };
    }

    @Override
    public void onPause() {
        super.onPause();
        strContactField = strContact.getText().toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
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
            RelativeLayout mylayout = (RelativeLayout) findViewById(R.id.relLayoutContact);
            mylayout.setBackground(imageBackground);
        }
    }





    private class contactRetrievalFunctionality extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            if (searchterm.contains(" ")) {
                myController.fnSearchByFullName(searchterm, 469);
            } else {
                myController.fn_RetrieveCampaignItemsByFirstName(searchterm, 469);
                if (myController.getMyCampaign().getListOfCampaigns().size() == 0)
                    myController.fn_RetrieveCampaignItemsByLastName(searchterm, 469);
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
            Context local2 = getApplicationContext();
            ArrayAdapter<CampaignItem> myAdapter = GetCampaignItems(local2);
            ContactView.setAdapter(myAdapter);
            View headerView = getLayoutInflater().inflate(R.layout.headerforlist, null);
            TextView text = (TextView) headerView.findViewById(R.id.headerView);
            text.setTextColor(Color.parseColor("#000000"));
            text.setText("Contacts");
            if (ContactView.getHeaderViewsCount() == 0)
                ContactView.addHeaderView(headerView);
            fn_createSuccessDialog("Successful retrieval!");

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
