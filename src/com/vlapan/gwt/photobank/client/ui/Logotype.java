package com.vlapan.gwt.photobank.client.ui;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Label;
import com.vlapan.gwt.photobank.client.PhotoBank;

public class Logotype extends Label {
	private static final double OPACITY = 0.5;
	protected final Animation fade = new Animation() {
		@Override
		protected void onUpdate(final double progress) {
			final double opacity = OPACITY - progress * OPACITY;
			getElement().getStyle().setProperty("opacity", opacity + "");
			getElement().getStyle().setProperty("filter",
					"alpha(opacity=" + opacity * 100 + ")");
			if (progress == 1) {
				removeFromParent();
			}
		}
	};

	public Logotype(String str) {
		super(str);
		setVisible(false);
		getElement().getStyle().setPropertyPx("padding", 0);
		getElement().getStyle().setPropertyPx("margin", 0);
		getElement().getStyle().setProperty("lineHeight", "100%");
		getElement().getStyle().setProperty("opacity", OPACITY + "");
		getElement().getStyle().setProperty("filter",
				"alpha(opacity=" + OPACITY * 100 + ")");
		getElement().getStyle().setProperty("fontSize", "3em");
		getElement().getStyle().setProperty("color", "#555");
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				setVisible(true);
				getElement().getStyle().setPropertyPx("top",
						PhotoBank.photoBrowser.getOffsetHeight() / 4);
				getElement().getStyle().setPropertyPx(
						"left",
						PhotoBank.photoBrowser.getOffsetWidth() / 2 - getOffsetWidth()
								/ 2);
				fade.run(700);
			}
		});
	}
}
