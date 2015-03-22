/**
 *
 * Copyright (c) 2014 CoderKiss
 *
 * CoderKiss[AT]gmail.com
 *
 */

package com.kisstools.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class KeyboardUtil implements OnGlobalLayoutListener {
	static public interface KeyboardListener {
		public void onKeyboardVisible(boolean visible);
	}

	private int mHeight;
	private boolean mVisible;
	private View mRootView;
	private KeyboardListener mListener;

	public KeyboardUtil(Activity activity) {
		mVisible = false;
		mHeight = 0;
		if (activity == null) {
			return;
		}

		try {
			mRootView = activity.getWindow().getDecorView()
					.findViewById(android.R.id.content);
			mRootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setListener(KeyboardListener listener) {
		this.mListener = listener;
	}

	@Override
	public void onGlobalLayout() {
		if (mHeight == 0) {
			mHeight = mRootView.getMeasuredHeight();
			return;
		}

		if (mListener == null) {
			return;
		}

		int height = mRootView.getHeight();

		if (!mVisible && mHeight > (height + 100)) {
			mVisible = true;
			mListener.onKeyboardVisible(mVisible);
			mHeight = height;
		} else if (mVisible && mHeight < (height - 100)) {
			mVisible = false;
			mListener.onKeyboardVisible(mVisible);
			mHeight = height;
		}
	}
}
