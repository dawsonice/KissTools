/**
 * @author dawson dong
 */

package com.kisstools.shell;

public class ShellResult {

	public static final String TAG = "ShellResult";

	public int resultCode;
	public String successMsg;
	public String errorMsg;

	public ShellResult() {
		this.resultCode = -1;
	}

	public ShellResult(int resultCode) {
		this.resultCode = resultCode;
	}

	public ShellResult(int resultCode, String successMsg, String errorMsg) {
		this.resultCode = resultCode;
		this.successMsg = successMsg;
		this.errorMsg = errorMsg;
	}

	public boolean success() {
		return (resultCode == 0);
	}
}
