/**
 *
 * Copyright (c) 2014 CoderKiss
 *
 * CoderKiss[AT]gmail.com
 *
 */

package me.dawson.kisstools.utils;

import java.util.LinkedList;

import me.dawson.kisstools.KissTools;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

public class MessageUtil {
	public static final String TAG = "Messager";

	public static final String BROADCAST_PARAM = "broadcast_param";
	public static final String REQUEST_ACTION = "request_action";
	public static final String RESPONSE_ACTION = "response_action";

	private static volatile MessageUtil instance;

	public static final MessageUtil sharedInstance() {
		synchronized (MessageUtil.class) {
			if (instance == null) {
				instance = new MessageUtil();
			}
		}
		return instance;
	}

	private LocalBroadcastManager mManager;
	private LinkedList<BroadcastReceiver> mReceivers;

	private MessageUtil() {
		mReceivers = new LinkedList<BroadcastReceiver>();
		mManager = LocalBroadcastManager.getInstance(KissTools
				.getApplicationContext());
	}

	public void registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
		if (receiver == null || filter == null) {
			LogUtil.e(TAG, "invalid parameters");
			return;
		}

		synchronized (mManager) {
			if (mReceivers.contains(receiver)) {
				LogUtil.e(TAG, "unregister old receiver!");
				mManager.unregisterReceiver(receiver);
			}

			mReceivers.addLast(receiver);
			mManager.registerReceiver(receiver, filter);
		}
	}

	public void unregisterReceiver(BroadcastReceiver receiver) {
		if (receiver == null) {
			LogUtil.e(TAG, "invalid parameters!");
			return;
		}

		if (!mReceivers.contains(receiver)) {
			return;
		}

		synchronized (mManager) {
			mReceivers.remove(receiver);
			mManager.unregisterReceiver(receiver);
		}
	}

	public void sendBroadcast(Intent intent) {
		if (intent == null) {
			return;
		}
		String action = intent.getAction();
		LogUtil.d(TAG, "sendBroadcast " + action);
		mManager.sendBroadcast(intent);
	}

	public void sendBroadcast(String action, String info) {
		if (TextUtils.isEmpty(action)) {
			LogUtil.e(TAG, "invalid action");
			return;
		}

		Intent intent = new Intent(action);
		if (!TextUtils.isEmpty(info)) {
			intent.putExtra(BROADCAST_PARAM, info);
		}
		mManager.sendBroadcast(intent);
	}
}
