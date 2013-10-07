package com.starsecurity.component.listview;

import android.text.TextWatcher;
import android.widget.AdapterView;

/**
 * @function     功能	 可搜索ListView需实现的接口
 *     集成TextWatcher、AdapterView.OnItemClickListener二
 * 者作为可搜索ListView必须实现的接口。
 * 可达到自定义可搜索ListView效果。
 * @author       创建人                陈明珍
 * @date        创建日期           2013-10-05
 * @author       修改人                陈明珍
 * @date        修改日期           2013-10-05
 * @description 修改说明	 
 *   2013-10-05 创建  陈明珍
 */
public interface ISearchableListView extends TextWatcher, AdapterView.OnItemClickListener {

}
