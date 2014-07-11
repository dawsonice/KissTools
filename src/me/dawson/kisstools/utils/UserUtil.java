/**
 *
 * Copyright (c) 2014 CoderKiss
 *
 * CoderKiss[AT]gmail.com
 *
 */

package me.dawson.kisstools.utils;

public class UserUtil {
	public static final String TAG = "UserUtil";

	private static String userId;

	public static final String getUserId() {
		return userId;
	}

	public static final void setUserId(String id) {
		userId = id;
	}
}
