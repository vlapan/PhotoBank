package com.vlapan.gwt.photobank.client.ui;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.vlapan.gwt.photobank.client.PhotoBank;
import com.vlapan.gwt.photobank.client.enums.Status;

public class PhotoView extends AbsolutePanel {

	private Status status = Status.LOADING;
	// public final AbsolutePanel panel = new AbsolutePanel();
	private final Image loadImg = new Image("loading.gif");
	public PhotoWidget photo = null;
	Style style = getElement().getStyle();
	Style loadStyle = loadImg.getElement().getStyle();
	public Animation loadShow = new Animation() {
		@Override
		protected void onUpdate(double progress) {
			loadStyle.setProperty("filter", "alpha(opacity=" + progress * 100
					+ ")");

			loadStyle.setProperty("opacity", progress + "");
		}
	};

	public PhotoView() {
		style.setProperty("textAlign", "center");
		setSize("100%", "100%");
		// panel.setWidth("100%");
		// panel.setVisible(false);
		// add(panel, DockPanel.CENTER);
		// setCellHorizontalAlignment(panel, DockPanel.ALIGN_CENTER);
		// setCellVerticalAlignment(panel, DockPanel.ALIGN_MIDDLE);
		loadImg.setPixelSize(32, 32);
		loadStyle.setPropertyPx("marginBottom", 20);
		loadStyle.setProperty("opacity", "0");
		add(PhotoBank.np, 0, 0);
	}

	public void setLoad(boolean load) {
		if (load == true) {
			if (status == Status.LOADING) {
				return;
			}
			status = Status.LOADING;
			if (photo != null) {
				photo.show.cancel();
				photo.setVisible(false);
			}
			loadStyle.setProperty("opacity", "0");
			add(loadImg, PhotoBank.photoBrowser.getOffsetWidth() / 2 - 16,
					PhotoBank.photoBrowser.getOffsetHeight() / 2 - 16);
			loadShow.run(1500);
		} else {
			if (status == Status.SHOWING)
				return;
			status = Status.SHOWING;
			loadShow.cancel();
			this.remove(loadImg);
		}
	}

	public void setPhotoWidget(PhotoWidget w) {
		if (photo != null) {
			remove(photo);
		}
		photo = w;
		add(photo);
	}
}