/**
 *
 * Copyright (c) 2014 CoderKiss
 *
 * CoderKiss[AT]gmail.com
 *
 */

package me.dawson.kisstools.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import me.dawson.kisstools.KissTools;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.SensorManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

//hardware related functions
public class DeviceUtil {
	public static final String TAG = "DeviceUtil";

	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static String getBuildInfo() {
		StringBuilder sb = new StringBuilder();
		// alpha sort
		sb.append("board: " + Build.BOARD).append("\nbrand: " + Build.BRAND)
				.append("\ncpu_abi: " + Build.CPU_ABI)
				.append("\ncpu_abi2: " + Build.CPU_ABI2)
				.append("\ndevice: " + Build.DEVICE)
				.append("\ndisplay: " + Build.DISPLAY)
				.append("\nfingerprint: " + Build.FINGERPRINT)
				.append("\nhardware: " + Build.HARDWARE)
				.append("\nid: " + Build.ID)
				.append("\nmanufacture: " + Build.MANUFACTURER)
				.append("\nmodel: " + Build.MODEL)
				.append("\nproduct: " + Build.PRODUCT)
				.append("\nradio: " + Build.RADIO)
				.append("\nsdk_int: " + Build.VERSION.SDK_INT);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			sb.append("\nserial: " + Build.SERIAL);
		}
		sb.append("\ntype: " + Build.TYPE).append("\ntags: " + Build.TAGS);

		return sb.toString();
	}

	public static String getProductInfo() {
		String brand = android.os.Build.BRAND;
		String model = android.os.Build.MODEL;
		String manufacture = android.os.Build.MANUFACTURER;
		String finalInfo = brand + " " + model + "/" + manufacture;
		return finalInfo;
	}

	public static final Point getScreenSize() {
		Context context = KissTools.getApplicationContext();
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		// since SDK_INT = 1;
		int widthPixels = metrics.widthPixels;
		int heightPixels = metrics.heightPixels;
		// includes window decorations (status bar bar/menu bar)
		if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17) {
			try {
				widthPixels = (Integer) Display.class.getMethod("getRawWidth")
						.invoke(display);
				heightPixels = (Integer) Display.class
						.getMethod("getRawHeight").invoke(display);
			} catch (Exception ignored) {
			}
		}
		// includes window decorations (status bar bar/menu bar)
		else if (Build.VERSION.SDK_INT >= 17) {
			try {
				Point realSize = new Point();
				Display.class.getMethod("getRealSize", Point.class).invoke(
						display, realSize);
				widthPixels = realSize.x;
				heightPixels = realSize.y;
			} catch (Exception ignored) {
			}
		}
		return new Point(widthPixels, heightPixels);
	}

	public static final float getScreenDensity() {
		Context context = KissTools.getApplicationContext();
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		float screenDensity = dm.density;
		return screenDensity;
	}

	public static int getScreenDensityDpi() {
		Context context = KissTools.getApplicationContext();
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		int screenDensityDpi = dm.densityDpi;
		return screenDensityDpi;
	}

	public static final String getBluetoothMac() {
		BluetoothAdapter adapter = null;
		String bluetoothMac = null;
		try {
			adapter = BluetoothAdapter.getDefaultAdapter();
			bluetoothMac = adapter.getAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!TextUtils.isEmpty(bluetoothMac)) {
			bluetoothMac = bluetoothMac.toLowerCase();
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
			e.printStackTrace();
		}
		if (!TextUtils.isEmpty(wlanMac)) {
			wlanMac = wlanMac.toLowerCase();
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

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static final String getSerialId() {
		return Build.SERIAL;
	}

	public static final String getDeviceId() {
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

	public static double getScreenInches() {
		Context context = KissTools.getApplicationContext();
		double screenInches = -1;
		try {
			Resources resources = context.getResources();
			DisplayMetrics dm = resources.getDisplayMetrics();
			Point point = getScreenSize();
			double width = Math.pow(point.x / dm.xdpi, 2);
			double height = Math.pow(point.y / dm.ydpi, 2);
			screenInches = Math.sqrt(width + height);
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

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static final boolean hasFrontCamera() {
		int number = Camera.getNumberOfCameras();
		for (int index = 0; index < number; index++) {
			CameraInfo ci = new CameraInfo();
			Camera.getCameraInfo(index, ci);
			if (ci.facing == CameraInfo.CAMERA_FACING_FRONT) {
				return true;
			}
		}
		return false;
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static final boolean hasBackCamera() {
		int number = Camera.getNumberOfCameras();
		for (int index = 0; index < number; index++) {
			CameraInfo ci = new CameraInfo();
			Camera.getCameraInfo(index, ci);
			if (ci.facing == CameraInfo.CAMERA_FACING_BACK) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasSensor(int type) {
		Context context = KissTools.getApplicationContext();
		SensorManager manager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);
		return manager.getDefaultSensor(type) != null;
	}

	public static final long getTotalMemory() {
		String memInfoPath = "/proc/meminfo";
		String str2;
		long initial_memory = 0;
		try {
			FileReader fr = new FileReader(memInfoPath);
			BufferedReader bf = new BufferedReader(fr, 8192);
			str2 = bf.readLine();// total memory size
			String[] as = str2.split("\\s+");
			initial_memory = Integer.valueOf(as[1]).intValue() * 1024;
			bf.close();
			return initial_memory;
		} catch (IOException e) {
			return -1;
		}
	}
}
