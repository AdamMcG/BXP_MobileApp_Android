package com.td.counter;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	int counter;
	Button add, sub;
	TextView countText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		counter = 0;
		countText = (TextView) findViewById(R.id.count_text);
		add = (Button) findViewById(R.id.add);
		sub = (Button) findViewById(R.id.sub);

		add.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				counter++;
				countText.setText("Your count is: " + counter);

			}
		});

		sub.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				counter--;
				countText.setText("Your count is: " + counter);
			}
		});
	}
}
