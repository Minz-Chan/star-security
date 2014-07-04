package com.starsecurity.activity;

import java.util.ArrayList;
import java.util.List;

import com.starsecurity.R;
import com.starsecurity.component.listview.CollectDeviceListView;
import com.starsecurity.model.DVRDevice;
import com.starsecurity.model.FavouriteRecord;
import com.starsecurity.service.FavouriteControlService;
import com.starsecurity.service.impl.FavouriteControlServiceImpl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

//import android.widget.TextView;

/**
 * 
 * @author 赵康
 * @function 功能 完成用户收藏设备列表的查看
 * @data 创建时间 2014年7月3日
 * @description 用户收藏信息以后，会保存在地址为filePath的地方，当用户想查看自己的收藏信息时，可以再此处查看收藏情况
 * 
 */
public class CollectDeviceActivity extends Activity {

	private CollectDeviceListView cdlv = null;// 关联收藏Listview，即继承自SearchableListView的CollectDeviceListView
	private List<DVRDevice> deviceInfoList;// 保存设备的数据信息
	@SuppressLint("SdCardPath")
	private static final String filePath = "/data/data/com.starsecurity/MyFavourites.xml";// 保存收藏夹信息的地址URL
	private FavouriteControlService favouriteControlService = null;// 收藏夹控制服务

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collectlistview);
		cdlv = (CollectDeviceListView) findViewById(R.id.lview_collect);

		deviceInfoList = new ArrayList<DVRDevice>();// 用于保存DVRDevice的数据信息列表；
		deviceInfoList = getDeviceInfoList();// 获取收藏夹中的信息

		cdlv.init(deviceInfoList);// 为CollectDeviceListView初始化数据源
		cdlv.searchItem("");// 模糊搜索时，未设置确定值，显示全部信息
	}

	/**
	 * 用于获取收藏夹中的信息中的设备信息
	 * 
	 * @return 设备信息列表
	 */
	private List<DVRDevice> getDeviceInfoList() {
		favouriteControlService = new FavouriteControlServiceImpl("conn1");
		List<DVRDevice> list = new ArrayList<DVRDevice>();// 初始化保存设备列表的list

		List<String> favouriteRecordList = new ArrayList<String>();// 初始化保存设备名称的收藏夹列表
		favouriteRecordList = favouriteControlService
				.getFavouriteList(filePath);// 获取收藏夹信息

		if (favouriteRecordList.size() != 0) {// 解析收藏夹中的信息
			int size = favouriteRecordList.size();
			for (int i = 0; i < size; i++) {
				String favouriteName = favouriteRecordList.get(i);
				FavouriteRecord fr = favouriteControlService
						.getFavouriteRecordByName(filePath, favouriteName);// 获取设备记录

				if (fr != null) {
					String deviceName = fr.getFavouriteName();// 获取设备名称
					String password = fr.getPassword();// 获取用户密码
					String userName = fr.getUserName();// 获取用户名称
					String channel = fr.getDefaultChannel();// 获取默认通道
					String ip = fr.getIPAddress();// 获取IP地址
					String port = fr.getPort();// 获取用户设置的端口号

					// 只有在以上所有的字段中都不为空值，方才进行值的设置
					if ((deviceName != null) && (password != null)
							&& (userName != null) && (channel != null)
							&& (ip != null) && (port != null)) {
						DVRDevice dvrDevice = new DVRDevice();
						dvrDevice.setDeviceName("    " + deviceName);// 为设备名称添加4个空格，由于显示的时候需要去除前4个字符
						dvrDevice.setLoginUsername(userName);
						dvrDevice.setLoginPassword(password);
						dvrDevice.setLoginIP(ip);
						dvrDevice.setMobliePhonePort(port);
						dvrDevice.setStarChannel(channel);
						list.add(dvrDevice);
					}
				}
			}
		}
		return list;
	}
}