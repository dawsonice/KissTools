/**
 *
 * Copyright (c) 2014 CoderKiss
 *
 * CoderKiss[AT]gmail.com
 *
 */

package com.kisstools.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.text.TextUtils;

@SuppressLint("SimpleDateFormat")
public class TimeUtil {
	public static final String TAG = "TimeUtil";

	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

	public static long format(String time, String format) {
		if (TextUtils.isEmpty(time)) {
			return 0;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		long modified = 0;
		try {
			Date date = sdf.parse(time);
			modified = date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return modified;
	}

	public static String format(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
		Date date = new Date(time);
		return sdf.format(date);
	}

	public static String format(long time, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date(time);
		return sdf.format(date);
	}

	public static String format(Date date, String format) {
		if (TextUtils.isEmpty(format) || date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String format(String timeStr, String srcFormat,
			String dstFormat) {
		long time = format(timeStr, srcFormat);
		String result = format(time, dstFormat);
		return result;
	}

	public static String utcToLocal(String utcTime) {
		String localTime = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(UTC_FORMAT);
			Date date = sdf.parse(utcTime);
			sdf.applyPattern(DEFAULT_FORMAT);
			localTime = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return localTime;
	}
}
