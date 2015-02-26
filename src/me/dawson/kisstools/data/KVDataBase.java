package me.dawson.kisstools.data;

import java.util.LinkedList;
import java.util.List;

import me.dawson.kisstools.utils.FileUtil;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

public class KVDataBase extends SQLiteOpenHelper implements KVDataSet {

	public static final String TAG = "DataBase";

	private static final int DB_VERSION = 1;
	private static final String DEFAULT_DB_NAME = "kv_data.db";
	private static final String DEFAULT_TABLE_NAME = "key_value";

	private Context dbContext;
	private String dbName;

	public KVDataBase(Context context) {
		this(context, DEFAULT_DB_NAME);
	}

	public KVDataBase(Context context, String name) {
		super(context, name, null, DB_VERSION);
		this.dbName = name;
		this.dbContext = context;
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ DEFAULT_TABLE_NAME
				+ "(id INTEGER, key TEXT UNIQUE, value TEXT NOT NULL, PRIMARY KEY(id, key));");
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public String get(String key) {
		if (TextUtils.isEmpty(key)) {
			return null;
		}

		String sql = "SELECT * FROM " + DEFAULT_TABLE_NAME + " WHERE key='"
				+ key + "'";
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		String value = null;
		if (cursor.moveToFirst()) {
			value = cursor.getString(cursor.getColumnIndex("value"));
		}
		cursor.close();
		db.close();

		return value;
	}

	public boolean set(String key, String value) {
		if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
			return false;
		}

		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("INSERT OR REPLACE INTO " + DEFAULT_TABLE_NAME
				+ "(key, value) VALUES(?, ?)", new Object[] { key, value });
		db.close();
		return true;
	}

	public boolean remove(String key) {
		if (TextUtils.isEmpty(key)) {
			return false;
		}
		String sql = "DELETE FROM " + DEFAULT_TABLE_NAME + " WHERE key " + "='"
				+ key + "'";
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(sql);
		db.close();
		return true;
	}

	public List<String> list() {
		String sql = "SELECT * FROM " + DEFAULT_TABLE_NAME
				+ " ORDER BY id  ASC";
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		LinkedList<String> values = new LinkedList<String>();
		while (cursor.moveToNext()) {
			String value = cursor.getString(cursor.getColumnIndex("value"));
			values.addLast(value);
		}
		cursor.close();
		db.close();
		return values;
	}

	@Override
	public boolean clear() {
		String sql = "DELETE * FROM " + DEFAULT_TABLE_NAME;
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(sql);
		db.close();
		return true;
	}

	@Override
	public boolean delete() {
		String absPath = dbContext.getFilesDir().getParent() + "/databases/";
		absPath = absPath + dbName;
		boolean succeed = FileUtil.delete(absPath);
		return succeed;
	}
}
