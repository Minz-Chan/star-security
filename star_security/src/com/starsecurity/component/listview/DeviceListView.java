package com.starsecurity.component.listview;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.starsecurity.component.ConnectionManager;
import com.starsecurity.model.DVRDevice;
import com.starsecurity.model.FavouriteRecord;
import com.starsecurity.service.FavouriteControlService;
import com.starsecurity.service.impl.FavouriteControlServiceImpl;
import com.starsecurity.util.PinyinComparator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @function     功能	  自定义可搜索的设备搜索控件
 *     此类主要通过实作ISearchableListView接口实现设备列表搜索、设
 * 备选择等功能。ListViewAdapter可供定制ListView中Item的显示效果
 * 及形式。
 * 可达到自定义可搜索ListView效果。
 * @author       创建人                陈明珍
 * @date        创建日期           2013-10-05
 * @author       修改人                陈明珍
 * @date        修改日期           2013-10-07
 * @description 修改说明	 
 *   2013-10-07 onItemClick接口实作完善 陈明珍
 */
public class DeviceListView extends SearchableListView {

	private Context context;
	
	public DeviceListView(Context context) {
		super(context);
		this.context = context;
	}

	public DeviceListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	
	public void init(List list) {
		List contentList = super.getContentList();
		
		for (Object obj: list) {
			contentList.add(obj);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void searchItem(String searchString) {
		List list = super.getContentList();
		List<DVRDevice> resultList = new ArrayList<DVRDevice>();
		
		for (int i = 0; i < list.size(); i++) {
			String deviceName = ((DVRDevice) list.get(i)).getDeviceName();
			
			if (deviceName != null && deviceName.indexOf(searchString) != -1) {
				resultList.add((DVRDevice) list.get(i));
			}
		}
		
		if (!resultList.isEmpty()) {
			Collections.sort(resultList, new PinyinComparator());
		}
		
		
		super.updateListView(new ListViewAdapter(this.getContext(), resultList));	
	}
	

	class ListViewAdapter extends BaseAdapter {

		private Context context;
		private List<DVRDevice> list;
		
		public ListViewAdapter(Context context, List<DVRDevice> list) {
			super();
			
			this.context = context;
			this.list = list;						
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView mTextView = new TextView(context);  
			
			mTextView.setText(list.get(position).getDeviceName());
			mTextView.setTextColor(Color.WHITE);  
			mTextView.setTextSize(20);
			mTextView.setHeight(30);
			
			return mTextView;
		}
		
	}

	@Override
	public void afterTextChanged(Editable s) {
		searchItem(s.toString());		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		ConnectionManager.getConnection("conn1").close();
		
		DVRDevice device = (DVRDevice) parent.getAdapter().getItem(position);
		
		// 存放所选平台到收藏夹中
		// 存放收藏夹的XMl文件
		FavouriteControlService favouriteControlService = new FavouriteControlServiceImpl("conn1");
		String filePath = "/data/data/com.starsecurity/MyFavourites.xml";
		File myFavouritesFile=new File(filePath);	
		
		// 若首次使用，则创建XML存储文件
        if(!myFavouritesFile.exists()){
        	try {
        		favouriteControlService.createFileAndRoot(filePath, "Favourites");	//创建文件
			} catch (Exception e) {
				System.out.println(e.getMessage().toString());
			}; 
        }
        FavouriteRecord favouriteRecord = new FavouriteRecord();
        
		String tempDeviceName = device.getDeviceName();
		String deviceName = tempDeviceName.substring(4,tempDeviceName.length());
		
        favouriteRecord.setFavouriteName(deviceName);
		favouriteRecord.setUserName(device.getLoginUsername());
		favouriteRecord.setPassword(device.getLoginPassword());
		favouriteRecord.setIPAddress(device.getLoginIP());
		favouriteRecord.setPort(device.getMobliePhonePort());
		favouriteRecord.setDefaultChannel(device.getStarChannel());
		favouriteRecord.setRecordName(device.getDeviceName());
		
        // 检测此平台是否已经存储，若有则覆盖，若没有则添加
        if(favouriteControlService.isExist(filePath, deviceName)){
        	favouriteControlService.coverFavouriteElement(filePath, favouriteRecord);
        }else{	
        	favouriteControlService.addFavouriteElement(filePath, favouriteRecord);
        }
        favouriteControlService.setLastRecord(filePath, favouriteRecord.getFavouriteName());
        favouriteControlService.setLastChannel(filePath, favouriteRecord.getDefaultChannel());

        
		// 返回主界面播放视频
        Activity searchableDeviceListActivity = (Activity) this.context;
        
		Intent intent = searchableDeviceListActivity.getIntent();
		intent.putExtra("DVRDevice", device);
		searchableDeviceListActivity.setResult(Activity.RESULT_FIRST_USER, intent);
		searchableDeviceListActivity.finish();
	}

}
