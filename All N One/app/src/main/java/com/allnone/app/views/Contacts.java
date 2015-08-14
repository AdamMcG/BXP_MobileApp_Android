package com.allnone.app.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.allnone.app.allnone.R;

public class Contacts extends Activity {
    static String strContactField;
    EditText strContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        strContact = (EditText) this.findViewById(R.id.editText_contact);
        if (strContact != null) {
            strContact.setText(strContactField);
        }
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
}
