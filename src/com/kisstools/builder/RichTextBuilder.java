package com.kisstools.builder;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;

public class RichTextBuilder {

	private SpannableString richText;
	private int startIndex;
	private int endIndex;

	public RichTextBuilder(SpannableString ss, String key) {
		richText = ss;
		String text = ss.toString();
		startIndex = text.indexOf(key);
		endIndex = startIndex + key.length();
	}

	public RichTextBuilder(String text, String key) {
		richText = new SpannableString(text);
		startIndex = text.indexOf(key);
		endIndex = startIndex + key.length();
	}

	public RichTextBuilder setBackgroud(int color) {
		BackgroundColorSpan background = new BackgroundColorSpan(color);
		richText.setSpan(background, startIndex, endIndex,
				Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		return this;
	}

	public RichTextBuilder setForeground(int color) {
		ForegroundColorSpan foreground = new ForegroundColorSpan(color);
		richText.setSpan(foreground, startIndex, endIndex,
				Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		return this;
	}

	public RichTextBuilder setSize(int size) {
		AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(size);
		richText.setSpan(sizeSpan, startIndex, endIndex,
				Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		return this;
	}

	public RichTextBuilder setScale(float scale) {
		ScaleXSpan scaleSpan = new ScaleXSpan(scale);
		richText.setSpan(scaleSpan, startIndex, endIndex,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return this;
	}

	public RichTextBuilder setStrike() {
		StrikethroughSpan strikeSpan = new StrikethroughSpan();
		richText.setSpan(strikeSpan, startIndex, endIndex,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return this;
	}

	public RichTextBuilder setTypeface(String typeface) {
		TypefaceSpan typeSpan = new TypefaceSpan(typeface);
		richText.setSpan(typeSpan, startIndex, endIndex,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return this;
	}

	public RichTextBuilder setStyle(int style) {
		StyleSpan styleSpan = new StyleSpan(style);
		richText.setSpan(styleSpan, startIndex, endIndex,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return this;
	}

	public SpannableString build() {
		return richText;
	}
}
