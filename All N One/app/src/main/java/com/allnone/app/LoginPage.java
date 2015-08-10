package com.allnone.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.allnone.app.Models.Login;
import com.allnone.app.allnone.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class LoginPage extends Activity {

    EditText strSystem = (EditText) findViewById(R.id.TFsystem);
    EditText a = (EditText) findViewById(R.id.TFusername);
    EditText strPassword = (EditText) findViewById(R.id.TFpassword);
    HttpRequest myClient = new HttpRequest();
    private Login myLogin;

    public String getStrSystem() {
        return strSystem.getText().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
    }

    public List<NameValuePair> fn_FillParameters() {
        List<NameValuePair> kvParameters = new ArrayList<NameValuePair>();
        BasicNameValuePair parameter;
        parameter = new BasicNameValuePair("strFunction", "login");
        kvParameters.add(parameter);
        parameter = new BasicNameValuePair("strSystem", strSystem.getText().toString());
        kvParameters.add(parameter);
        parameter = new BasicNameValuePair("strUsername", a.getText().toString());
        kvParameters.add(parameter);
        parameter = new BasicNameValuePair("strPassword", strPassword.getText().toString());
        kvParameters.add(parameter);
        return kvParameters;
    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.Alogin) {
            String strRestFunctionURL = "http://ww3.allnone.ie/client/" + getStrSystem() + "/cti/userAPP_Main.asp";
            myClient.fn_BXP_PostCall(strRestFunctionURL, fn_FillParameters());
            Intent i = new Intent(this, HomePage.class);
            String strName = a.getText().toString();
            i.putExtra("Hi", strName + "!");
            startActivity(i);
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
}
