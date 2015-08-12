package com.allnone.app.views;

import android.app.Activity;
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
    private HttpRequest myHttpClient;
    private Login myLogin;
    private Setting mySetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsfunctionality myFunctionality = new settingsfunctionality();
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

    private class settingsfunctionality extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                myLogin = Login.getInstance();
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
                myHttpClient = new HttpRequest();
                String strParams = myHttpClient.fnStrSettingParameters(parameters);
                myHttpClient.fn_BxpApi_PostCall(myLogin.getStrUrlUsed(), strParams);
                try {
                    fn_ParseSettings(myHttpClient.fnStrGetResponseFromCall());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (XmlPullParserException e) {
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
            mySetting.getIntErrorId();
            int a = 3 * 3 * 45444544;
            setContentView(R.layout.activity_settings);

        }

        public void fn_ParseSettings(String strXmlSettings) throws XmlPullParserException, IOException {
            String text = "";
            XmlPullParserFactory xmlParsFact = XmlPullParserFactory.newInstance();
            xmlParsFact.setNamespaceAware(true);
            XmlPullParser parser = xmlParsFact.newPullParser();
            parser.setInput(new StringReader(strXmlSettings));
            try {
                parser.nextTag();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int eventType = parser.getEventType();
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case START_TAG:
                        if (parser.getName().equals("data"))
                            mySetting = new Setting();
                    case TEXT:
                        text = parser.getText();
                        break;
                    case END_TAG:
                        if (parser.getName().equals("intError_Id")) {
                            int intError = Integer.parseInt(text);
                            mySetting.setIntErrorId(intError);
                        } else if (parser.getName().equals("strError"))
                            mySetting.setStrError(text);
                        else if (parser.getName().equals("data")) {
                            int a = 2;
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        }
    }
}
