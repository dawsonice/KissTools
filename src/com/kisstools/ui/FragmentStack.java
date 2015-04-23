/**
 * @author dawson dong
 */

package com.kisstools.ui;

import java.util.Stack;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentStack<T extends Fragment> {

	private Stack<T> fragmentStack;
	private FragmentManager fragmentManager;
	private int contentId;

	public FragmentStack(FragmentManager fragmentManager, int contentId) {
		this.fragmentManager = fragmentManager;
		this.contentId = contentId;
		this.fragmentStack = new Stack<T>();
	}

	public boolean addFragment(T t) {
		if (t == null || t.isAdded() || fragmentStack.contains(t)) {
			return false;
		}

		if (!fragmentStack.isEmpty()) {
			T topFragment = fragmentStack.peek();
			hideFragment(topFragment);
		}

		fragmentStack.push(t);
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.add(contentId, t).commitAllowingStateLoss();
		return true;
	}

	public boolean showFragment(T fragment) {
		if (fragment == null || fragment.isVisible()) {
			return false;
		}

		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.show(fragment).commitAllowingStateLoss();

		// for the 'show' method will not trigger onResume event
		fragment.onResume();
		return true;
	}

	private boolean hideFragment(T fragment) {
		if (fragment == null || fragment.isHidden()) {
			return false;
		}

		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.hide(fragment).commitAllowingStateLoss();

		// for the 'hide' method will not trigger onPause event
		fragment.onPause();
		return true;
	}

	public boolean popFragment() {
		return removeFragment(fragmentStack.size() - 1);
	}

	public boolean removeFragment(int index) {
		if (fragmentStack.isEmpty()) {
			return false;
		}

		int stackSize = fragmentStack.size();
		if (index < 0 || index >= stackSize) {
			return false;
		}

		T fragment = fragmentStack.get(index);
		return removeFragment(fragment);
	}

	public boolean removeFragment(T t) {
		if (t == null || !fragmentStack.contains(t)) {
			return false;
		}

		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.remove(t).commitAllowingStateLoss();

		fragmentStack.remove(t);
		if (!fragmentStack.isEmpty()) {
			T topFragment = fragmentStack.peek();
			showFragment(topFragment);
		}
		return true;
	}

}
