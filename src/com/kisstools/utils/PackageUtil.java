/**
 * @author dawson dong
 */

package com.kisstools.utils;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;

import com.kisstools.KissTools;
import com.kisstools.shell.ShellResult;
import com.kisstools.shell.ShellUtil;

public class PackageUtil {

	private static final String BOOT_START_PERMISSION = "android.permission.RECEIVE_BOOT_COMPLETED";

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

	public static PackageInfo getPackageInfo(String packageName) {
		PackageInfo packageInfo = null;
		Context context = KissTools.getApplicationContext();
		PackageManager packageManager = context.getPackageManager();
		try {
			int flags = PackageManager.GET_ACTIVITIES | PackageManager.GET_GIDS
					| PackageManager.GET_CONFIGURATIONS
					| PackageManager.GET_INSTRUMENTATION
					| PackageManager.GET_PERMISSIONS
					| PackageManager.GET_PROVIDERS
					| PackageManager.GET_RECEIVERS
					| PackageManager.GET_SERVICES
					| PackageManager.GET_SIGNATURES
					| PackageManager.GET_UNINSTALLED_PACKAGES;
			packageInfo = packageManager.getPackageInfo(packageName, flags);
		} catch (Exception ignore) {
		}
		return packageInfo;
	}

	public static boolean isBootStart(String packageName) {
		Context context = KissTools.getApplicationContext();
		PackageManager pm = context.getPackageManager();
		int flag = pm.checkPermission(BOOT_START_PERMISSION, packageName);
		return (flag == PackageManager.PERMISSION_GRANTED);
	}

	public static boolean isAutoStart(String packageName) {
		Context context = KissTools.getApplicationContext();
		PackageManager pm = context.getPackageManager();
		Intent intent = new Intent(Intent.ACTION_BOOT_COMPLETED);
		List<ResolveInfo> resolveInfoList = pm.queryBroadcastReceivers(intent,
				PackageManager.GET_DISABLED_COMPONENTS);
		for (ResolveInfo ri : resolveInfoList) {
			String pn = ri.loadLabel(pm).toString();
			if (packageName.equals(pn)) {
				return true;
			}
		}
		return false;
	}

	public static String getPackageDir(String packageName) {
		String applicationDir = null;
		PackageInfo pi = getPackageInfo(packageName);
		if (pi != null) {
			applicationDir = pi.applicationInfo.dataDir;
		}
		return applicationDir;
	}
}
