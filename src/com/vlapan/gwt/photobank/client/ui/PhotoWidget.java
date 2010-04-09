package com.vlapan.gwt.photobank.client.ui;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.ui.Image;
import com.vlapan.gwt.photobank.client.Logs;
import com.vlapan.gwt.photobank.client.PhotoBank;
import com.vlapan.gwt.photobank.client.animation.HasOpacity;
import com.vlapan.gwt.photobank.client.animation.OpacityIncrease;
import com.vlapan.gwt.photobank.client.data.Photo;
import com.vlapan.gwt.photobank.client.enums.PhotoOrientation;
import com.vlapan.gwt.photobank.client.enums.Status;
import com.vlapan.gwt.photobank.client.event.PhotoShowedEvent;

public class PhotoWidget extends Image implements LoadHandler,
		MouseMoveHandler, ClickHandler, HasOpacity {

	private final class ShowAnimation extends OpacityIncrease {
		private ShowAnimation(HasOpacity widget, double opacity) {
			super(widget, opacity);
		}

		protected void onComplete() {
			super.onComplete();
			PhotoShowedEvent.fire(PhotoWidget.this);
			addMouseMoveHandler(PhotoWidget.this);
			// PhotoBank.np.show(PhotoWidget.this);
		}
	}

	public PhotoOrientation orientation = PhotoOrientation.HORIZONTAL;
	public Status status = Status.READY;
	public int oWidth = -1;
	public int oHeight = -1;
	public final Style style = getElement().getStyle();
	public OpacityIncrease show = new ShowAnimation(this, 1);
	public Photo photo;

	public PhotoWidget(Photo photo) {
		super();
		Logs.getInstance().add(getClass(), "init");
		this.photo = photo;
		this.setOpacity(0);
		addLoadHandler(this);
		addClickHandler(this);
		status = Status.LOADING;
		setUrl(photo.getOriginalUrl());
	}

	public void setISize() {
		if (oHeight > PhotoBank.photoBrowser.getOffsetHeight()
				|| oWidth > PhotoBank.photoBrowser.getOffsetWidth()) {
			int width = oWidth * PhotoBank.photoBrowser.getOffsetHeight()
					/ oHeight;
			if (width < PhotoBank.photoBrowser.getOffsetWidth())
				setWidth(width + "px");
			else
				setWidth(PhotoBank.photoBrowser.getOffsetWidth() + "px");
		} else {
			setWidth(oWidth + "px");
			setHeight(oHeight + "px");
		}
	}

	public void setOSize() {
		oWidth = getWidth();
		oHeight = getHeight();
		if (oWidth < oHeight) {
			orientation = PhotoOrientation.VERTICAL;
		} else if (oWidth == oHeight) {
			orientation = PhotoOrientation.SQUARE;
		}
	}

	public void onLoad(LoadEvent event) {
		if (!isAttached()) {
			return;
		}
		Logs.getInstance().add(getClass(), "onLoad");
		PhotoBank.photoBrowser.photoView.setLoad(false);
//		setVisible(true);
		status = Status.LOADED;
		if (getWidth() > 0) {
			setOSize();
			setISize();
		}
		PhotoBank.photoBrowser.photoView.style.setPropertyPx("top",
				PhotoBank.photoBrowser.getOffsetHeight() / 2
						- getOffsetHeight() / 2);
		show.run(500);
	}

	public void onMouseMove(MouseMoveEvent event) {
		PhotoBank.np.show();
	}

	public void onClick(ClickEvent event) {
		// if (this.orientation == Orientation.HORIZONTAL) {
		// setWidth(PhotoBank.panel.getOffsetWidth() + "px");
		// setHeight(oHeight * PhotoBank.panel.getOffsetWidth() / oWidth
		// + "px");
		// } else {
		// setHeight(PhotoBank.panel.getOffsetHeight() + "px");
		// setWidth(oWidth * PhotoBank.panel.getOffsetHeight() / oHeight
		// + "px");
		// }
	}

	public void setOpacity(double opacity) {
		this.getElement().getStyle().setProperty("opacity", opacity + "");
	}
}