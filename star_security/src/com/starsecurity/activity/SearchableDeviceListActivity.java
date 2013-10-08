package com.starsecurity.activity;

import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;

import com.starsecurity.R;
import com.starsecurity.component.listview.DeviceListView;
import com.starsecurity.component.listview.SynObject;
import com.starsecurity.model.DVRDevice;
import com.starsecurity.service.CloudService;
import com.starsecurity.service.FavouriteControlService;
import com.starsecurity.service.impl.CloudServiceImpl;
import com.starsecurity.service.impl.FavouriteControlServiceImpl;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * @function     功能	              设备列表弹框
 * @author       创建人                陈明珍
 * @date        创建日期           2013-10-05
 * @author       修改人                陈明珍
 * @date        修改日期           2013-10-06
 * @description 修改说明	 
 *   2013-10-05 创建此文件 陈明珍
 */
public class SearchableDeviceListActivity extends Activity{

	DeviceListView deviceListView;
	
	private String ddns_server;
	private String ddns_port;
	private String user_id;
	private String ddns_password;
	
	private final int PROGRESS_DIALOG = 0x1234;
	private final int DDNS_RESP_SUCC = 0x1100;		// 获取设备信息成功
	private final int DDNS_RESP_FAILURE = 0x1101;	// 获取设备信息失败
	private final int DDNS_SYS_FAILURE = 0x1103;	// 非DDNS返回错误
	
	private CloudService cloudService = new CloudServiceImpl("conn1");

	private List<DVRDevice> deviceInfoList;
	
	private SynObject synObj = new SynObject();
	
	/***
	 * 手机存放收藏夹URL
	 */
	private static final String filePath = "/data/data/com.starsecurity/MyFavourites.xml";
	/** 使用记录单元实例 */
	private FavouriteControlService favouriteControlService = new FavouriteControlServiceImpl("conn1");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.devicelist_new);
		
		deviceListView = (DeviceListView) findViewById(R.id.lview_device);
		
		// 获取域名服务器、端口、用户名及密码
		/*
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		ddns_server = pref.getString("ddns_server", "");
		ddns_port = pref.getString("ddns_port", "");
		user_id = pref.getString("ddbs_userid", "");
		ddns_password = pref.getString("password", "");*/
		ddns_server = favouriteControlService.getServerIP(filePath);
		ddns_port = favouriteControlService.getServerPort(filePath);
		user_id = favouriteControlService.getUserName(filePath);
		ddns_password = favouriteControlService.getPassword(filePath);
		
		if (!checkSetting()) {
			Toast.makeText(this, getString(R.string.IDS_PROMPT_NULLSETTING), Toast.LENGTH_LONG);
			this.finish();
		}
		
		requset4DeviceList();
		
		// 挂起等待请求结果
		synObj.suspend();		
	}
	
	
	/* 服务器返回处理 */
	private Handler responseHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			String errMsg;
			
			dismissDialog(PROGRESS_DIALOG);
			
			// 解除挂起， 程序往下执行
			synObj.resume();
			
			switch(msg.what) {
			case DDNS_RESP_SUCC:
				deviceListView.init(deviceInfoList);
				deviceListView.searchItem("");
				break;
			case DDNS_RESP_FAILURE:
				errMsg = msg.getData().getString("ERR_MSG");
				Toast.makeText(SearchableDeviceListActivity.this, errMsg, Toast.LENGTH_LONG).show();
				SearchableDeviceListActivity.this.finish();
				break;
			case DDNS_SYS_FAILURE:
				errMsg = getString(R.string.DEVICE_LIST_ErrorReason);
				Toast.makeText(SearchableDeviceListActivity.this, errMsg, Toast.LENGTH_LONG).show();
				SearchableDeviceListActivity.this.finish();
				break;
			default:
				break;
			}
		}
		
	};
	
	
	private boolean checkSetting() {
		if (ddns_server.equals("") && ddns_port.equals("") && user_id.equals("")
				&& ddns_password.equals("")) {
			return false;
		}
		
		return true;
	}
	
	private void requset4DeviceList() {
		showDialog(PROGRESS_DIALOG);
		(new RequestDeviceInfoThread(responseHandler)).start();
	}
	
	
	@Override 
	protected Dialog onCreateDialog(int id) { 
		switch(id) { 
		case PROGRESS_DIALOG: 
			return ProgressDialog.show(this, "",getString(R.string.ddns_progress_text), true); 
		default: return null; 
		} 
	} 
	
	class RequestDeviceInfoThread extends Thread {

		private Handler handler;
		
		public RequestDeviceInfoThread(Handler handler) {
			this.handler = handler;			
		}
		
		@Override
		public void run() {
			Message msg = new Message();
			try {
				Document doc = cloudService.SendURLPost(ddns_server, ddns_port, user_id, ddns_password, "");
				
				String requestResult = cloudService.readXmlStatus(doc);
				if (requestResult == null)	// 请求成功，返回null
				{
					deviceInfoList = cloudService.readXmlDVRDevices(doc);
					msg.what = DDNS_RESP_SUCC;
				} else {	// 请求失败，返回错误原因
					Bundle errMsg =  new Bundle();
					
					msg.what = DDNS_RESP_FAILURE;
					errMsg.putString("ERR_MSG", requestResult);
					msg.setData(errMsg);
				}
				
			} catch (DocumentException e) {
				msg.what = DDNS_SYS_FAILURE;
				e.printStackTrace();
			} catch (IOException e) {
				msg.what = DDNS_SYS_FAILURE;
				e.printStackTrace();
			} 
			
			handler.sendMessage(msg);
		}
		
	}

	
	
}
