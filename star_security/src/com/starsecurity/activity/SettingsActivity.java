package com.starsecurity.activity;

import java.io.File;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.starsecurity.R;
import com.starsecurity.model.FavouriteRecord;
import com.starsecurity.util.XMLControlUtil;

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
@SuppressLint("SdCardPath")
public class SettingsActivity extends Activity {

	private Button save_deleButton;
	private EditText usernameEditText;
	private EditText passwordEditText;
	private EditText serverEditText;
	private EditText portEditText;
	private EditText recordNameEditText;
	
	/***
	 * 通道选择
	 */
	private Spinner channelSpinner;
	private String[] items = {"1", "2", "3", "4", "5","6","7","8","9","10","11","12","13","14","15","16"};
	/***
	 * 收藏夹选择
	 */
	private Spinner favouriteRecordSpinner;
	private String[] nullItems = {};
	/***
	 * 手机存放收藏夹URL
	 */
	private static final String filePath = "/data/data/com.starsecurity/MyFavourites.xml";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		
		
		save_deleButton = (Button) findViewById(R.id.save_dele);
		usernameEditText = (EditText) findViewById(R.id.set_username);
		passwordEditText = (EditText) findViewById(R.id.set_password);
		serverEditText = (EditText) findViewById(R.id.set_server);
		portEditText = (EditText) findViewById(R.id.set_port);
		recordNameEditText = (EditText) findViewById(R.id.record_name);
		//通道选择下拉框初始化
		channelSpinner = (Spinner) findViewById(R.id.channel_spinner);
		ArrayAdapter<String> channelItems = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,items); 
		channelItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		channelSpinner.setAdapter(channelItems);		
		//收藏记录选择下拉框初始化
		favouriteRecordSpinner = (Spinner) findViewById(R.id.set_spinner);
		File myFavouritesFile=new File(filePath);		//存放收藏夹的XMl文件
		//若首次使用，则创建XML存储文件
		if(!myFavouritesFile.exists()){
			try {
				XMLControlUtil.createFileAndRoot(filePath, "Favourites");	//创建文件
			} catch (Exception e) {
				System.out.println(e.getMessage().toString());
			}; 
		}else{
			List<String> favouriteRecordList = XMLControlUtil.getFavouriteList(filePath);
			if(favouriteRecordList.size()!=0){
				//控件初始化
				ArrayAdapter<String> favouriteRecordItems = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,favouriteRecordList);
				favouriteRecordItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				favouriteRecordSpinner.setAdapter(favouriteRecordItems);
				String selectedRecordName= (String) favouriteRecordSpinner.getSelectedItem();
				FavouriteRecord favouriteRecord = (FavouriteRecord) XMLControlUtil.getFavouriteRecordByName(filePath,selectedRecordName);
				usernameEditText.setText(favouriteRecord.getUserName());
				passwordEditText.setText(favouriteRecord.getPassword());
				serverEditText.setText(favouriteRecord.getIPAddress());
				portEditText.setText(favouriteRecord.getPort());
				recordNameEditText.setText(favouriteRecord.getFavouriteName());
				channelSpinner.setSelection(Integer.parseInt(favouriteRecord.getDefaultChannel())-1);
			}	
		}
		
		favouriteRecordSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String selectedRecordName = (String) favouriteRecordSpinner.getSelectedItem();
				FavouriteRecord favouriteRecord = XMLControlUtil.getFavouriteRecordByName(filePath, selectedRecordName);
				usernameEditText.setText(favouriteRecord.getUserName());
				passwordEditText.setText(favouriteRecord.getPassword());
				serverEditText.setText(favouriteRecord.getIPAddress());
				portEditText.setText(favouriteRecord.getPort());
				recordNameEditText.setText(favouriteRecord.getFavouriteName());
				channelSpinner.setSelection(Integer.parseInt(favouriteRecord.getDefaultChannel())-1);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		//save/delete按钮的响应
		save_deleButton.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				if(usernameEditText.getText().toString()!=null
						&&passwordEditText.getText().toString()!=null
						&&serverEditText.getText().toString()!=null
						&&portEditText.getText().toString()!=null
						&&recordNameEditText.getText().toString()!=null){
					if(!XMLControlUtil.isExist(filePath, recordNameEditText.getText().toString())){
						FavouriteRecord favouriteRecord = new FavouriteRecord();
						favouriteRecord.setFavouriteName(recordNameEditText.getText().toString());
						favouriteRecord.setUserName(usernameEditText.getText().toString());
						favouriteRecord.setPassword(passwordEditText.getText().toString());
						favouriteRecord.setIPAddress(serverEditText.getText().toString());
						favouriteRecord.setPort(portEditText.getText().toString());
						favouriteRecord.setDefaultChannel(channelSpinner.getSelectedItem().toString());
						favouriteRecord.setRecordName(recordNameEditText.getText().toString());
						if(XMLControlUtil.addFavouriteElement(filePath, favouriteRecord)){
							//更新界面				
							List<String> favouriteRecordList = XMLControlUtil.getFavouriteList(filePath);
							ArrayAdapter<String> favouriteRecordItemsTemp = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,favouriteRecordList);
							favouriteRecordItemsTemp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
							favouriteRecordSpinner.setAdapter(favouriteRecordItemsTemp);
							favouriteRecordSpinner.setSelection(favouriteRecordList.size()-1);
							String selectedRecordName = (String) favouriteRecordSpinner.getSelectedItem();
							FavouriteRecord favouriteRecordTemp = (FavouriteRecord) XMLControlUtil.getFavouriteRecordByName(filePath,selectedRecordName);
							usernameEditText.setText(favouriteRecordTemp.getUserName());
							passwordEditText.setText(favouriteRecordTemp.getPassword());
							serverEditText.setText(favouriteRecordTemp.getIPAddress());
							portEditText.setText(favouriteRecordTemp.getPort());
							recordNameEditText.setText(favouriteRecordTemp.getFavouriteName());
							channelSpinner.setSelection(Integer.parseInt(favouriteRecordTemp.getDefaultChannel())-1);
							Toast.makeText(getApplicationContext(),getString(R.string.IDS_RecordSaveSuc), Toast.LENGTH_LONG).show();
						}
					}else{
						//若存在同名记录则是用户选择覆盖或删除
						new  AlertDialog.Builder(SettingsActivity.this).setMessage(getString(R.string.IDS_DupRecord)).
						setPositiveButton(getString(R.string.IDS_OVERRIDE),new DialogInterface.OnClickListener() {
		            		@Override
		            		public void onClick(DialogInterface dialog, int which) {
		            			FavouriteRecord favouriteRecord = new FavouriteRecord();
								favouriteRecord.setFavouriteName(recordNameEditText.getText().toString());
								favouriteRecord.setUserName(usernameEditText.getText().toString());
								favouriteRecord.setPassword(passwordEditText.getText().toString());
								favouriteRecord.setIPAddress(serverEditText.getText().toString());
								favouriteRecord.setPort(portEditText.getText().toString());
								favouriteRecord.setDefaultChannel(channelSpinner.getSelectedItem().toString());
								favouriteRecord.setRecordName(recordNameEditText.getText().toString());
		            			if(XMLControlUtil.coverFavouriteElement(filePath, favouriteRecord))
		            				Toast.makeText(getApplicationContext(),getString(R.string.IDS_RecordSaveSuc), Toast.LENGTH_LONG).show();
		            		}
		            	}).setNegativeButton(getString(R.string.IDS_DELETE),new DialogInterface.OnClickListener() {
		            		@Override
		            		public void onClick(DialogInterface dialog, int which) {
		            			if(XMLControlUtil.removeFavouriteElement(filePath, favouriteRecordSpinner.getSelectedItem().toString())){
		            				//更新界面				
									List<String> favouriteRecordList = XMLControlUtil.getFavouriteList(filePath);
									if(favouriteRecordList.size()!=0){
										ArrayAdapter<String> favouriteRecordItemsTemp = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,favouriteRecordList);
										favouriteRecordItemsTemp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
										favouriteRecordSpinner.setAdapter(favouriteRecordItemsTemp);
										favouriteRecordSpinner.setSelection(0);
										String selectedRecordName = (String) favouriteRecordSpinner.getItemAtPosition(0);
										FavouriteRecord favouriteRecordTemp = (FavouriteRecord) XMLControlUtil.getFavouriteRecordByName(filePath,selectedRecordName);
										usernameEditText.setText(favouriteRecordTemp.getUserName());
										passwordEditText.setText(favouriteRecordTemp.getPassword());
										serverEditText.setText(favouriteRecordTemp.getIPAddress());
										portEditText.setText(favouriteRecordTemp.getPort());
										recordNameEditText.setText(favouriteRecordTemp.getFavouriteName());
										channelSpinner.setSelection(Integer.parseInt(favouriteRecordTemp.getDefaultChannel())-1);	
			            				Toast.makeText(getApplicationContext(),getString(R.string.IDS_DelSuc), Toast.LENGTH_LONG).show();
									}
									else{
										ArrayAdapter<String> favouriteRecordNullItems = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,favouriteRecordList);
										favouriteRecordNullItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
										favouriteRecordSpinner.setAdapter(favouriteRecordNullItems);
										usernameEditText.setText(null);
										passwordEditText.setText(null);
										serverEditText.setText(null);
										portEditText.setText(null);
										recordNameEditText.setText(null);
										channelSpinner.setSelection(0);	
									}
		            			}
		            		}
		            	}).show(); 
					}
				}
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
				this.finish();
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
				String channel = (String) channelSpinner.getSelectedItem();
				bundle.putString("usernameStr", username);
				bundle.putString("passwordStr", password);
				bundle.putString("serverStr", server);
				bundle.putString("portStr", port);
				bundle.putString("channelStr", channel);
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
