package com.tutorialsface.httpsrequest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity{

	TextView textResponse;
	Button btnGetRequest, btnPostRequest;
	RestClient restClient;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		getViews();
		setListeners();
		restClient = RestClient.getInstance();
	}

	private void getViews() {
		btnGetRequest = (Button) findViewById(R.id.btnGetRequest);
		btnPostRequest = (Button) findViewById(R.id.btnPostRequest);
		textResponse = (TextView) findViewById(R.id.textResponse);
	}

	private void setListeners() {
		btnGetRequest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						String response = restClient.getRequest();
						setText("HTTPS Get Response:-\n" + response);
					}
				}).start();
			}
		});
		btnPostRequest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						String response = restClient.postRequest();
						setText("HTTPS Post Response:-\n" + response);
					}
				}).start();
			}
		});
	}
	
	private void setText(final String response) {
		MainActivity.this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				textResponse.setText(response);
			}
		});
	}
}
