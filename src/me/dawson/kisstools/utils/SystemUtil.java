/**
 *
 * Copyright (c) 2014 CoderKiss
 *
 * CoderKiss[AT]gmail.com
 *
 */

package me.dawson.kisstools.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import me.dawson.kisstools.KissTools;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.MotionEvent;

public class SystemUtil {
	public static final String TAG = "SystemUtil";
	public static final int MAX_BRIGHTNESS = 255;
	public static final int MIN_BRIGHTNESS = 0;

	public static int getStatusBarHeight(Context context) {
		int height = 0;
		if (context == null) {
			return height;
		}
		Resources resources = context.getResources();
		int resId = resources.getIdentifier("status_bar_height", "dimen",
				"android");
		if (resId > 0) {
			height = resources.getDimensionPixelSize(resId);
		}
		return height;
	}

	public static final String getVersion() {
		String version = Build.VERSION.RELEASE;
		Field[] fields = Build.VERSION_CODES.class.getFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			int fieldValue = -1;
			try {
				fieldValue = field.getInt(new Object());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (fieldValue == Build.VERSION.SDK_INT) {
				version = version + " : " + fieldName + " : " + fieldValue;
			}
		}
		return version;
	}

	public static boolean installedApp(String packageName) {
		Context context = KissTools.getApplicationContext();
		PackageInfo packageInfo = null;
		if (TextUtils.isEmpty(packageName)) {
			return false;
		}
		final PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
		if (packageInfos == null) {
			return false;
		}
		for (int index = 0; index < packageInfos.size(); index++) {
			packageInfo = packageInfos.get(index);
			final String name = packageInfo.packageName;
			if (packageName.equals(name)) {
				return true;
			}
		}
		return false;
	}

	public static void uninstallApp(String packageName) {
		Context context = KissTools.getApplicationContext();
		boolean installed = installedApp(packageName);
		if (!installed) {
			ToastUtil.showToast("package_not_installed");
			return;
		}

		boolean isRooted = isRooted();
		if (isRooted) {
			runRootCmd("pm uninstall " + packageName);
		} else {
			Uri uri = UrlUtil.parse("package:" + packageName);
			Intent intent = new Intent(Intent.ACTION_DELETE, uri);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
	}

	public static int getBrightness() {
		Context context = KissTools.getApplicationContext();
		int screenBrightness = 255;
		try {
			screenBrightness = Settings.System.getInt(
					context.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return screenBrightness;
	}

	public static void setBrightness(int brightness) {
		Context context = KissTools.getApplicationContext();
		try {
			if (brightness < MIN_BRIGHTNESS) {
				brightness = MIN_BRIGHTNESS;
			}
			if (brightness > MAX_BRIGHTNESS) {
				brightness = MAX_BRIGHTNESS;
			}
			ContentResolver resolver = context.getContentResolver();
			Uri uri = Settings.System
					.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
			Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS,
					brightness);
			resolver.notifyChange(uri, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getBrightnessMode() {
		Context context = KissTools.getApplicationContext();
		int brightnessMode = Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL;
		try {
			brightnessMode = Settings.System.getInt(
					context.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS_MODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return brightnessMode;
	}

	// 1 auto, 0 manual
	public static void setBrightnessMode(int brightnessMode) {
		Context context = KissTools.getApplicationContext();
		try {
			Settings.System.putInt(context.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS_MODE, brightnessMode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setWifiEnabled(boolean enable) {
		Context context = KissTools.getApplicationContext();
		try {
			WifiManager wifiManager = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			wifiManager.setWifiEnabled(enable);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isWifiEnabled() {
		Context context = KissTools.getApplicationContext();
		boolean enabled = false;
		try {
			WifiManager wifiManager = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			enabled = wifiManager.isWifiEnabled();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return enabled;
	}

	public static boolean isRooted() {
		String binaryName = "su";
		boolean rooted = false;
		String[] places = { "/sbin/", "/system/bin/", "/system/xbin/",
				"/data/local/xbin/", "/data/local/bin/", "/system/sd/xbin/",
				"/system/bin/failsafe/", "/data/local/" };
		for (String where : places) {
			if (new File(where + binaryName).exists()) {
				rooted = true;
				break;
			}
		}
		return rooted;
	}

	public static boolean isSimulator() {
		return (Build.FINGERPRINT != null && Build.FINGERPRINT
				.contains("generic"))
				|| "google_sdk".equals(Build.PRODUCT)
				|| "google_sdk".equals(Build.MODEL)
				|| "goldfish".equals(Build.HARDWARE);
	}

	public static boolean hasPermission(String permission) {
		Context context = KissTools.getApplicationContext();
		int result = context.checkCallingOrSelfPermission(permission);
		return (result == PackageManager.PERMISSION_GRANTED);
	}

	public static void lockScreen() {
		Context context = KissTools.getApplicationContext();
		DevicePolicyManager deviceManager = (DevicePolicyManager) context
				.getSystemService(Context.DEVICE_POLICY_SERVICE);
		deviceManager.lockNow();
	}

	// <uses-permission android:name="android.permission.INJECT_EVENTS" />
	public final static void inputKeyEvent(int keyCode) {
		try {
			runRootCmd("input keyevent " + keyCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String runCmd(String cmd) {
		if (TextUtils.isEmpty(cmd)) {
			return null;
		}
		Process process = null;
		String result = null;

		String[] commands = { "/system/bin/sh", "-c", cmd };

		try {
			process = Runtime.getRuntime().exec(commands);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int read = -1;
			InputStream errIs = process.getErrorStream();
			while ((read = errIs.read()) != -1) {
				baos.write(read);
			}
			baos.write('\n');

			InputStream inIs = process.getInputStream();
			while ((read = inIs.read()) != -1) {
				baos.write(read);
			}

			byte[] data = baos.toByteArray();
			result = new String(data);

			LogUtil.d(TAG, "runCmd result " + result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String runRootCmd(String cmd) {
		if (TextUtils.isEmpty(cmd)) {
			return null;
		}

		Process process = null;
		String result = null;

		try {
			String[] commands = { "su", "-c", cmd };
			process = Runtime.getRuntime().exec(commands);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int read = -1;
			InputStream errIs = process.getErrorStream();
			while ((read = errIs.read()) != -1) {
				baos.write(read);
			}
			baos.write('\n');

			InputStream inIs = process.getInputStream();
			while ((read = inIs.read()) != -1) {
				baos.write(read);
			}

			byte[] data = baos.toByteArray();
			result = new String(data);

			LogUtil.d(TAG, "runRootCmd result " + result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static int getDistance(MotionEvent e1, MotionEvent e2) {
		float x = e1.getX() - e2.getX();
		float y = e1.getY() - e2.getY();
		return (int) Math.sqrt(x * x + y * y);
	}

	public static long getMaxMemory() {
		Runtime runtime = Runtime.getRuntime();
		long maxMemory = runtime.maxMemory();
		LogUtil.d(TAG, "application max memory " + maxMemory);
		return maxMemory;
	}

	public static void restartApplication(Class<?> clazz) {
		Context context = KissTools.getApplicationContext();
		Intent intent = new Intent(context, clazz);
		int pendingIntentId = 198964;
		PendingIntent pendingIntent = PendingIntent.getActivity(context,
				pendingIntentId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		am.set(AlarmManager.RTC, System.currentTimeMillis() + 500,
				pendingIntent);
		System.exit(0);
	}

	public static void killApplication(String packageName) {
		try {
			Context context = KissTools.getApplicationContext();
			ActivityManager activityManager = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityManager.killBackgroundProcesses(packageName);
			Method forceStopPackage = activityManager.getClass()
					.getDeclaredMethod("forceStopPackage", String.class);
			forceStopPackage.setAccessible(true);
			forceStopPackage.invoke(activityManager, packageName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final boolean isMainThread() {
		return Looper.getMainLooper() == Looper.myLooper();
	}

	public static void runOnMain(Runnable runnable) {
		if (runnable == null) {
			return;
		}

		if (isMainThread()) {
			runnable.run();
		} else {
			Handler handler = new Handler(Looper.getMainLooper());
			handler.post(runnable);
		}
	}

	public static void runOnMain(Runnable runnable, long delay) {
		if (runnable == null) {
			return;
		}
		Handler handler = new Handler(Looper.getMainLooper());
		handler.postDelayed(runnable, delay);
	}
}
