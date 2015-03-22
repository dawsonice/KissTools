/**
 *
 * Copyright (c) 2014 CoderKiss
 *
 * CoderKiss[AT]gmail.com
 *
 */

package com.kisstools.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.kisstools.KissTools;

public class ResourceUtil {

	public static final String getString(int resId) {
		Context context = KissTools.getApplicationContext();
		if (context == null || resId <= 0) {
			return null;
		}
		try {
			return context.getString(resId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final String getString(int resId, Object... args) {
		Context context = KissTools.getApplicationContext();
		if (context == null || resId <= 0) {
			return null;
		}
		try {
			return context.getString(resId, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final Drawable getDrawable(int resId) {
		Context context = KissTools.getApplicationContext();
		if (context == null || resId <= 0) {
			return null;
		}
		try {
			return context.getResources().getDrawable(resId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
