package com.example.androidbase;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/**
 * 自定义table适配�?
 * <p>
 * 功能：标题栏、�?中行变色、接收List<Hash map>类型的数据来组织展示
 */

public class TabAdptList extends SimpleAdapter {
	
	protected int[] to;
	protected String[] title;
	protected View titleView;
	protected List<? extends Map<String, ?>> dataList = null;
	
	protected int layout_ListItem;

	protected int selectedPosition = -1;// 选中的位�?

	protected LayoutInflater mInflater;
	

	public TabAdptList(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to, String[] title) {
		super(context, data, resource, from, to);
		layout_ListItem = resource;
		dataList = data;

		this.mInflater = LayoutInflater.from(context);

		// 部分参数记录到本地变量中
		this.to = to;
		this.title = title;

		// 构建title的数�?
		titleView = LayoutInflater.from(context).inflate(resource, null);

		// 创建标题�?
		createTitleView();
	}

	public View getTitleView() {
		return titleView;
	}
	public void createTitleView() {
		int count = to.length;

		// 设置标题栏各个控件的颜色、文字内容�?字体大小
		for (int i = 0; i < count; i++) {
			View v = titleView.findViewById(to[i]);
			if (v != null) {
				if (v instanceof TextView) {
					((TextView) v).setText(title[i]);
					((TextView) v).setTextColor(Color.WHITE);
					((TextView) v).setTextSize(12);
					((TextView) v).setGravity(Gravity.CENTER);
					// v.setPadding(0, 7, 0, 6);
				}
			}
		}
		setTitleView(titleView);
	}

	public void setTitleView(View titleView) {
		this.titleView = titleView;
	}

	public int getSelectedPosition() {
		return selectedPosition;
	}

	public void setSelectedPosition(int selectedPosition) {
		this.selectedPosition = selectedPosition;
	}
	
}