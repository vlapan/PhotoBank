package com.vlapan.gwt.photobank.client.data;

import com.google.gwt.core.client.JavaScriptObject;

public class Settings extends JavaScriptObject {
	protected Settings() {
	}

	public final static native Settings getInstance() /*-{
		return $wnd.settings;
	}-*/;

	public final native String getDataMode() /*-{
		return this.data ? this.data : "simple";
	}-*/;

	public final native boolean getVote() /*-{
		return this.vote ? this.vote : false;
	}-*/;

	public final native boolean getLiveInternet() /*-{
		return this.liveinternet ? this.liveinternet : false;
	}-*/;

	public final native boolean getTitleChange() /*-{
		return this.titlechange ? this.titlechange : false;
	}-*/;

	public final native boolean getAutoSlideShow() /*-{
		return this.autoslideshow ? this.autoslideshow : false;
	}-*/;

	public final native boolean getPhotoSelector() /*-{
		return this.photoselector ? this.photoselector : true;
	}-*/;

	public final native boolean getAlbumSelector() /*-{
		return this.albumselector ? this.albumselector : true;
	}-*/;

	public final native boolean getHidePanels() /*-{
		return this.hidepanels ? this.hidepanels : true;
	}-*/;

}
