package com.vlapan.gwt.photobank.client.event;

import java.util.ArrayList;
import java.util.List;

import com.vlapan.gwt.photobank.client.ui.PhotoWidget;

public class PhotoShowedEvent extends Event {
	public static final List<PhotoShowedHandler> handlers = new ArrayList<PhotoShowedHandler>();

	public static void fire(PhotoWidget photo) {
		for (PhotoShowedHandler handler : handlers) {
			handler.onPhotoLoaded(photo);
		}
	}
}
