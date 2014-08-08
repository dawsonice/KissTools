/**
 *
 * Copyright (c) 2014 CoderKiss
 *
 * CoderKiss[AT]gmail.com
 *
 */

package me.dawson.kisstools.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

public class StringUtil {
	public static final String TAG = "StringUtil";

	public static final String regEmail = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	public static final String regCnChar = "[\u4E00-\u9FFF]+";
	public static final String regIpAddress = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	public static final String cutText(String text, int maxLength) {
		if (isEmpty(text) || maxLength < 0) {
			return text;
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

	public static String stringify(Throwable t) {
		if (t == null) {
			return null;
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return sw.toString();
	}

	public static boolean isEmpty(String text) {
		if (text == null || text.length() == 0) {
			return true;
		}
		return false;
	}

	private static Pattern urlPattern = Pattern
			.compile("((?:(http|https|Http|Https):\\/\\/"
					+ "(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|"
					+ "(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_\\.\\"
					+ "+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})"
					+ "?\\@)?)?((?:(?:[a-zA-Z0-9][a-zA-Z0-9\\-\\_]{0,64}\\.)"
					+ "+(?:(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])"
					+ "|(?:biz|b[abdefghijmnorstvwyz])|(?:cat|com|"
					+ "coop|c[acdfghiklmnoruvxyz])|d[ejkmoz]|(?:edu|e[cegrstu])|f[ijkmor]"
					+ "|(?:gov|g[abdefghilmnpqrstuwy])|h[kmnrtu]|(?:info|int|i[delmnoqrst])"
					+ "|(?:jobs|j[emop])|k[eghimnrwyz]|l[abcikrstuvy]|"
					+ "(?:mil|mobi|museum|m[acdeghklmnopqrstuvwxyz])|"
					+ "(?:name|net|n[acefgilopruz])|(?:org|om)|(?:pro|p[aefghklmnrstwy])|"
					+ "qa|r[eouw]|s[abcdeghijklmnortuvyz]|(?:tel|travel|t[cdfghjklmnoprtvwz])"
					+ "|u[agkmsyz]|v[aceginu]|w[fs]|y[etu]|z[amw]))|(?:(?:25[0-5]|2[0-4][0-9]"
					+ "|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
					+ "|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]"
					+ "|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9])))"
					+ "(?:\\:\\d{1,5})?)(\\/(?:(?:[a-zA-Z0-9\\;\\/\\?\\:\\@\\&\\=\\#\\~%\\-\\."
					+ "\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?");

	public static List<String> extractUrls(String text) {
		List<String> urls = new ArrayList<String>();

		Matcher matcher = urlPattern.matcher(text);
		while (matcher.find()) {
			urls.add(matcher.group());
		}

		return urls;
	}
}
