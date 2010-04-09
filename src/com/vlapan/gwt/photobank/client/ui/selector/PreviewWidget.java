package com.vlapan.gwt.photobank.client.ui.selector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.vlapan.gwt.photobank.client.PhotoBank;
import com.vlapan.gwt.photobank.client.data.Photo;
import com.vlapan.gwt.photobank.client.enums.Status;

public class PreviewWidget extends SelectorWidget implements LoadHandler {
	public PreviewWidget() {
		super();
		image.addLoadHandler(this);
	}

	public void setPreview(final Photo photo) {
		status = Status.LOADING;
		image.setUrl(photo.getUrlPreview75());
		image.setTitle(photo.getTitle());
	}

	public void onLoad(LoadEvent event) {
		this.status = Status.LOADED;
		image.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				PhotoBank.photoBrowser.photoSelector.select(index);
				PhotoBank.photoBrowser.focusCatcher.setFocus(true);
			}});
	}
}