package com.starsecurity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.starsecurity.R;

/***
 * 
 * 设置界面控制
 * @author  	创建人                 肖远东
 * @date        创建日期           2013-03-18
 * @author      修改人                 肖远东
 * @date        修改日期           2013-03-18
 * @description 修改说明	             首次增加
 *
 */
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
		
		//save/delete按钮的响应
		save_deleButton.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {		
				
			}	
		});
	}
	
	/**
     * 若是在当前activity中按了返回键,则并返回主界面
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN)
            {
            	Intent intentCancel = getIntent();
				setResult(Activity.RESULT_OK, intentCancel);
				finish();
            }
            return super.onKeyDown(keyCode, event);
    }
	
    /***
     * 载入Menu
     */
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.settingsactivitymenu, menu);
		return true;
	}
    
    /***
     * 为Menu设置响应，点击设置完成设置，取消返回主界面
     */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int item_id = item.getItemId();
		switch(item_id){
			case R.id.setting_settingMenu:
				//完成设置并跳转至主界面
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
				break;
			case R.id.setting_cancelMenu:
				//返回主界面
				Intent intentCancel = getIntent();
				setResult(Activity.RESULT_OK, intentCancel);
				finish();
				break;
		}
		return true;
	}
}
