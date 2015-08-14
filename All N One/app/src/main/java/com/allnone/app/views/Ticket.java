package com.allnone.app.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.allnone.app.allnone.R;


public class Ticket extends Activity {
    static String strTicketSubject;
    static String StrTicketDescription;
    EditText ticketSubject;
    EditText ticketDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        ticketSubject = (EditText) this.findViewById(R.id.editText_ticketsub);
        ticketDescription = (EditText) this.findViewById(R.id.editText_ticketdes);


        if (strTicketSubject != null)
            ticketSubject.setText(strTicketSubject);
        ticketDescription.setText(StrTicketDescription);
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
}
