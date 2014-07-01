package me.dawson.kisstools.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

public class StringUtil {
	public static final String TAG = "StringUtil";

	public static final String regEmail = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	public static final String regCnChar = "[\u4E00-\u9FFF]+";
	public static final String regUserName = "^[a-zA-Z0-9_-]{2,30}$";
	public static final String regPassword = "^[]{6,25}$";
	public static final String regIpAddress = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	public static final String cutText(String text, int maxLength) {
		if (TextUtils.isEmpty(text) || maxLength < 0) {
			return null;
		}
		int length = text.length();
		if (length > maxLength) {
			return text.substring(0, maxLength);
		}
		return text;
	}

	public static boolean isEmail(String text) {
		if (TextUtils.isEmpty(text)) {
			return false;
		}

		Pattern pattern = Pattern.compile(regEmail);
		Matcher matcher = pattern.matcher(text);
		return matcher.find();
	}

	public static boolean isIp4Address(String text) {
		if (TextUtils.isEmpty(text)) {
			return false;
		}

		Pattern pattern = Pattern.compile(regIpAddress);
		Matcher macher = pattern.matcher(text);
		return macher.matches();
	}

	public static boolean isUserName(String text) {
		if (text == null) {
			return false;
		}

		Pattern p = Pattern.compile(regUserName);
		Matcher m = p.matcher(text);
		return m.find();
	}

	public static boolean isPassword(String text) {
		if (TextUtils.isEmpty(text)) {
			return false;
		}

		int length = text.length();
		if (length < 6) {
			return false;
		}

		return true;
	}
}
