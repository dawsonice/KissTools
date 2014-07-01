package me.dawson.kisstools.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.text.TextUtils;

public class ZipUtil {
	public static final String TAG = "ZipUtil";

	public static boolean zip(String zipPath, List<String> filePaths) {
		if (TextUtils.isEmpty(zipPath) || filePaths == null
				|| filePaths.isEmpty()) {
			return false;
		}

		FileUtil.delete(zipPath);
		if (!FileUtil.create(zipPath)) {
			LogUtil.e(TAG, "failed to create zip file");
			return false;
		}

		return true;
	}

	public static boolean unzip(String zipPath, String unzipFolder) {
		if (!FileUtil.exists(zipPath)) {
			LogUtil.e(TAG, "zip path not exists!");
			return false;
		}

		if (!FileUtil.mkdirs(unzipFolder, true)) {
			LogUtil.e(TAG, "failed to create unzip folder.");
			return false;
		}

		try {
			FileInputStream fin = new FileInputStream(zipPath);
			ZipInputStream zin = new ZipInputStream(fin);
			ZipEntry ze = null;
			while ((ze = zin.getNextEntry()) != null) {
				String zeName = ze.getName();
				LogUtil.d(TAG, "unzip entry " + zeName);

				String zePath = unzipFolder + "/" + zeName;
				if (ze.isDirectory()) {
					FileUtil.mkdirs(zePath);
				} else {
					FileOutputStream fout = new FileOutputStream(zePath);

					byte[] buffer = new byte[8192];
					int len;
					while ((len = zin.read(buffer)) != -1) {
						fout.write(buffer, 0, len);
					}

					fout.close();
					zin.closeEntry();
				}
			}
			zin.close();
		} catch (Exception e) {
			LogUtil.e("unzip exception", e);
			return false;
		}
		return true;
	}
}
