package com.allnone.app.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.allnone.app.allnone.R;


public class ToDo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_to_do);

        final EditText enterListee = (EditText)findViewById(R.id.editText_listee);

        Button buttonListee = (Button)findViewById(R.id.button_add);

        buttonListee.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String listee = enterListee.getText().toString();

           }
       });
    }
}
