/**
 * @author dawson dong
 */

package com.kisstools.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnSystemUiVisibilityChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kisstools.utils.SystemUtil;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FullscreenView extends LinearLayout implements
		OnSystemUiVisibilityChangeListener {

	public static final String TAG = "FullscreenView";

	private View decorView;
	private View paddingView;
	private TextView tvTitleBar;

	public FullscreenView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Activity activity = (Activity) getContext();
		decorView = activity.getWindow().getDecorView();
	}

	protected void onFinishInflate() {
		super.onFinishInflate();
		paddingView = new TextView(getContext());
		paddingView.setVisibility(View.GONE);
		this.setOrientation(VERTICAL);
		int height = SystemUtil.getStatusBarHeight(getContext());
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				height);
		this.addView(paddingView, 0, params);
		tvTitleBar = new TextView(getContext());
		tvTitleBar.setText("TitleBar");
		tvTitleBar.setBackgroundColor(0xB0444444);
		height = SystemUtil.getActionBarHeight(getContext());
		params = new LayoutParams(LayoutParams.MATCH_PARENT, height);
		this.addView(tvTitleBar, 1, params);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		setOnSystemUiVisibilityChangeListener(this);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
	}

	public void enableFullscreen() {
		paddingView.setVisibility(View.VISIBLE);

		int visibility = 0;
		visibility |= SYSTEM_UI_FLAG_LOW_PROFILE;
		visibility |= SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		visibility |= SYSTEM_UI_FLAG_FULLSCREEN;
		visibility |= SYSTEM_UI_FLAG_LAYOUT_STABLE;
		visibility |= SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
		visibility |= SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

		decorView.setSystemUiVisibility(visibility);
	}

	@Override
	public void onSystemUiVisibilityChange(int visibility) {
		Log.d(TAG, "onSystemUiVisibilityChange " + visibility);
		boolean fullscreen = (visibility & SYSTEM_UI_FLAG_LOW_PROFILE) != 0;
		tvTitleBar.setVisibility(fullscreen ? View.INVISIBLE : View.VISIBLE);
	}
}
