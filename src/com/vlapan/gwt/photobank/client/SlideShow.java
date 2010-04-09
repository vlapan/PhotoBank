package com.vlapan.gwt.photobank.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.vlapan.gwt.photobank.client.icons.Icons;
import com.vlapan.gwt.photobank.client.ui.selector.ActionSelector;
import com.vlapan.gwt.photobank.client.ui.selector.Selector;

public class SlideShow extends Timer {
	public static final Icons icons = GWT.create(Icons.class);
	private boolean started = false;
	private int delay = 2000;
	private static final int delayStep = 200;
	private static final int delayUpLimit = 10000;
	private static final int delayDownLimit = 800;
	private Selector<?> selector;

	public SlideShow(Selector<?> selector) {
		this.selector = selector;
	}

	@Override
	public void run() {
		selector.next();
//		PhotoBank.np.stay();
	}

	public void start() {
		PhotoBank.photoBrowser.albumSelector.setVisible(false);
		PhotoBank.photoBrowser.photoSelector.setVisible(false);
		ActionSelector.slideshow.setResource(icons.pause());
		PhotoBank.np.autoHide = false;
		scheduleRepeating(delay);
	}

	public void stop() {
		PhotoBank.photoBrowser.albumSelector.setVisible(true);
		PhotoBank.photoBrowser.photoSelector.setVisible(true);
		ActionSelector.slideshow.setResource(icons.play());
		PhotoBank.np.autoHide = true;
		cancel();
	}

	public void trigger() {
		if (started) {
			started = false;
			stop();
		} else {
			started = true;
			start();
		}
	}

	public void increaseDelay() {
		if (delay < delayUpLimit)
			delay = delay + delayStep;
		start();
	}

	public void decreaseDelay() {
		if (delay >= delayDownLimit)
			delay = delay - delayStep;
		scheduleRepeating(delay);
		start();
	}
}
