/**
 * @author dawson dong
 */

package com.kisstools.utils;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CloseUtil {

	public static final void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {

			}
		}
	}

	public static final void close(Socket closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {

			}
		}
	}

	public static final void close(ServerSocket closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {

			}
		}
	}
}
