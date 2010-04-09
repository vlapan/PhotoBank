package com.vlapan.gwt.photobank.client.event;

import java.util.ArrayList;
import java.util.List;

public class PhotoSelectEvent extends Event {
	public static final List<PhotoSelectHandler> handlers = new ArrayList<PhotoSelectHandler>();

	public static void fire(int photoId) {
		for (PhotoSelectHandler handler : handlers) {
			handler.onPhotoSelect(photoId);
		}
	}
}
