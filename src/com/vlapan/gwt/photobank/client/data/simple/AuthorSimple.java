package com.vlapan.gwt.photobank.client.data.simple;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.vlapan.gwt.photobank.client.data.Photo;

public class AuthorSimple extends JavaScriptObject {
	protected AuthorSimple() {
	}

	public static native JsArray<Photo> getPhoto() /*-{
		return $wnd.photos;
	}-*/;

	public static native String getImageHost() /*-{
		return $wnd.imghost;
	}-*/;

	public static native JsArrayString getHosts()
	/*-{
		return $wnd.hosts;
	}-*/;
}
