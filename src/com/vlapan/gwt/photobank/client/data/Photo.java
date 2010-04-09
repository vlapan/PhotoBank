package com.vlapan.gwt.photobank.client.data;

import com.google.gwt.core.client.JavaScriptObject;

public class Photo extends JavaScriptObject {
	protected Photo() {
	}

	public final native String getTitle() /*-{
		return this.title;
	}-*/;

	public final native int getId() /*-{
		return this.id;
	}-*/;

	private final String getFolder(int id) {
		double folder = Math.floor(this.getId() / (double) 10000);
		return (int) folder + "";
	}

	private final String getUrlPreviewSuffix() {
		return Data.getImageHost() + this.getFolder(this.getId())
				+ "/" + this.getId();
	}

	public final String getUrlPreview500() {
		return this.getUrlPreviewSuffix() + "s_.jpg";
	}

	public final String getUrlPreview240() {
		return this.getUrlPreviewSuffix() + "s.jpg";
	}

	public final String getUrlPreview75() {
		return this.getUrlPreviewSuffix() + "r.jpg";
	}

	public final String getOriginalUrl() {
		return Data.getHosts().get(getSrcHost())
				+ this.getFileName();
	}

	private final native int getSrcHost() /*-{
		return this.srcHost ? this.srcHost : 0;
	}-*/;

	public final native String getFileName() /*-{
		return this.file;
	}-*/;

	public final native int getVotesCount() /*-{
		return this.votes;
	}-*/;

	public final native String getPlace() /*-{
		return this.place;
	}-*/;

}
