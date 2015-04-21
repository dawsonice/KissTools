/**
 * @author dawson dong
 */

package com.kisstools.helper;

import android.util.SparseArray;
import android.view.View;

public class ViewHolder {

	private SparseArray<View> views;
	private View convertView;

	public static ViewHolder get(View view) {
		Object tag = view.getTag();
		if (tag instanceof ViewHolder) {
			return (ViewHolder) tag;
		} else {
			return new ViewHolder(view);
		}
	}

	private ViewHolder(View view) {
		this.views = new SparseArray<View>();
		this.convertView = view;
		view.setTag(this);
	}

	@SuppressWarnings("unchecked")
	public <T extends View> T findViewById(int id) {
		View view = views.get(id);
		if (view == null) {
			view = convertView.findViewById(id);
			views.put(id, view);
		}
		
		return (T) view;
	}
}
