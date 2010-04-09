package com.vlapan.gwt.photobank.client.animation;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.ui.Widget;

public class SlideHorizontal extends Animation implements MyAnimation {
	private Widget w = null;
	int start = 0;
	int stop = 0;
	int distance = 0;

	public void run(Widget w, int stop, int duration) {
		this.w = w;
		this.stop = stop;
		this.start = w.getAbsoluteLeft();
		this.distance = this.stop - this.start;
		this.run(duration);
	}

	@Override
	public void run(int duration) {
		if (w == null || (stop == 0 && start == 0))
			return;
		else
			super.run(duration);
	}

	@Override
	protected void onUpdate(double progress) {
		final String x = start + (distance * progress) + "";
		w.getElement().getStyle().setProperty("left", x);
	}
}
