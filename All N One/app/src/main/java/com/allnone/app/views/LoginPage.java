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
import android.widget.EditText;

import com.allnone.app.Controllers.HttpRequest;
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
import static org.xmlpull.v1.XmlPullParser.TEXT;

public class LoginPage extends Activity {
    static String strStaticSystem;
    static String strStaticUsername;
    EditText strSystem;
    EditText a;
    EditText strPassword;
    HttpRequest myClient = new HttpRequest();
    Login myLogin;
    Intent i;
    private LoginPage local;

    public String getStrSystem() {
        return "client_" + strSystem.getText().toString();
    }

    @Override
    public void onPause() {
        super.onPause();
        strStaticSystem = strSystem.getText().toString();
        strStaticUsername = a.getText().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        local = this;
        setContentView(R.layout.activity_login_page);
        strSystem = (EditText) findViewById(R.id.TFsystem);

        a = (EditText) findViewById(R.id.TFusername);
        strPassword = (EditText) findViewById(R.id.TFpassword);
        if (strStaticSystem != null) {
            strSystem.setText(strStaticSystem);
            a.setText(strStaticUsername);
        }
    }

    public List<NameValuePair> fn_FillParameters() {
        List<NameValuePair> kvParameters = new ArrayList<NameValuePair>();
        BasicNameValuePair parameter;
        parameter = new BasicNameValuePair("strFunction", "login");
        kvParameters.add(parameter);
        parameter = new BasicNameValuePair("strSystem", getStrSystem());
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

    public XmlPullParser fn_createLoginParser(String xmlString) {
        XmlPullParser parser = null;
        try {
            myLogin = Login.getInstance();
            myLogin.setStrUserName(a.getText().toString());
            myLogin.setStrSystemUsed(getStrSystem());
            XmlPullParserFactory xmlParsFact = XmlPullParserFactory.newInstance();
            xmlParsFact.setNamespaceAware(true);
            parser = xmlParsFact.newPullParser();
            parser.setInput(new StringReader(xmlString));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return parser;
    }

    public void fn_ParsingThroughXMLDocument(XmlPullParser parser) throws IOException {
        try {
            String text = "";
            parser.nextTag();
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                text = ReadingThroughXML(parser, text, eventType);
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    private String ReadingThroughXML(XmlPullParser parser, String text, int eventType) {
        switch (eventType) {
            case TEXT:
                text = parser.getText();
                break;
            case END_TAG:
                assigningToLoginObject(parser, text);
                break;
            default:
                break;
        }
        return text;
    }

    private void assigningToLoginObject(XmlPullParser parser, String text) {
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
    }

    public void fn_createErrorDialog() {
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

    private class LoginFunctionality extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String strRestFunctionURL = "https://ww3.allnone.ie/client/" + getStrSystem() + "/cti/userAPP_main.asp";
            myLogin = Login.getInstance();
            myLogin.setStrUrlUsed(strRestFunctionURL);
            myClient.fn_BxpApi_PostCall(strRestFunctionURL, myClient.fnStrSettingParameters(fn_FillParameters()));
            try {
                fn_ParsingThroughXMLDocument(fn_createLoginParser(myClient.fnStrGetResponseFromCall()));

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
            if (myLogin.getIntError() == 0 && myLogin.getIntClientid() != 0) {
                String strName = a.getText().toString();
                i.putExtra("Hi", strName + "!");
                startActivity(i);
            } else {
                fn_createErrorDialog();
            }
        }
    }
}
