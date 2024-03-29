package com.allnone.app.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.allnone.app.Controllers.HttpRequest;
import com.allnone.app.Controllers.SettingController;
import com.allnone.app.Models.Login;
import com.allnone.app.Models.Setting;
import com.allnone.app.allnone.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;

public class LoginPage extends Activity {
    static String strStaticSystem;
    static String strStaticUsername;
    SharedPreferences myCache;
    SettingController myController = new SettingController();
    EditText strSystem;
    EditText a;
    EditText strPassword;
    HttpRequest myClient = new HttpRequest();
    Login myLogin;
    Intent i;

    private LoginPage local;

    public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input;
        input = connection.getInputStream();
        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(x);

    }

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
        myCache = this.getSharedPreferences("com.allnone.app", Context.MODE_PRIVATE);
        local = this;
        setContentView(R.layout.activity_login_page);
        strSystem = (EditText) findViewById(R.id.TFsystem);
            backgrounfunctionality funct = new backgrounfunctionality();
            funct.execute();
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

    private class backgrounfunctionality extends AsyncTask<String, String, String> {
        ImageView view;
        Drawable background = null;
        Drawable LogoImage = null;

        @Override
        protected String doInBackground(String... params) {
            Setting mysetting = new Setting();
            try {
                background = drawableFromUrl(mysetting.getStrInterface_Image_Background());
                LogoImage = drawableFromUrl(mysetting.getStrInterface_Image_LogoURL());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "success";
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            view = (ImageView) findViewById(R.id.imageView);
            view.setBackground(LogoImage);
            RelativeLayout mylayout = (RelativeLayout) findViewById(R.id.relLayoutLogin);
            mylayout.setBackground(background);
        }
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
                if (Login.getInstance().getIntClientid() != 0) {
                    myController.fn_SettingsPostCall(Login.getInstance().getStrUrlUsed());
                    myController.fn_ButtonsPostCall(Login.getInstance().getStrUrlUsed(), 10);
                }
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
