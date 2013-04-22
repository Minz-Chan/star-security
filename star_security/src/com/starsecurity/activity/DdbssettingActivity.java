package com.starsecurity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;

import com.starsecurity.R;

public class DdbssettingActivity extends PreferenceActivity {
	
	private EditTextPreference ddns_server;
	private EditTextPreference ddns_port;
	private EditTextPreference user_id;
	private EditTextPreference ddns_password;
	private EditTextPreference ddns_devicename;
	
	private String ddns_serverStr;
	private String ddns_portStr;
	private String user_idStr;
	private String ddns_passwordStr;
	private String ddns_devicenameStr;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.ddbssetting);
            
            ddns_server = (EditTextPreference) findPreference("ddns_server");
            ddns_port = (EditTextPreference) findPreference("ddns_port");
            user_id = (EditTextPreference) findPreference("ddbs_userid");
            ddns_password = (EditTextPreference) findPreference("password");
            ddns_devicename = (EditTextPreference) findPreference("device_name");
    }

	private int saveSettingData(){
		try{
			ddns_serverStr = ddns_server.getText().toString();
			ddns_portStr = ddns_port.getText().toString();
			user_idStr = user_id.getText().toString();
			ddns_passwordStr = ddns_password.getText().toString();
			ddns_devicenameStr = ddns_devicename.getText().toString();
			
			Bundle bundle = new Bundle();
			bundle.putString("ddns_serverStr", ddns_serverStr);
			bundle.putString("ddns_portStr", ddns_portStr);
			bundle.putString("user_idStr", user_idStr);
			bundle.putString("ddns_passwordStr", ddns_passwordStr);
			bundle.putString("ddns_devicenameStr", ddns_devicenameStr);
			Intent intent = getIntent();
			intent.putExtras(bundle);
			setResult(Activity.RESULT_CANCELED, intent);
			finish();
			return 1;
		}catch(Exception e){
			return 0;
		}
	}
	
	/**
     * 若是activity跳转或者被对话框覆盖则对修改的配置进行保存
     */
    @Override
    protected void onPause() 
    {
    	saveSettingData();
        super.onPause();
    }
     
    /**
     * 若是在当前activity中按了返回键,则对修改的配置进行保存
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN)
            {
            	saveSettingData();
            }
            return super.onKeyDown(keyCode, event);
    }
	
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.ddbssettingactivitymenu, menu);
		return true;
	}
}
