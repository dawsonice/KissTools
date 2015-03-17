/**
 * @author dawson dong
 */

package me.dawson.kisstools.utils;

import me.dawson.kisstools.KissTools;
import me.dawson.kisstools.shell.ShellResult;
import me.dawson.kisstools.shell.ShellUtil;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

public class PackageUtil {

	// install package normally
	public static final boolean install(String filePath) {
		if (!FileUtil.exists(filePath)) {
			return false;
		}
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + filePath),
				"application/vnd.android.package-archive");
		Context context = KissTools.getApplicationContext();
		return ActivityUtil.startActivity(context, intent);
	}

	// install package silently
	public static final boolean silentInstall(String filePath) {
		if (!FileUtil.exists(filePath)) {
			return false;
		}

		String command = new StringBuilder().append("pm install ")
				.append(filePath.replace(" ", "\\ ")).toString();
		ShellUtil shell = new ShellUtil();
		shell.prepare(true);
		ShellResult result = shell.execute(command);
		return (result.resultCode == 0);
	}

	// uninstall package normally
	public static final boolean uninstall(String packageName) {
		if (TextUtils.isEmpty(packageName)) {
			return false;
		}

		Intent intent = new Intent(Intent.ACTION_DELETE, Uri.parse("package:"
				+ packageName));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Context context = KissTools.getApplicationContext();
		return ActivityUtil.startActivity(context, intent);
	}

	public static boolean silentUninstall(String packageName, boolean keepData) {
		if (TextUtils.isEmpty(packageName)) {
			return false;
		}

		String command = new StringBuilder().append("pm uninstall")
				.append(keepData ? " -k " : " ")
				.append(packageName.replace(" ", "\\ ")).toString();
		ShellUtil shell = new ShellUtil();
		shell.prepare(true);
		ShellResult result = shell.execute(command);
		return (result.resultCode == 0);
	}
}
