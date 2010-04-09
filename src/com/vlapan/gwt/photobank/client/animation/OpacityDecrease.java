package com.vlapan.gwt.photobank.client.animation;

public class OpacityDecrease extends Opacity {
	public OpacityDecrease(HasOpacity widget, double opacity) {
		super(widget, opacity);
	}

	@Override
	protected void onUpdate(double progress) {
		widget.setOpacity(opacity
				- (Math.rint(progress * opacity * 100d) / 100d));
	}
}
