package com.starsecurity.component.listview;

import java.util.ArrayList;
import java.util.List;

import com.starsecurity.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * @function     功能	  自定义可搜索的ListView控件
 *     此类实例本身不可被实例化，其子类须实现ISearchableListView接口方
 * 可达到自定义可搜索ListView效果。
 * @author       创建人                陈明珍
 * @date        创建日期           2013-10-05
 * @author       修改人                陈明珍
 * @date        修改日期           2013-10-06
 * @description 修改说明	 
 *   2013-10-05 错误提示完善 陈明珍
 */
public abstract class SearchableListView extends LinearLayout implements ISearchableListView {
	
	private EditText editText;
	private ListView listView;
	
	private List contentList = new ArrayList<Object>();
	
	
	public SearchableListView(Context context) {
		super(context);
	}
	
	public SearchableListView(Context context, AttributeSet attrs) {
		super(context, attrs);  

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
        inflater.inflate(R.layout.searchablelist, this);   
        editText = (EditText) findViewById(R.id.searchText);
        listView = (ListView) findViewById(R.id.listView);
        
        editText.addTextChangedListener(this);
        listView.setOnItemClickListener(this);
	}
	
	/**
	 * 默认情况下contentList为List<String>，此时contentList中的元素(String)即为搜索字段
	 * 若contentList中元素不为String类型时，则应覆写此方法选定对象某个属性为搜索字段
	 * @param searchString
	 */
	public void searchItem(String searchString) {
		List list = contentList;
		List<String> resultList = new ArrayList<String>();
		
		for (int i = 0; i < list.size(); i++) {
			String itemText = (String) list.get(i);
			
			if (itemText != null && itemText.indexOf(searchString) != -1) {
				resultList.add(itemText);
			}
		}
		
		updateListView(new ArrayAdapter<String>(this.getContext(), 
                android.R.layout.simple_list_item_1,  resultList ));	
	}

	/**
	 * 获取ListView供搜索的原始数据列表
	 * @return 对象列表
	 */
	public List getContentList() {
		return contentList;
	}
	
	/**
	 * 为ListView适配数据
	 * @param adapter 适配器
	 */
	public void updateListView(ListAdapter adapter) {
		listView.setAdapter(adapter);
	}
}





