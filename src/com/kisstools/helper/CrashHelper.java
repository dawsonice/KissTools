/**
 * @author dawson dong
 */

package com.kisstools.helper;

import java.lang.Thread.UncaughtExceptionHandler;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Application;
import android.os.Build;
import android.os.Looper;

import com.kisstools.utils.LogUtil;
import com.kisstools.utils.StringUtil;
import com.kisstools.utils.ToastUtil;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class CrashHelper implements UncaughtExceptionHandler {

	public static final String TAG = "CrashHelper";

	private boolean enableToast;

	@SuppressLint("NewApi")
	public static final void init(Application application) {
		if (Thread.getDefaultUncaughtExceptionHandler() instanceof CrashHelper) {
			return;
		}

		CrashHelper helper = new CrashHelper();
		Thread.setDefaultUncaughtExceptionHandler(helper);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		final String name = thread.getName();

		if (enableToast) {
			String message = "exception in " + name;
			showToast(message);
		}

		String text = StringUtil.stringify(ex);
		LogUtil.e(TAG, "thread " + name + " exception");
		LogUtil.d(TAG, "exception detail\n" + text);

		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			//
		}

		// TODO save crash file to local
		System.exit(1);
	}

	private void showToast(final String message) {
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				ToastUtil.show(message);
				Looper.loop();
			}
		}.start();
	}
}
