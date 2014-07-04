package com.starsecurity.component.listview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.starsecurity.component.ConnectionManager;
import com.starsecurity.model.DVRDevice;
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
 * 
 * @author 赵康
 * @function 功能 实现收集框的自定义，显示收藏列表
 * @data 2014年7月3日
 * @description 收藏的列表定义，该类继承抽象类SearchableListView,需要实现其中的方法
 * 
 */
public class CollectDeviceListView extends SearchableListView {
	private Context context = null;

	public CollectDeviceListView(Context context) {
		super(context);
		this.context = context;
	}

	public CollectDeviceListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	// 初始化数据源，用于填充listView
	@SuppressWarnings("unchecked")
	public void init(List<DVRDevice> list) {
		List<DVRDevice> contentList = super.getContentList();// 从父类中获取内容列表
		for (DVRDevice obj : list) {
			contentList.add(obj);
		}
	}

	// 有关文本框的操作
	@Override
	public void afterTextChanged(Editable s) {
		searchItem(s.toString());
	}

	// 有关文本框的操作
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	// 有关文本框的操作
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

	@SuppressWarnings("rawtypes")
	@Override
	public void searchItem(String searchString) {// 重写搜索方法
		List list = super.getContentList();
		List<DVRDevice> resultList = new ArrayList<DVRDevice>();// 保存结果数据
		// 从List列表中获取设备名称数据
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

	// 有关listview的单击操作
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// 首先获取必须的信息：端口号，密码，用户名等等；根据获取的信息，加载视频
		ConnectionManager.getConnection("conn1").close();
		DVRDevice device = (DVRDevice) parent.getAdapter().getItem(position);

		System.out.println("ip:" + device.getLoginIP() + "password:" + device.getLoginPassword()
				+ "port:" + device.getMobliePhonePort());
		// 返回主界面播放视频
		Activity collectDeviceActivity = (Activity) this.context;
		Intent intent = collectDeviceActivity.getIntent();
		intent.putExtra("DVRDevice", device);
		collectDeviceActivity.setResult(Activity.RESULT_FIRST_USER, intent);
		collectDeviceActivity.finish();
	}

	private class ListViewAdapter extends BaseAdapter {
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
}