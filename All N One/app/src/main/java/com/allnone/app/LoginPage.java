package com.allnone.app;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.allnone.app.Models.Login;
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


public class LoginPage extends Activity {

    EditText strSystem;
    EditText a;
    EditText strPassword;
    HttpRequest myClient = new HttpRequest();
    Login myLogin;
    Intent i;

    public String getStrSystem() {
        return strSystem.getText().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        strSystem = (EditText) findViewById(R.id.TFsystem);
        a = (EditText) findViewById(R.id.TFusername);
        strPassword = (EditText) findViewById(R.id.TFpassword);
    }

    public List<NameValuePair> fn_FillParameters() {
        List<NameValuePair> kvParameters = new ArrayList<NameValuePair>();
        BasicNameValuePair parameter;
        parameter = new BasicNameValuePair("strFunction", "login");
        kvParameters.add(parameter);
        parameter = new BasicNameValuePair("strSystem", strSystem.getText().toString());
        kvParameters.add(parameter);
        parameter = new BasicNameValuePair("strClient_Username", a.getText().toString());
        kvParameters.add(parameter);
        parameter = new BasicNameValuePair("strClient_Password", strPassword.getText().toString());
        kvParameters.add(parameter);
        return kvParameters;
    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.Alogin) {
            i = new Intent(this, HomePage.class);
            LoginFunctionality logIntoBxp = new LoginFunctionality();
            logIntoBxp.execute();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }

    //Using XMLPullParser to parse through a string of an xml document. Will be called in doInBackground()
    public void ParsingThroughMydocument(String xmlString) throws IOException {
        try {
            String text = "";
            myLogin = Login.getInstance();
            myLogin.setStrUserName(a.getText().toString());
            myLogin.setStrSystemUsed(strSystem.getText().toString());
            XmlPullParserFactory xmlParsFact = XmlPullParserFactory.newInstance();
            xmlParsFact.setNamespaceAware(true);
            XmlPullParser parser = xmlParsFact.newPullParser();
            parser.setInput(new StringReader(xmlString));
            parser.nextTag();
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                switch (eventType) {
                    case START_TAG:
                        if (tagName.equals("data")) ;
                    case TEXT:
                        text = parser.getText();
                        break;
                    case END_TAG:
                        if (parser.getName().equals("strFunction")) {
                            myLogin.setStrFunction(text);
                        } else if (parser.getName().equals("intErrorId")) {
                            int errorId = Integer.parseInt(text);
                            myLogin.setIntError(errorId);
                        } else if (parser.getName().equals("strError")) {
                            myLogin.setStrError(text);
                        } else if (parser.getName().equals("intClient_Id")) {
                            int clientId = Integer.parseInt(text);
                            myLogin.setIntClientid(clientId);
                        } else if (parser.getName().equals("strClient_SessionField")) {
                            myLogin.setStrClient_SessionField(text);
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    private class LoginFunctionality extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String strRestFunctionURL = "http://ww3.allnone.ie/client/" + getStrSystem() + "/cti/userAPP_main.asp";
            myClient.fnHttpPost(strRestFunctionURL, myClient.fnStrSettingParameters(fn_FillParameters()));
            String strLoginContent = myClient.fnStrGetResponseFromCall();
            try {
                ParsingThroughMydocument(strLoginContent);

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
            String strName = a.getText().toString();
            i.putExtra("Hi", strName + "!");
            startActivity(i);
        }
    }
}
