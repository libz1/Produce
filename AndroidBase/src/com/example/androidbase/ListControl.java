package com.example.androidbase;

import java.util.List;
import java.util.Map;




import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ListControl extends LinearLayout {

	private ListView list = null;

	// 标题栏是否已经存�?
	private Boolean titleExist = false;

	// 标题栏layout
	private LinearLayout titleLayout = null;

	// 列表的当前行
	private int currentLine = -1;

	public int getCurrentLine() {
		return currentLine;
	}

	TabAdptList listAdapter;

	public ListControl(Context context, AttributeSet attrs) {
		super(context, attrs);

		// 添加字段标题�?
		titleLayout = new LinearLayout(context);
		LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 0);
		titleLayout.setLayoutParams(lp1);
		// 设置标题栏的属�?
		titleLayout.setBackgroundColor(Color.parseColor("#177cb0"));
		titleLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		titleLayout.setOrientation(LinearLayout.HORIZONTAL);
		this.addView(titleLayout);

		// 添加列表
		list = new ListView(context);
		lp1 = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT, 0);
		list.setLayoutParams(lp1);
		// 设置列表的属�?
		list.setBackgroundColor(Color.parseColor("#FFFFFF"));
		list.setCacheColorHint(Color.parseColor("#00000000"));
		// list.setSelector(Color.parseColor("#000000"));
		// android:height="300dip"
		// android:listSelector="#000000" />

		this.addView(list);


		// 在外部手动加载数�?
		// loadData();

		// 如果外部没有定义事件，则这里的定义的事件触发函数将会执行，实现当前行变色，当前对象数据更�?
		// 列表的点击事�?
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				rowChanged(position);
			}
		});

	}

	// 可以接收外部的事件定�?
	public void setOnItemClickListener(OnItemClickListener l) {
		list.setOnItemClickListener(l);
	}

	// 更新明细控件展示内容 、更新列表展示内�?
	public void rowChanged(int position) {
		currentLine = position;
		listAdapter.notifyDataSetInvalidated();

		if (position >= 0) {
			// 更新当前对象数据
			// String str = (String) data.get(position).get("meterId");
			// currentMeter = meterList.getOneMeter(str);
			// 当前行变�?
			listAdapter.setSelectedPosition(position);
			// 自动滚动到指定行
			list.setSelection(position);
		} else {
			// currentMeter = null;

		}

	}

	public void loadData(List<Map<String, Object>> d, TabAdptList a) {

		listAdapter = a;
//		listAdapter = new HZListTabAdpt(c, d, R.layout.list_hz_item, new String[] {
//				"num", "specs" }, new int[] { R.id.num, R.id.specs },
//				new String[] { "数量", "规格" });

		// 设置当前行信�?
		if (d.size() > 0)
			currentLine = 0;
		else
			currentLine = -1;


//		// 初始化界面中的标题栏控件指针，并加载标题栏数�?
//		if (!titleExist) {
//			titleLayout.addView(listAdapter.getTitleView());
//			titleExist = true;
//		}
		titleLayout.removeAllViews();
		titleLayout.addView(listAdapter.getTitleView());
//		titleExist = true;

		list.setAdapter(listAdapter);

		// 手动触发行变化事�?
		list.performItemClick(null, currentLine, 0);

	}

}
