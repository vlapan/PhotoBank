package com.vlapan.gwt.photobank.client.animation;

import com.google.gwt.animation.client.Animation;
import com.vlapan.gwt.photobank.client.Logs;

public abstract class Opacity extends Animation {
	public final double opacity;
	public HasOpacity widget;

	public Opacity(HasOpacity widget, double opacity) {
		super();
		this.widget = widget;
		this.opacity = opacity;
	}

	@Override
	protected void onStart() {
		super.onStart();
		Logs.getInstance().add(getClass(), "started");
	}

	@Override
	protected void onComplete() {
		super.onComplete();
		Logs.getInstance().add(getClass(), "completed");
	}

	@Override
	protected void onCancel() {
		super.onCancel();
		Logs.getInstance().add(getClass(), "canceled");
//		widget.setOpacity(opacity);
	}
}
