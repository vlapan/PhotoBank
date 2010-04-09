package com.vlapan.gwt.photobank.client.data.full;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.vlapan.gwt.photobank.client.data.Photo;

public class AlbumFull extends JavaScriptObject {
	protected AlbumFull() {
	}

	public final native String getTitle() /*-{
		return this.title;
	}-*/;

	public final native int getId() /*-{
		return this.id;
	}-*/;

	public final native JsArray<Photo> getPhotos() /*-{
		return this.photos;
	}-*/;

	public final native int getSelected() /*-{
		return this.selected ? this.selected : this.photos[0];
	}-*/;

	public final native void setSelected() /*-{
		this.selected;
	}-*/;
}
