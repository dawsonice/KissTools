/**
 * @author dawson dong
 */

package me.dawson.kisstools.utils;

import me.dawson.kisstools.KissTools;
import android.content.Context;
import android.content.pm.ApplicationInfo;

public class DebugUtil {

	public static boolean isDebugable() {
		try {
			Context context = KissTools.getApplicationContext();
			ApplicationInfo info = context.getApplicationInfo();
			return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}