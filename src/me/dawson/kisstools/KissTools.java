package me.dawson.kisstools;

import java.lang.ref.WeakReference;
import java.security.InvalidParameterException;

import me.dawson.kisstools.utils.DeviceInfo;
import android.content.Context;

public class KissTools {
	public static final String TAG = "KissTools";
	private static WeakReference<Context> contextRef;

	public static void setContext(Context context) {
		if (context == null) {
			throw new InvalidParameterException("Invalid context parameter!");
		}

		Context appContext = context.getApplicationContext();
		contextRef = new WeakReference<Context>(appContext);

		DeviceInfo.init();
	}

	public static Context getApplicationContext() {
		Context context = contextRef.get();
		if (context == null) {
			throw new InvalidParameterException("Context parameter not set!");
		} else {
			return context;
		}
	}
}
