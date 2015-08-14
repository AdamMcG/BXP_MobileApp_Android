package com.allnone.app.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.allnone.app.Controllers.HttpRequest;
import com.allnone.app.Models.Login;
import com.allnone.app.Models.Setting;
import com.allnone.app.allnone.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;


public class Settings extends Activity {
    private Setting mySetting;
    private Context local;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        local = this;
        settingsFunctionality myFunctionality = new settingsFunctionality();
        myFunctionality.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }

    public XmlPullParser fn_createSettingsParser(String strXmlSettings) {
        XmlPullParser parser = null;
        try {
            XmlPullParserFactory xmlParsFact = XmlPullParserFactory.newInstance();
            xmlParsFact.setNamespaceAware(true);
            parser = xmlParsFact.newPullParser();
            parser.setInput(new StringReader(strXmlSettings));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return parser;
    }

    public void fn_ParseSettings(XmlPullParser parser) throws XmlPullParserException, IOException {
        String text = "";

        try {
            parser.nextTag();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int eventType = parser.getEventType();
        while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
            text = readingThroughXMLText(parser, text, eventType);
            eventType = parser.next();
        }
    }

    private String readingThroughXMLText(XmlPullParser parser, String text, int eventType) {
        switch (eventType) {
            case START_TAG:
                if (parser.getName().equals("data"))
                    mySetting = new Setting();
            case TEXT:
                text = parser.getText();
                break;
            case END_TAG:
                assigningToSettingsObject(parser, text);
                break;
            default:
                break;
        }
        return text;
    }

    private void assigningToSettingsObject(XmlPullParser parser, String text) {
        if (parser.getName().equals("intError_Id")) {
            int intError = Integer.parseInt(text);
            mySetting.setIntErrorId(intError);
        } else if (parser.getName().equals("strError"))
            mySetting.setStrError(text);
        else if (parser.getName().equals("data")) {
            System.out.println("Data goes here!");
        }
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
        Login myLogin = Login.getInstance();
        Context context = local;
        AlertDialog.Builder noNetwork = new AlertDialog.Builder(context);
        noNetwork.setMessage(myLogin.getStrError());
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

    private class settingsFunctionality extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Login myLogin = Login.getInstance();
                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                //region Parameters
                BasicNameValuePair parameter = new BasicNameValuePair("strFunction", "settings");
                parameters.add(parameter);
                parameter = new BasicNameValuePair("strSystem", myLogin.getStrSystemUsed());
                parameters.add(parameter);
                String a = String.valueOf(myLogin.getIntClientid());
                parameter = new BasicNameValuePair("intClient_Id", a);
                parameters.add(parameter);
                parameter = new BasicNameValuePair("strClient_SessionField", myLogin.getStrClient_SessionField());
                parameters.add(parameter);
                //endregion
                HttpRequest myHttpClient = new HttpRequest();
                String strParams = myHttpClient.fnStrSettingParameters(parameters);
                myHttpClient.fn_BxpApi_PostCall(myLogin.getStrUrlUsed(), strParams);
                try {
                    fn_ParseSettings(fn_createSettingsParser(myHttpClient.fnStrGetResponseFromCall()));
                } catch (IOException e) {
                    e.printStackTrace();
                    return "fail";
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
                return "fail";
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
            setContentView(R.layout.activity_settings);
            if (s.equals("success")) {
                fn_createSuccessDialog("Success!");
            } else {
                fn_createErrorDialog();
            }
        }
    }
}
