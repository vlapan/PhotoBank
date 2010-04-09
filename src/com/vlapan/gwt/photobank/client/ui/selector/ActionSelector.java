package com.vlapan.gwt.photobank.client.ui.selector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.vlapan.gwt.photobank.client.PhotoBank;
import com.vlapan.gwt.photobank.client.animation.SlideVertical;
import com.vlapan.gwt.photobank.client.data.Settings;
import com.vlapan.gwt.photobank.client.enums.Status;
import com.vlapan.gwt.photobank.client.icons.Icons;

public class ActionSelector extends CloseingSelector<ActionWidget> {
	private static final class RightHandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			PhotoBank.photoBrowser.photoSelector.next();
			PhotoBank.photoBrowser.focusCatcher.setFocus(true);
		}
	}

	private static final class SlideshowHandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			PhotoBank.photoBrowser.slideShow.trigger();
			PhotoBank.photoBrowser.focusCatcher.setFocus(true);
		}
	}

	private static final class LeftHandler implements ClickHandler {
		public void onClick(final ClickEvent event) {
			PhotoBank.photoBrowser.slideShow.cancel();
			PhotoBank.photoBrowser.photoSelector.prev();
			PhotoBank.photoBrowser.focusCatcher.setFocus(true);
		}
	}

	private static final class HomeHandler implements ClickHandler {
		public void onClick(final ClickEvent event) {
			PhotoBank.photoBrowser.photoSelector.select();
			PhotoBank.photoBrowser.focusCatcher.setFocus(true);
		}
	}

	private static final class EndHandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			PhotoBank.photoBrowser.photoSelector.end();
			PhotoBank.photoBrowser.focusCatcher.setFocus(true);
		}
	}

	public static final Icons ICONS = GWT.create(Icons.class);
	public static final ActionWidget slideshow = new ActionWidget(ICONS.play());

	public ActionSelector() {
		super(new SlideVertical());

		ActionWidget home = new ActionWidget(ICONS.home());
		home.addClickHandler(new HomeHandler());
		this.add(home);
		home.getElement().getStyle().setProperty("filter", "alpha(opacity=60)");
		home.getElement().getStyle().setProperty("opacity", "0.6");

		ActionWidget left = new ActionWidget(ICONS.left());
		left.addClickHandler(new LeftHandler());
		this.add(left);
		left.getElement().getStyle().setProperty("filter", "alpha(opacity=60)");
		left.getElement().getStyle().setProperty("opacity", "0.6");

		slideshow.addClickHandler(new SlideshowHandler());
		this.add(slideshow);
		slideshow.getElement().getStyle().setProperty("filter",
				"alpha(opacity=60)");
		slideshow.getElement().getStyle().setProperty("opacity", "0.6");

		ActionWidget right = new ActionWidget(ICONS.right());
		right.addClickHandler(new RightHandler());
		this.add(right);
		right.getElement().getStyle().setProperty("opacity", "0.6");
		right.getElement().getStyle()
				.setProperty("filter", "alpha(opacity=60)");

		ActionWidget end = new ActionWidget(ICONS.end());
		end.addClickHandler(new EndHandler());
		this.add(end);
		end.getElement().getStyle().setProperty("opacity", "0.6");
		end.getElement().getStyle().setProperty("filter", "alpha(opacity=60)");

		if (Settings.getInstance().getVote()) {
			ActionWidget star = new ActionWidget(ICONS.star());
			this.add(star);
			star.getElement().getStyle().setProperty("opacity", "0.6");
			star.getElement().getStyle().setProperty("filter",
					"alpha(opacity=60)");
		}

		Window.addResizeHandler(new ResizeHandler() {
			public void onResize(ResizeEvent event) {
				setPosition();
			}
		});
	}

	public void setSize() {

	}

	@Override
	public void setPosition() {
		this.setSize(this.getWidgetCount() * 32 + "px", "32px");
		final int left = PhotoBank.photoBrowser.getOffsetWidth() / 2
				- (this.getWidgetCount() * 32 / 2);
		final int top = status == Status.OPENED ? getOpenPx() : getClosePx();
		this.getElement().getStyle().setPropertyPx("top", top);
		this.getElement().getStyle().setPropertyPx("left", left);
	}

	@Override
	public void next() {
	}

	@Override
	public void prev() {
	}

	@Override
	public int getClosePx() {
		return PhotoBank.photoBrowser.getOffsetHeight() - padding;
	}

	@Override
	public int getOpenPx() {
		return PhotoBank.photoBrowser.getOffsetHeight()
				- this.getOffsetHeight();
	}
}
