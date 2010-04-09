package com.vlapan.gwt.photobank.client.data.full;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;

public class AuthorFull extends JavaScriptObject {
	protected AuthorFull() {
	}

	public static native AuthorFull getAuthor() /*-{
		return $wnd.authorData;
	}-*/;

	public final native String getImageHost() /*-{
		return this.imghost;
	}-*/;

	public final native int getId() /*-{
		return this.authorId;
	}-*/;

	public final native String getTitle() /*-{
		return this.author;
	}-*/;

	public final native JsArray<AlbumFull> getAlbums() /*-{
		return this.albums;
	}-*/;

	public final native JsArrayString getHosts()
	/*-{
		return this.hosts;
	}-*/;
}
