package me.dawson.kisstools.utils;

import me.dawson.kisstools.KissTools;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class DeviceInfo {
	public static final String TAG = "DeviceInfo";
	public static String MODEL;
	public static String DEVICE;
	public static String BRAND;
	public static String PRODUCT;
	public static String DISPLAY;
	public static String MANUFACTURER;

	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;
	public static float SCREEN_DENSITY;

	public static void init() {
		MODEL = android.os.Build.MODEL;
		DEVICE = android.os.Build.DEVICE;
		BRAND = android.os.Build.BRAND;
		PRODUCT = android.os.Build.PRODUCT;
		DISPLAY = android.os.Build.DISPLAY;
		MANUFACTURER = android.os.Build.MANUFACTURER;

		Resources resources = KissTools.getApplicationContext().getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		SCREEN_WIDTH = dm.widthPixels;
		SCREEN_HEIGHT = dm.heightPixels;
		SCREEN_DENSITY = dm.density;
	}
}
