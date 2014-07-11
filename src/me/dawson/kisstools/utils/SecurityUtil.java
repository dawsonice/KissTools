/**
 *
 * Copyright (c) 2014 CoderKiss
 *
 * CoderKiss[AT]gmail.com
 *
 */

package me.dawson.kisstools.utils;

import java.security.MessageDigest;
import java.util.Locale;

import android.text.TextUtils;

public class SecurityUtil {
	public static final String TAG = "SecurityUtil";

	public static String getMD5(String text) {
		String md5 = null;
		if (TextUtils.isEmpty(text)) {
			return md5;
		}
		MessageDigest md5Digest = null;
		try {
			md5Digest = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return md5;
		}
		char[] charArray = text.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int index = 0; index < charArray.length; index++) {
			byteArray[index] = (byte) charArray[index];
		}

		byte[] md5Bytes = md5Digest.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();

		for (int index = 0; index < md5Bytes.length; index++) {
			int val = ((int) md5Bytes[index]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}

		md5 = hexValue.toString();
		return md5;
	}

	public static String bytes2Hex(byte[] bytes) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < bytes.length; n++) {
			stmp = (Integer.toHexString(bytes[n] & 0XFF));
			if (stmp.length() == 1) {
				hs += "0" + stmp;
			} else {
				hs += stmp;
			}
		}
		return hs.toLowerCase(Locale.ENGLISH);
	}

	public static String getSHA1(String text) {
		String sha1 = null;
		if (TextUtils.isEmpty(text)) {
			return sha1;
		}
		MessageDigest sha1Digest = null;
		try {
			sha1Digest = MessageDigest.getInstance("SHA-1");
		} catch (Exception e) {
			return sha1;
		}
		byte[] textBytes = text.getBytes();
		sha1Digest.update(textBytes, 0, text.length());
		byte[] sha1hash = sha1Digest.digest();
		return bytes2Hex(sha1hash);
	}
}
