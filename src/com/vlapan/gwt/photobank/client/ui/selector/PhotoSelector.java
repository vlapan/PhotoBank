package com.vlapan.gwt.photobank.client.ui.selector;

import java.util.HashMap;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;
import com.vlapan.gwt.photobank.client.ItemBuffer;
import com.vlapan.gwt.photobank.client.Logs;
import com.vlapan.gwt.photobank.client.PhotoBank;
import com.vlapan.gwt.photobank.client.animation.SlideVertical;
import com.vlapan.gwt.photobank.client.data.Data;
import com.vlapan.gwt.photobank.client.data.Photo;
import com.vlapan.gwt.photobank.client.data.full.AuthorFull;
import com.vlapan.gwt.photobank.client.enums.Status;
import com.vlapan.gwt.photobank.client.event.PhotoSelectEvent;
import com.vlapan.gwt.photobank.client.ui.PhotoBrowser;
import com.vlapan.gwt.photobank.client.ui.PhotoWidget;

public class PhotoSelector extends CloseingSelector<PreviewWidget> {
	public final PhotoSelector obj = this;
	private Timer close = new Timer() {
		@Override
		public void run() {
			close();
		}
	};
	public ItemBuffer<PreviewWidget> buffer = new ItemBuffer<PreviewWidget>();

	public PhotoSelector() {
		super(new SlideVertical());
		this.setHeight("75px");
	}

	@Override
	public void setPosition() {
		setPosition(-1);
	}

	public void setPosition(int max) {
		Logs.getInstance().add(getClass(), "setPosition( max = " + max + " )");
		setShow(max);
		final int top = status == Status.OPENED ? 0 : 3 - this
				.getOffsetHeight();
		final int left = PhotoBank.photoBrowser.getOffsetWidth() / 2
				- (size * show / 2);
		this.getElement().getStyle().setPropertyPx("top", top);
		this.getElement().getStyle().setPropertyPx("left", left);
	}

	public void select() {
		select(0);
	}

	public void select(int photoIndex) {
		PhotoBank.photoBrowser.photoView.setLoad(true);
		// PhotoBank.np.stay();
		// PhotoBank.np.setVisible(false);
		// PhotoBank.np.status = NotificationPanel.Status.CLOSED;
		final HashMap<String, Integer> var = new HashMap<String, Integer>();
		var.put("p", photoIndex);
		PhotoBrowser.historyManager.change(var);
		PhotoSelectEvent.fire(photoIndex);
		JsArray<Photo> photos = Data.getPhotos();
		Image.prefetch(photos.get(photoIndex).getOriginalUrl());
		Data.photoSelected = photoIndex;
		this.clear();
		int start = photoIndex - ((show - 1) / 2) > 0 ? photoIndex
				- ((show - 1) / 2) : 0;
		int stop;
		if (start + show < photos.length()) {
			stop = start + show;
		} else {
			stop = photos.length();
			if (photos.length() - show > 0)
				start = photos.length() - show;
			else
				start = 0;
		}
		Logs.getInstance().add(getClass(),
				"select:" + photoIndex + "(" + start + "-" + stop + ")");
		for (int i = start; i < stop; i++) {
			Photo photo = photos.get(i);
			PreviewWidget previewWidget;
			if (buffer.containsKey(i)) {
				previewWidget = buffer.get(i);
				add(previewWidget);
			} else {
				previewWidget = new PreviewWidget();
				add(previewWidget);
				previewWidget.setPreview(photo);
				previewWidget.setIndex(i, i == photos.length() - 1 ? true
						: false);
				previewWidget.setSize(size + "px", size + "px");
				previewWidget.image.setSize(size + "px", size + "px");
				buffer.put(i, previewWidget);
			}
			if (i == photoIndex) {
				PhotoWidget oImg = new PhotoWidget(photo);
				PhotoBank.photoBrowser.photoView.setPhotoWidget(oImg);
				previewWidget.select();
				setSelected(previewWidget);
				// PhotoBank.np.setText(photo.getTitle(), photo.getPlace());
			} else {
				Image.prefetch(photo.getOriginalUrl());
				Image.prefetch(photo.getUrlPreview75());
				previewWidget.blur();
			}
		}
		if (stop + 1 < photos.length()) {
			Image.prefetch(photos.get(stop + 1).getUrlPreview75());
		}
		// PhotoBank.focusCatcher.setFocus(true);
	}

	@Override
	public void next() {
		int i = Data.photoSelected;
		if (i == Data.getPhotos().length() - 1) {
			return;
		}
		final int nextIndex = i + 1;
		select(nextIndex);
	}

	@Override
	public void prev() {
		int i = Data.photoSelected;
		if (i == 0) {
			return;
		}
		final int prevIndex = i - 1;
		select(prevIndex);
	}

	public void end() {
		this.select(Data.getPhotos().length() - 1);
	}

	public void touch() {
		this.open();
		close.schedule(1500);
	}

	public void add(final PreviewWidget photo) {
		// photo.image.addClickHandler(new ClickHandler() {
		// @Override
		// public void onClick(ClickEvent event) {
		// obj.select(photo.index);
		// }
		// });
		super.add(photo);
		if (this.getWidgetCount() <= show) {
			this.setWidth(this.getWidgetCount() * size + "px");
		}
	}

	@Override
	public int getClosePx() {
		return padding - this.getOffsetHeight();
	}

	@Override
	public int getOpenPx() {
		return 0;
	}
}