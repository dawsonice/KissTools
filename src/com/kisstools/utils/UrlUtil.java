/**
 *
 * Copyright (c) 2014 CoderKiss
 *
 * CoderKiss[AT]gmail.com
 *
 */

package com.kisstools.utils;

import java.net.URLEncoder;

import android.net.Uri;
import android.text.TextUtils;

public class UrlUtil {
	public static final String TAG = "UrlUtil";

	public static String encode(String url) {
		if (TextUtils.isEmpty(url)) {
			return url;
		}

		String encodedURL = "";
		String[] temp = url.split("/");
		int length = temp.length;
		for (int index = 0; index < length; index++) {
			try {
				temp[index] = URLEncoder.encode(temp[index], "UTF-8");
				temp[index] = temp[index].replace("+", "%20");
			} catch (Exception e) {
				e.printStackTrace();
				return url;
			}
			encodedURL += temp[index];
			if (index < (length - 1)) {
				encodedURL += "/";
			}
		}
		return encodedURL;
	}

	public static Uri parse(String url) {
		if (TextUtils.isEmpty(url)) {
			return null;
		}

		Uri uri = null;
		try {
			uri = Uri.parse(url);
		} catch (Exception e) {

		}
		return uri;
	}

	public static String getParam(String url, String key) {
		Uri uri = parse(url);
		if (uri == null) {
			return null;
		}

		String value = null;
		try {
			value = uri.getQueryParameter(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
}
