/**
 *
 * Copyright (c) 2014 CoderKiss
 *
 * CoderKiss[AT]gmail.com
 *
 */

package me.dawson.kisstools.utils;

import android.content.Context;

public class ClipboardUtil {
	public static final String TAG = "ClipboardHelper";

	@SuppressWarnings("deprecation")
	public static boolean setClipboard(Context context, String text) {
		android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		clipboard.setText(text);
		return true;
	}

	@SuppressWarnings("deprecation")
	public static String getClipboard(Context context) {
		android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		return clipboard.getText().toString();
	}
}
