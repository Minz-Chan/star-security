package com.starsecurity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.starsecurity.R;

public class SettingsActivity extends Activity {

	private Button save_deleButton;
	private EditText usernameEditText;
	private EditText passwordEditText;
	private EditText serverEditText;
	private EditText portEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		save_deleButton = (Button) findViewById(R.id.save_dele);
		usernameEditText = (EditText) findViewById(R.id.set_username);
		passwordEditText = (EditText) findViewById(R.id.set_password);
		serverEditText = (EditText) findViewById(R.id.set_server);
		portEditText = (EditText) findViewById(R.id.set_port);
		
		//点击保存按钮时，将设置的参数传回原界面
		save_deleButton.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {		
				Bundle bundle = new Bundle();
				String username = usernameEditText.getText().toString();
				String password = passwordEditText.getText().toString();
				String server = serverEditText.getText().toString();
				String port = portEditText.getText().toString();
				bundle.putString("usernameStr", username);
				bundle.putString("passwordStr", password);
				bundle.putString("serverStr", server);
				bundle.putString("portStr", port);
				Intent intent = getIntent();
				intent.putExtras(bundle);
				setResult(Activity.RESULT_OK, intent);
				finish();
			}	
		});
	}
	
}
