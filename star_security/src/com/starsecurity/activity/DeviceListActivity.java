package com.starsecurity.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.starsecurity.R;
import com.starsecurity.model.DVRDevice;
import com.starsecurity.model.FavouriteRecord;
import com.starsecurity.service.CloudService;
import com.starsecurity.service.impl.CloudServiceImpl;
import com.starsecurity.util.XMLControlUtil;

/***
 * 
 * @function     功能	  	云台列表显示界面
 * @author       创建人              肖远东
 * @date        创建日期           2013-04-22
 * @author       修改人              肖远东
 * @date        修改日期           2013-04-22
 * @description 修改说明	          首次增加
 *
 */
@SuppressLint("SdCardPath")
public class DeviceListActivity extends Activity {
	/***
	 * 
	 */
	private ListView deviceListView;
	/***
	 *	
	 */
	private static List<DVRDevice> deviceList;
	/***
	 * 
	 */
	private static List<String> deviceNameList;
	
	private static String errorReason;
	
	/***
	 * 手机存放收藏夹URL
	 */
	private static final String filePath = "/data/data/com.starsecurity/MyFavourites.xml";
	
	private static final int PROGRESS_DIALOG = 1; 
	
	private static final int STATE_SUCCESS = 1;
	private static final int STATE_FAIL = 0;
	
	private CloudService cloudService = new CloudServiceImpl("conn1");
	/***
	 * 处理子线程消息，并更新界面
	 */
	private final Handler handler = new Handler(Looper.getMainLooper()) {
		public void handleMessage(Message msg) { // 处理Message，更新ListView
			int state = msg.getData().getInt("state"); 
			switch(state){ 
			case STATE_SUCCESS:
				dismissDialog(PROGRESS_DIALOG);
				deviceListView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), 
                        android.R.layout.simple_list_item_1, 
                        deviceNameList ));
				/*为设备列表添加事件监听*/
				deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						//存放所选平台到收藏夹中
						//存放收藏夹的XMl文件
						File myFavouritesFile=new File(filePath);		
						//若首次使用，则创建XML存储文件
				        if(!myFavouritesFile.exists()){
				        	try {
				        		XMLControlUtil.createFileAndRoot(filePath, "Favourites");	//创建文件
							} catch (Exception e) {
								System.out.println(e.getMessage().toString());
							}; 
				        }else{
				        	FavouriteRecord favouriteRecord = new FavouriteRecord();
				        	DVRDevice dvrDevice = deviceList.get(position);
				        	favouriteRecord.setFavouriteName(dvrDevice.getDeviceName());
							favouriteRecord.setUserName(dvrDevice.getLoginUsername());
							favouriteRecord.setPassword(dvrDevice.getLoginPassword());
							favouriteRecord.setIPAddress(dvrDevice.getLoginIP());
							favouriteRecord.setPort(dvrDevice.getMobliePhonePort());
							favouriteRecord.setDefaultChannel(dvrDevice.getStarChannel());
							favouriteRecord.setRecordName(dvrDevice.getDeviceName());
				        	//检测此平台是否已经存储，若有则覆盖，若没有则添加
				        	if(XMLControlUtil.isExist(filePath, dvrDevice.getDeviceName())){
				        		XMLControlUtil.coverFavouriteElement(filePath, favouriteRecord);
				        	}else{	
								XMLControlUtil.addFavouriteElement(filePath, favouriteRecord);
				        	}
				        }
						
						//返回主界面播放视频
						Intent intent = getIntent();
						intent.putExtra("DVRDevice",deviceList.get(position));
						setResult(Activity.RESULT_FIRST_USER, intent);
						DeviceListActivity.this.finish();
					}
				});
				break; 
			case STATE_FAIL: 
				dismissDialog(PROGRESS_DIALOG);
				Toast.makeText(getApplicationContext(),errorReason, Toast.LENGTH_LONG).show();
				DeviceListActivity.this.finish();
				break;
			default:
				break;
			} 
		} 
	}; 
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.devicelist);
		deviceListView = (ListView) findViewById(R.id.deviceListView);
		errorReason = getString(R.string.DEVICE_LIST_ErrorReason);
		
		showDialog(PROGRESS_DIALOG); 
        new ProgressDeviceListThread(handler).start();
	}

	/***
	 * 创建加载对话框
	 */
	@Override 
	protected Dialog onCreateDialog(int id) { 
		switch(id) { 
		case PROGRESS_DIALOG: 
			return ProgressDialog.show(this, "",getString(R.string.ddns_progress_text), true); 
		default: return null; 
		} 
	} 
	
	/***
	 * 子线程，用于后台处理数据
	 * @author 肖远东
	 *
	 */
	private class ProgressDeviceListThread extends Thread { 
		  
        private Handler handler;
  
        public ProgressDeviceListThread(Handler handler) { 
            this.handler = handler; 
        } 
        //后台数据处理
        @Override 
        public void run() {
        	try{
        		Intent intent = getIntent();
        		Bundle bundle = intent.getExtras();
				String ddns_serverStr = bundle.getString("ddns_serverStr");
				String ddns_portStr = bundle.getString("ddns_portStr");
				String user_idStr = bundle.getString("user_idStr");
				String ddns_passwordStr = bundle.getString("ddns_passwordStr");
				String ddns_devicenameStr = bundle.getString("ddns_devicenameStr");
				Document doc = cloudService.SendURLPost(ddns_serverStr,ddns_portStr,user_idStr,ddns_passwordStr,ddns_devicenameStr);
				if(cloudService.readXmlStatus(doc)==null)
				{
					deviceList = cloudService.readXmlDVRDevices(doc);
					if(deviceList.size()!=0){
	        			Iterator<DVRDevice> deviceListIter = deviceList.iterator();
	        			deviceNameList = new ArrayList();
	        			while(deviceListIter.hasNext()){
	        				DVRDevice dvrDeviceTemp = deviceListIter.next();
	        				deviceNameList.add(dvrDeviceTemp.getDeviceName());
	        			}
	        			Message msg = handler.obtainMessage(); 
	                    Bundle messageBundle = new Bundle(); 
	                    messageBundle.putInt("state", STATE_SUCCESS); 
	                    msg.setData(messageBundle); 
	                    handler.sendMessage(msg);
	        		}
				}
				else{
					errorReason = cloudService.readXmlStatus(doc);
	        		Message msg = handler.obtainMessage(); 
	                Bundle messageBundle = new Bundle(); 
	                messageBundle.putInt("state", STATE_FAIL); 
	                msg.setData(messageBundle); 
	                handler.sendMessage(msg);
                }
        	}catch(Exception e){
        		Message msg = handler.obtainMessage(); 
                Bundle bundle = new Bundle(); 
                bundle.putInt("state", STATE_FAIL); 
                msg.setData(bundle); 
                handler.sendMessage(msg);
        	}
            
        } 
          
    } 
}
