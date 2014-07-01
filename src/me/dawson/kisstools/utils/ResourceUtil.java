package me.dawson.kisstools.utils;

import me.dawson.kisstools.KissTools;
import android.content.Context;
import android.graphics.drawable.Drawable;

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
