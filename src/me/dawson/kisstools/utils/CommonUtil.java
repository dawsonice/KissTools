package me.dawson.kisstools.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;

public class CommonUtil {
	public static final String TAG = "CommonUtil";

	public static long valueOfLong(String text) {
		long value = -1;
		try {
			value = Long.valueOf(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public static void showIME(Context context) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public static void hideIME(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	public final static String getMimeType2(String url) {
		String extension = MimeTypeMap.getFileExtensionFromUrl(url);
		String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
				extension);
		return mimeType;
	}
}
