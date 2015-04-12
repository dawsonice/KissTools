/**
 *
 * Copyright (c) 2014 CoderKiss
 *
 * CoderKiss[AT]gmail.com
 *
 */

package com.kisstools.utils;

import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

public class ViewUtil {
	public static final String TAG = "ViewUtil";

	public final static int measureTextWidth(TextView view, String text) {
		if (TextUtils.isEmpty(text)) {
			return 0;
		}

		TextPaint paint = view.getPaint();
		int width = (int) paint.measureText(text);
		return width;
	}

	public static boolean eventInView(MotionEvent event, View view) {
		if (event == null || view == null) {
			return false;
		}

		int eventX = (int) event.getRawX();
		int eventY = (int) event.getRawY();

		int[] location = new int[2];
		view.getLocationOnScreen(location);

		int width = view.getWidth();
		int height = view.getHeight();
		int left = location[0];
		int top = location[1];
		int right = left + width;
		int bottom = top + height;

		Rect rect = new Rect(left, top, right, bottom);
		boolean contains = rect.contains(eventX, eventY);
		return contains;
	}

	public static Point getViewCenter(View view) {
		if (view == null) {
			return new Point();
		}

		int[] location = new int[2];
		view.getLocationOnScreen(location);
		int x = location[0] + view.getWidth() / 2;
		int y = location[1] + view.getHeight() / 2;
		return new Point(x, y);
	}

	@SuppressLint("NewApi")
	public static void setAlpha(View view, float alpha) {
		if (Build.VERSION.SDK_INT < 11) {
			final AlphaAnimation animation = new AlphaAnimation(alpha, alpha);
			animation.setDuration(0);
			animation.setFillAfter(true);
			view.startAnimation(animation);
		} else {
			view.setAlpha(alpha);
		}
	}

	public static void removeFromParent(View view) {
		if (view == null) {
			return;
		}

		ViewParent parent = view.getParent();
		if (parent instanceof ViewGroup) {
			((ViewGroup) parent).removeView(view);
		}
	}

	public static Bitmap capture(View view) {
		if (view == null) {
			return null;
		}
		boolean oldEnabled = view.isDrawingCacheEnabled();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		if (!oldEnabled) {
			view.setDrawingCacheEnabled(false);
		}
		return bitmap;
	}

	public static void toggleSoftInput(TextView tv, boolean enable) {
		Method setShowSoftInputOnFocus = null;
		try {
			Method[] methods = tv.getClass().getMethods();
			for (Method m : methods) {
				String name = m.getName();
				if (name.equals("setSoftInputShownOnFocus")) {
					setShowSoftInputOnFocus = tv.getClass().getMethod(
							"setSoftInputShownOnFocus", boolean.class);
					break;
				} else if (name.equals("setShowSoftInputOnFocus")) {
					setShowSoftInputOnFocus = tv.getClass().getMethod(
							"setShowSoftInputOnFocus", boolean.class);
					break;
				}
			}
			if (null != setShowSoftInputOnFocus) {
				setShowSoftInputOnFocus.setAccessible(true);
				setShowSoftInputOnFocus.invoke(tv, enable);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
