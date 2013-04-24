package com.starsecurity.activity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.starsecurity.R;
import com.starsecurity.model.DVRDevice;
import com.starsecurity.util.MD5Util;

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
	
	private static String errorReason = "连接失败，请检查域名和端口设置";
	
	private static final int PROGRESS_DIALOG = 1; 
	
	private static final int STATE_SUCCESS = 1;
	private static final int STATE_FAIL = 0;
	
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
				
        		if(SendURLPost(ddns_serverStr,ddns_portStr,user_idStr,ddns_passwordStr,ddns_devicenameStr)==STATE_SUCCESS&&deviceList.size()!=0){
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
        		}else{
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
	
	/***
	 * XML解析
	 * @param inputStr
	 * @throws DocumentException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public static void readXml(InputStream inputStr) throws DocumentException{ 
		SAXReader sr = new SAXReader();//读取类 
		Document doc = sr.read(inputStr);  //这里可以使 字符串 或者 流转换成字符串，文件之类的//从里面读内容,返回的是一个Element /取得根节点 
		//获取状态码及相应状态信息，用于检测是否连接成功
		List resCodeList = null;
		resCodeList = doc.selectNodes("//Devices/resCode");
		Iterator resCodeIter = resCodeList.iterator();
		Element resCodeElement = (Element)resCodeIter.next();
		
		//获取状态码相对应信息，用于检测是否连接成功
		List descList = null;
		descList = doc.selectNodes("//Devices/desc");
		Iterator descIter = descList.iterator();
		Element descElement = (Element)descIter.next();
		
		if(resCodeElement.getText().equals("0")){
			//获取所有的设备节点元素
			List deviceListTemp = null;
			deviceListTemp = doc.selectNodes("//Devices/Device");
			Iterator deviceIter = deviceListTemp.iterator();
			deviceList = new ArrayList();
			while(deviceIter.hasNext()){
				DVRDevice dvrDevice = new DVRDevice();
				//获得具体的元素   
				Element element = (Element)deviceIter.next();
				//<du>登陆设备用户名</du>
				Element duElement = element.element("du");
				dvrDevice.setLoginUsername(duElement.getText().toString());
				//<dp>登陆设备密码</dp>
				Element dpElement = element.element("dp");
				dvrDevice.setLoginPassword(dpElement.getText().toString());
				//<dm>登陆设备模式(0.IP  1.域名)</dm>
				Element dmElement = element.element("dm");
				dvrDevice.setLoginMode(dmElement.getText().toString());
				//<dip>设备IP[登陆设备模式 为IP的时候有效]</dip>
				Element dipElement = element.element("dip");
				dvrDevice.setLoginIP(dipElement.getText().toString());
				//<dd>设备域名[登陆设备模式 为域名的时候有效]</dd>
				Element ddElement = element.element("dd");
				dvrDevice.setLoginDomain(ddElement.getText().toString());
				//<dpn>登陆设备端口号</dpn>
				Element dpnElement = element.element("dpn");
				dvrDevice.setLoginPort(dpnElement.getText().toString());
				//<scn>开始通道号</scn>
				Element scnElement = element.element("scn");
				dvrDevice.setStarChannel(scnElement.getText().toString());
				//<cn>通道个数</cn>
				Element cnElement = element.element("cn");
				dvrDevice.setChannelNumber(cnElement.getText().toString());
				//<ain>报警输入个数</ain>
				Element ainElement = element.element("ain");
				dvrDevice.setWarningInputNumber(ainElement.getText().toString());
				//<aon>报警输出个数</aon>
				Element aonElement = element.element("aon");
				dvrDevice.setWarningOutputNumber(aonElement.getText().toString());
				//<acn>音频通道个数</acn>
				Element acnElement = element.element("acn");
				dvrDevice.setAudioChannelNumber(acnElement.getText().toString());
				//<vn>语音对讲个数</vn>
				Element vnElement = element.element("vn");
				dvrDevice.setIntercomNumber(vnElement.getText().toString());
				//<dn>硬盘个数</dn>
				Element dnElement = element.element("dn");
				dvrDevice.setHDNumber(dnElement.getText().toString());
				//<mdn>最大支持的移动侦测区域个数</mdn>
				Element mdnElement = element.element("mdn");
				dvrDevice.setMaxMobileDetectionNumber(mdnElement.getText().toString());
				//<can>最大支持的视频遮盖区域个数</can>
				Element canElement = element.element("can");
				dvrDevice.setMaxOverlayAreaNumber(canElement.getText().toString());
				//<tp>产品型号</tp>
				Element tpElement = element.element("tp");
				dvrDevice.setProductModel(tpElement.getText().toString());
				//<ma>厂家类型: 星网锐捷[枚举类型]</ma>
				Element maElement = element.element("ma");
				dvrDevice.setManufacturer(maElement.getText().toString());
				//<sn>设备序列号</sn>
				Element snElement = element.element("sn");
				dvrDevice.setSerialNumber(snElement.getText().toString());
				//<dmc>设备网卡地址</dmc>
				Element dmcElement = element.element("dmc");
				dvrDevice.setEthernetaddress(dmcElement.getText().toString());
				//<dv>设备版本号</dv>
				Element dvElement = element.element("dv");
				dvrDevice.setVersionNumber(dvElement.getText().toString());
				//<wu>平台用户名</wu>
				Element wuElement = element.element("wu");
				dvrDevice.setPlatformUsername(wuElement.getText().toString());
				//<wp>密码（密文）</wp>
				Element wpElement = element.element("wp");
				dvrDevice.setPlatformPassword(wpElement.getText().toString());
				//<dwp>设备WEB端口</dwp>
				Element dwpElement = element.element("dwp");
				dvrDevice.setWEBPort(dwpElement.getText().toString());
				//<dna>(在线、离线)设备名称</dna>
				Element dnaElement = element.element("dna");
				dvrDevice.setDeviceName(dnaElement.getText().toString());
				//<mp>手机端口号</mp>
				Element mpElement = element.element("mp");
				dvrDevice.setMobliePhonePort(mpElement.getText().toString());
				//<up>是否通过upnp上网(0.否，1.是)</up>
				Element upElement = element.element("up");
				dvrDevice.setIsUPNP(upElement.getText().toString());
				deviceList.add(dvrDevice);
			}
		}else{
			errorReason = descElement.getText().toString();
		}
	} 
	
	/***
	 * 与云台建立连接
	 * @param domain
	 * @param port
	 * @param username
	 * @param password
	 * @param deviceName
	 * @return
	 * @throws IOException
	 */
	public static int SendURLPost(String domain,String port,String username,String password,String deviceName) throws IOException {
		try {
			String urlStr;
			URL url;
			HttpURLConnection httpURLConnection;

			urlStr = "http://"+domain+":"+port+"/xml_device-list";   
			url = new URL(urlStr);
			httpURLConnection = (HttpURLConnection) url.openConnection(); //获取连接
			httpURLConnection.setRequestMethod("POST"); //设置请求方法为POST, 也可以为GET
			httpURLConnection.setDoOutput(true);   
			String encoded = MD5Util.md5Encode(password);      
			StringBuffer param = new StringBuffer("wu="+username+"&wp="+encoded+"&pn=");  //请求URL的查询参数     
			OutputStream os = httpURLConnection.getOutputStream();   
			os.write(param.toString().getBytes());   
			os.flush();   
			os.close();
			InputStream is = httpURLConnection.getInputStream();
			
			readXml(is); 
			is.close();
			return STATE_SUCCESS;
		} catch (DocumentException e) {
			return STATE_FAIL;
		}
	} 
}
