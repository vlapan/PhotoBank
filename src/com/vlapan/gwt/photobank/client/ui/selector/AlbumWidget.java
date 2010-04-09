package com.vlapan.gwt.photobank.client.ui.selector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.vlapan.gwt.photobank.client.PhotoBank;
import com.vlapan.gwt.photobank.client.data.full.AlbumFull;
import com.vlapan.gwt.photobank.client.enums.Status;

public class AlbumWidget extends SelectorWidget implements LoadHandler {
	public int currentPhoto = -1;
	public String previewUrl;

	public AlbumWidget() {
		super();
		image.addLoadHandler(this);
		image.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				PhotoBank.photoBrowser.albumSelector.select(index);
			}
		});
	}

	public void setAlbum(final AlbumFull album) {
		this.status = Status.LOADING;
		image.setTitle(album.getTitle());
		image.setUrl(album.getPhotos().get(0).getUrlPreview75());
	}

	public void onLoad(LoadEvent event) {
		this.status = Status.LOADED;
	}
}