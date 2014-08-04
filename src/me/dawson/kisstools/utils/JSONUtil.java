/**
 *
 * Copyright (c) 2014 CoderKiss
 *
 * CoderKiss[AT]gmail.com
 *
 */

package me.dawson.kisstools.utils;

import java.math.BigDecimal;
import java.util.Set;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JSONUtil {
	public static final String TAG = "JSONUtil";

	public static boolean isJSON(String value) {
		if (TextUtils.isEmpty(value)) {
			return false;
		}

		try {
			JSON.parse(value);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static JSON parseJSON(String value) {
		if (TextUtils.isEmpty(value)) {
			return null;
		}

		JSON jo = null;
		try {
			jo = (JSON) JSON.parse(value);
		} catch (Exception e) {
		}
		return jo;
	}

	public static boolean isJSONObject(String value) {
		if (TextUtils.isEmpty(value)) {
			return false;
		}

		try {
			JSON.parseObject(value);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static JSONObject parseObject(String value) {
		if (TextUtils.isEmpty(value)) {
			return null;
		}

		JSONObject jo = null;
		try {
			jo = JSON.parseObject(value);
		} catch (Exception e) {
		}
		return jo;
	}

	public static boolean isJSONArray(String value) {
		if (TextUtils.isEmpty(value)) {
			return false;
		}

		try {
			JSON.parseArray(value);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static JSONArray parseArray(String value) {
		if (TextUtils.isEmpty(value)) {
			return null;
		}

		JSONArray ja = null;
		try {
			ja = JSON.parseArray(value);
		} catch (Exception e) {
		}
		return ja;
	}

	public static boolean isEmpty(JSONObject jo) {
		if (jo == null || jo.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isEmpty(JSONArray ja) {
		if (ja == null || ja.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public static String getString(JSONObject params, String key) {
		return getString(params, key, "");
	}

	private static String getString(JSONObject params, String key, String df) {
		if (df == null) {
			df = "";
		}
		return getValue(params, key, df);
	}

	public static boolean getBoolean(JSONObject params, String key, boolean df) {
		return getValue(params, key, df);
	}

	public static int getInt(JSONObject params, String key) {
		return getInt(params, key, 0);
	}

	public static int getInt(JSONObject params, String key, int df) {
		return getValue(params, key, df);
	}

	public static JSONObject getJSONObject(JSONObject params, String key) {
		return getJSONObject(params, key, null);
	}

	public static JSONObject getJSONObject(JSONObject params, String key,
			JSONObject df) {
		if (df == null) {
			df = new JSONObject();
		}
		return getValue(params, key, df);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getValue(JSONObject params, String key, T df) {
		if (params == null || params.isEmpty()) {
			return df;
		}
		if (df == null) {
			return df;
		}

		if (!params.containsKey(key)) {
			return df;
		}

		T value = df;
		Object obj = params.get(key);
		if (obj != null && value.getClass().isAssignableFrom(obj.getClass())) {
			value = (T) obj;
		} else {
			LogUtil.w(TAG, "[key] " + key + " [value] " + obj);
		}
		return value;
	}

	public static JSONObject toJSONObject(Bundle bundle) {
		JSONObject joBundle = new JSONObject();
		if (bundle == null) {
			return joBundle;
		}

		Set<String> keys = bundle.keySet();
		for (String key : keys) {
			Object value = bundle.get(key);
			joBundle.put(key, value);
		}
		return joBundle;
	}

	public static Bundle toBundle(JSONObject params) {
		return toBundle(null, params);
	}

	public static Bundle toBundle(Bundle bundle, JSONObject params) {
		if (bundle == null) {
			bundle = new Bundle();
		}

		if (params == null || params.isEmpty()) {
			return bundle;
		}

		for (String key : params.keySet()) {
			Object value = params.get(key);
			if (value instanceof Integer) {
				bundle.putInt(key, (Integer) value);
			} else if (value instanceof Boolean) {
				bundle.putBoolean(key, (Boolean) value);
			} else if (value instanceof String) {
				bundle.putString(key, (String) value);
			} else if (value instanceof Long) {
				bundle.putLong(key, (Long) value);
			} else if (value instanceof Double) {
				bundle.putDouble(key, (Double) value);
			} else if (value instanceof Float) {
				float f = (Float) value;
				BigDecimal bd = new BigDecimal(Float.toString(f));
				double dd = bd.doubleValue();
				bundle.putDouble(key, dd);
			} else if (value instanceof JSON) {
				String jsonStr = ((JSON) value).toJSONString();
				bundle.putString(key, jsonStr);
			}
		}

		return bundle;
	}
}
