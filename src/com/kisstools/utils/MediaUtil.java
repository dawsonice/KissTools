/**
 *
 * Copyright (c) 2014 CoderKiss
 *
 * CoderKiss[AT]gmail.com
 *
 */

package com.kisstools.utils;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.kisstools.KissTools;

public class MediaUtil {
	public static final String TAG = "MediaUtil";

	public static String getAppDir() {
		String appDir = null;
		Context context = KissTools.getApplicationContext();
		if (mediaMounted()) {
			File fileDir = context.getExternalFilesDir("");
			if (fileDir.exists()) {
				appDir = FileUtil.getParent(fileDir);
			}
		}

		if (appDir == null) {
			File fileDir = context.getFilesDir();
			if (fileDir.exists()) {
				appDir = FileUtil.getParent(fileDir);
			}
		}
		return appDir;
	}

	public static String getUserDir() {
		String appDir = getAppDir();
		if (TextUtils.isEmpty(appDir)) {
			return null;
		}

		String uid = UserUtil.getUserId();
		String userDir = appDir;
		if (TextUtils.isEmpty(uid)) {
			LogUtil.w(TAG, "user id not set!");
		} else {
			userDir = userDir + "/" + uid;
		}
		return userDir;
	}

	public static String getUserDir(String dirName) {
		String userDir = getUserDir();
		if (TextUtils.isEmpty(userDir)) {
			return null;
		} else {
			return userDir + "/" + dirName;
		}
	}

	public static boolean mediaMounted() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		} else {
			return false;
		}
	}
}
