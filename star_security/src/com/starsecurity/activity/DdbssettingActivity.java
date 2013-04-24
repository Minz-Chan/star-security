package com.starsecurity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.starsecurity.R;
import com.starsecurity.model.DVRDevice;

/***
 * 
 * @function     功能	  	云台设置界面
 * @author       创建人              肖远东
 * @date        创建日期           2013-03-18
 * @author       修改人              肖远东
 * @date        修改日期           2013-04-18
 * @description 修改说明	          首次增加
 *
 */
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
	
	/***
	 * Menu事件处理	
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int item_id = item.getItemId();
		switch(item_id){
		case R.id.ddns_requestMenu:
			Intent intent = new Intent();
			intent.setClass(DdbssettingActivity.this, DeviceListActivity.class);
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
			intent.putExtras(bundle);
			startActivityForResult(intent,0);
			break;
		case R.id.ddns_backMenu:
			DdbssettingActivity.this.finish();
			break;
		}
		return true;
	}

	/***
	 * 当新开启的设置界面结束跳转回来以后，处理设置界面的参数
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(resultCode){
			case Activity.RESULT_FIRST_USER:
				if(data!=null){
					DVRDevice dvrDevice = (DVRDevice) data.getSerializableExtra("DVRDevice");
					if(dvrDevice!=null){
						dvrDevice = (DVRDevice) data.getSerializableExtra("DVRDevice");
						Intent intent = getIntent();
						intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
						intent.putExtra("DVRDevice",dvrDevice);
						setResult(Activity.RESULT_FIRST_USER, intent);
						DdbssettingActivity.this.finish();
					}
				}
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	/**
     * 若是activity跳转或者被对话框覆盖则对修改的配置进行保存
     */
    @Override
    protected void onPause() 
    {
        super.onPause();
    }
     
    /**
     * 若是在当前activity中按了返回键,则对修改的配置进行保存
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN)
            {
            	DdbssettingActivity.this.finish();
            }
            return super.onKeyDown(keyCode, event);
    }
	
    /***
     * 加载Menu按钮
     */
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.ddbssettingactivitymenu, menu);
		return true;
	}
}
