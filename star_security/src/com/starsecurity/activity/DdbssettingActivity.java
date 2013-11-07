package com.starsecurity.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.starsecurity.R;
import com.starsecurity.model.DVRDevice;
import com.starsecurity.service.FavouriteControlService;
import com.starsecurity.service.impl.FavouriteControlServiceImpl;

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
public class DdbssettingActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	
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
	
	//读取已设置参数
	private FavouriteControlService favouriteControlService = new FavouriteControlServiceImpl("conn1");
	/***
	 * 手机存放收藏夹URL
	 */
	private static final String filePath = "/data/data/com.starsecurity/MyFavourites.xml";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.ddbssetting);
            
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            prefs.registerOnSharedPreferenceChangeListener(this); // 注册预览监听事件
            ddns_server = (EditTextPreference) findPreference("ddns_server");
            ddns_port = (EditTextPreference) findPreference("ddns_port");
            user_id = (EditTextPreference) findPreference("ddbs_userid");
            ddns_password = (EditTextPreference) findPreference("password");
            ddns_devicename = (EditTextPreference) findPreference("device_name");
            
            ddns_server.setText(favouriteControlService.getServerIP(filePath));
            ddns_port.setText(favouriteControlService.getServerPort(filePath));
            user_id.setText(favouriteControlService.getUserName(filePath));
            ddns_password.setText(favouriteControlService.getPassword(filePath));
            
            //预览已设置参数
            if(ddns_server.getText()!=null)
            	ddns_server.setSummary(ddns_server.getText().toString());
            if(ddns_port.getText()!=null)
            	ddns_port.setSummary(ddns_port.getText().toString());
            if(user_id.getText()!=null)
            	user_id.setSummary(user_id.getText().toString());
            if(ddns_password.getText()!=null){
            	int passwordLength = ddns_password.getText().toString().length();
            	StringBuffer passwordStr = new StringBuffer();
            	for(int index = 0;index<passwordLength;index++)
            		passwordStr.append("*");
            	ddns_password.setSummary(passwordStr.toString());
            }
            if(ddns_devicename.getText()!=null)
            	ddns_devicename.setSummary(ddns_devicename.getText().toString());
    }
	
	//监听设置参数的改变，即使更新预览
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key) {  
        Preference pref = findPreference(key);  
        if (pref instanceof EditTextPreference) {
        	if(key.equals("password")){
        		if(ddns_password!=null){
        			favouriteControlService.setPassword(filePath, ddns_password.getText().toString());
        			if(ddns_password.getText()!=null){
	        			int passwordLength = ddns_password.getText().length();
	                	StringBuffer passwordStr = new StringBuffer();
	                	for(int index = 0;index<passwordLength;index++)
	                		passwordStr.append("*");
	                	ddns_password.setSummary(passwordStr.toString());
        			}			
        		}
        	}
        	else if(key.equals("ddns_server")){
        		if(ddns_server.getText()!=null){
	        		favouriteControlService.setServerIP(filePath, ddns_server.getText().toString());
	        		EditTextPreference etp = (EditTextPreference) pref;  
		            pref.setSummary(etp.getText());
	            }
        	}
        	else if(key.equals("ddns_port")){
        		if(ddns_port.getText()!=null){
	        		favouriteControlService.setServerPort(filePath, ddns_port.getText().toString());
	        		EditTextPreference etp = (EditTextPreference) pref;  
		            pref.setSummary(etp.getText());
	            }
        	}
        	else if(key.equals("ddbs_userid")){
        		if(user_id.getText()!=null){
	        		favouriteControlService.setUserName(filePath, user_id.getText().toString());
	        		EditTextPreference etp = (EditTextPreference) pref;  
		            pref.setSummary(etp.getText());
        		}
        	}
        	else{
        		EditTextPreference etp = (EditTextPreference) pref;  
	            pref.setSummary(etp.getText());
        	}
        }  
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
			if(ddns_server.getText()!=null){
				ddns_serverStr = ddns_server.getText().toString();
			}
			if(ddns_port.getText()!=null){
				ddns_portStr = ddns_port.getText().toString();
			}
			if(user_id.getText()!=null){
				user_idStr = user_id.getText().toString();
			}
			if(ddns_password.getText()!=null){
				ddns_passwordStr = ddns_password.getText().toString();
			}
			if(ddns_devicename.getText()!=null){
				ddns_devicenameStr = ddns_devicename.getText().toString();
			}
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
     * 若是在当前activity中按了返回键,则返回前一个界面
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
