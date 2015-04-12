/**
 * @author dawson dong
 */

package com.kisstools.utils;

import java.io.InputStream;
import java.io.OutputStream;

public class IoUtil {

	// 4k buffer
	public static final int BUFFER_SIZE = 4096;

	public static boolean copy(InputStream is, OutputStream os) {
		final byte[] buffer = new byte[BUFFER_SIZE];
		try {
			int count;
			while ((count = is.read(buffer)) != -1) {
				os.write(buffer, 0, count);
			}
			os.flush();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
