package com.allnone.app.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.allnone.app.Controllers.ListerController;
import com.allnone.app.Models.Listee;
import com.allnone.app.allnone.R;

import java.text.ParseException;


public class ToDo extends Activity {
    static String strToDoName;
    EditText enterListee;
    ListView myListView;
    ListerController myController = new ListerController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        myListView = (ListView) findViewById(R.id.ListOfToDoListees);
        enterListee = (EditText) findViewById(R.id.editText_listee);
        if (strToDoName != null)
            enterListee.setText(strToDoName);
        Button buttonListee = (Button)findViewById(R.id.button_add);

        buttonListee.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                  insertListFunctionality myFunction = new insertListFunctionality();
                    myFunction.execute();

            }
       });


        ListeeFunctionality lister = new ListeeFunctionality();
        lister.execute();
    }


    public void fn_createSuccessDialog(String successDialog) {
        Context context = this;
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

    @Override
    public void onPause() {
        super.onPause();
        strToDoName = enterListee.getText().toString();
    }

    private class ListeeFunctionality extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String a = "list_listee_incomplete";
            try {
                myController.fn_ListerPOSTRestCall(a, "today");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return "success";
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            Context local = getApplicationContext();
            ArrayAdapter<Listee> myAdapter = new ArrayAdapter<Listee>(local, android.R.layout.simple_list_item_2, android.R.id.text1, myController.getLister().getListees()) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                    text1.setText(myController.getLister().getListees().get(position).strLister_Title);
                    text2.setText(myController.getLister().getListees().get(position).strLister_FromDate);
                    return view;
                }
            };
            View headerView = getLayoutInflater().inflate(R.layout.headerforlist, null);
            TextView text = (TextView) headerView.findViewById(R.id.headerView);
            text.setText("ToDo List");
            myListView.addHeaderView(headerView);
            myListView.setAdapter(myAdapter);
        }
    }
    private class insertListFunctionality extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... params) {
            try {
                myController.fn_ListerPOSTRestCall("insert_listee",enterListee.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return "success";
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            fn_createSuccessDialog("Successfully added a Listee.");
        }
    }
}
