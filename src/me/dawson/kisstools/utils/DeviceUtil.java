/**
 *
 * Copyright (c) 2014 CoderKiss
 *
 * CoderKiss[AT]gmail.com
 *
 */

package me.dawson.kisstools.utils;

import me.dawson.kisstools.KissTools;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;

//hardware related functions
public class DeviceUtil {
	public static final String TAG = "DeviceUtil";

	public static final String getBluetoothMac() {
		BluetoothAdapter adapter = null;
		String bluetoothMac = null;
		try {
			adapter = BluetoothAdapter.getDefaultAdapter();
			bluetoothMac = adapter.getAddress();
		} catch (Exception e) {
		}
		return bluetoothMac;
	}

	public static final String getWlanMac() {
		Context context = KissTools.getApplicationContext();
		String wlanMac = null;
		try {
			WifiManager wm = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			wlanMac = wm.getConnectionInfo().getMacAddress();
		} catch (Exception e) {

		}
		return wlanMac;
	}

	public static final String getAndroidId() {
		Context context = KissTools.getApplicationContext();
		String androidID = null;
		try {
			androidID = Secure.getString(context.getContentResolver(),
					Secure.ANDROID_ID);
		} catch (Exception e) {

		}
		return androidID;
	}

	public static final String getIMEI() {
		Context context = KissTools.getApplicationContext();
		String deviceIMEI = null;
		try {
			TelephonyManager teleManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			deviceIMEI = teleManager.getDeviceId();
		} catch (Exception e) {

		}
		return deviceIMEI;
	}

	public static float getScreenInches() {
		Context context = KissTools.getApplicationContext();
		float screenInches = -1;
		try {
			Resources resources = context.getResources();
			DisplayMetrics dm = resources.getDisplayMetrics();
			double width = Math.pow(dm.widthPixels / dm.xdpi, 2);
			double height = Math.pow(dm.heightPixels / dm.ydpi, 2);
			screenInches = (float) (Math.sqrt(width + height));
		} catch (Exception e) {
		}
		return screenInches;
	}

	public static int dp2px(int dip) {
		Context context = KissTools.getApplicationContext();
		Resources resources = context.getResources();
		int px = Math
				.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
						dip, resources.getDisplayMetrics()));
		return px;
	}

	public static int px2dp(int px) {
		Context context = KissTools.getApplicationContext();
		DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
		int dp = Math.round(px
				/ (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
		return dp;
	}

	public static int sp2px(float sp) {
		Context context = KissTools.getApplicationContext();
		final float scale = context.getResources().getDisplayMetrics().scaledDensity;
		int px = Math.round(sp * scale);
		return px;
	}
}
