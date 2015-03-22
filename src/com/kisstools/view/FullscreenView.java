/**
 * @author dawson dong
 */

package com.kisstools.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FullscreenView extends RelativeLayout {

	public FullscreenView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void toggleFullscreen() {
		int visibility = getSystemUiVisibility();
		boolean visible = (visibility & SYSTEM_UI_FLAG_FULLSCREEN) != 0;
		setFullscreen(visible);
	}

	private void setFullscreen(boolean fullscreen) {
		int visibility = 0;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			visibility = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| SYSTEM_UI_FLAG_LAYOUT_STABLE
					| SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
		}

		if (!fullscreen) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				visibility |= SYSTEM_UI_FLAG_LOW_PROFILE;
			}
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				visibility |= SYSTEM_UI_FLAG_FULLSCREEN;
			}
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				visibility |= SYSTEM_UI_FLAG_IMMERSIVE
						| SYSTEM_UI_FLAG_IMMERSIVE_STICKY
						| SYSTEM_UI_FLAG_HIDE_NAVIGATION;
			}
		}
		setSystemUiVisibility(visibility);
	}
}
