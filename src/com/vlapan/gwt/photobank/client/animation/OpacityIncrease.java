package com.vlapan.gwt.photobank.client.animation;

public class OpacityIncrease extends Opacity {
	public OpacityIncrease(HasOpacity widget, double opacity) {
		super(widget, opacity);
	}

	@Override
	protected void onUpdate(double progress) {
		widget.setOpacity(Math.rint(progress * opacity * 100d) / 100d);
	}
}
