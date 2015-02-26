package me.dawson.kisstools.data;

import me.dawson.kisstools.utils.FileUtil;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class KVPreference implements KVDataSet {
	public static final String TAG = "KVPreference";

	private static final String DEFAULT_NAME = "kv_sp";

	private SharedPreferences sp;
	private String spName;
	private Context spContext;

	public KVPreference(Context context) {
		this(context, DEFAULT_NAME);
	}

	public KVPreference(Context context, String name) {
		this.spContext = context;
		this.spName = name;
		int mode = Context.MODE_PRIVATE;
		sp = spContext.getSharedPreferences(spName, mode);
	}

	@Override
	public String get(String key) {
		String value = sp.getString(key, null);
		return value;
	}

	@Override
	public boolean set(String key, String value) {
		if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
			return false;
		}

		boolean succeed = sp.edit().putString(key, value).commit();
		return succeed;
	}

	@Override
	public boolean remove(String key) {
		if (TextUtils.isEmpty(key)) {
			return false;
		}

		boolean succeed = sp.edit().remove(key).commit();
		return succeed;
	}

	@Override
	public boolean clear() {
		boolean succeed = sp.edit().clear().commit();
		return succeed;
	}

	@Override
	public boolean delete() {
		boolean succeed = clear();
		String absPath = spContext.getFilesDir().getParent() + "/shared_prefs/";
		absPath = absPath + spName + ".xml";
		succeed |= FileUtil.delete(absPath);
		return succeed;
	}
}
