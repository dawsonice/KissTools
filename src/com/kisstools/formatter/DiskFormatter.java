/**
 * @author dawson dong
 */

package com.kisstools.formatter;

import java.text.DecimalFormat;

public class DiskFormatter {

	public static final int UNIT = 1024;
	public static final String FORMAT = "#.00";

	public static final String B = "B";
	public static final String KB = "KB";
	public static final String MB = "MB";
	public static final String GB = "GB";
	public static final String TB = "TB";

	private double kbUnit;
	private double mbUnit;
	private double gbUnit;
	private double tbUnit;

	private String format = FORMAT;
	private int unit = UNIT;

	public DiskFormatter() {
		calUnits();
	}

	public void setUnit(int unit) {
		if (unit > 0) {
			this.unit = unit;
			calUnits();
		}
	}

	private void calUnits() {
		kbUnit = unit;
		mbUnit = unit * kbUnit;
		gbUnit = unit * mbUnit;
		tbUnit = unit * gbUnit;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String format(double size) {
		String result = null;
		if (size < 0) {
			return result;
		} else if (size < kbUnit) {
			return size + "B";
		} else if (size < mbUnit) {
			return division(size, kbUnit) + "KB";
		} else if (size < gbUnit) {
			return division(size, mbUnit) + "MB";
		} else if (size < tbUnit) {
			return division(size, gbUnit) + "GB";
		} else {
			return division(size, tbUnit) + "TB";
		}
	}

	private String division(double size, double unit) {
		double result = size / unit;
		DecimalFormat df = new DecimalFormat(format);
		String formatted = df.format(result);
		return formatted;
	}
}
