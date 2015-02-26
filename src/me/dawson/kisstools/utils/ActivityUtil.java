/**
 * @author dawson dong
 */

package me.dawson.kisstools.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ActivityUtil {

	public static void chooseImage(Activity activity, String title,
			int requestCode) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_PICK);
		Intent chooser = Intent.createChooser(intent, title);
		startActivityForResult(activity, chooser, requestCode);
	}

	public static boolean startActivity(Context context, Intent intent) {
		if (context == null || intent == null) {
			return false;
		}

		if (!(context instanceof Activity)) {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}

		try {
			context.startActivity(intent);
		} catch (Exception globalException) {
			// catch all exception here
			globalException.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean startActivityForResult(Activity activity,
			Intent intent, int requestCode) {
		if (activity == null || intent == null) {
			return false;
		}

		try {
			activity.startActivityForResult(intent, requestCode);
		} catch (Exception globalException) {
			globalException.printStackTrace();
			return false;
		}
		return true;
	}
}
