package com.vlapan.gwt.photobank.client.data;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.vlapan.gwt.photobank.client.data.full.AuthorFull;
import com.vlapan.gwt.photobank.client.data.simple.AuthorSimple;

public class Data {
	public static int albumSelected = 0;
	public static int photoSelected = 0;

	public static JsArray<Photo> getPhotos() {
		JsArray<Photo> photos = null;
		if (Settings.getInstance().getDataMode().equals("full")) {
			photos = AuthorFull.getAuthor().getAlbums().get(albumSelected)
					.getPhotos();
		} else {
			photos = AuthorSimple.getPhoto();
		}
		return photos;
	}

	public final static JsArrayString getHosts() {
		JsArrayString hosts = null;
		if (Settings.getInstance().getDataMode().equals("full")) {
			hosts = AuthorFull.getAuthor().getHosts();
		} else {
			hosts = AuthorSimple.getHosts();
		}
		return hosts;
	}
	public final static String getImageHost(){
		String imageHost = null;
		if (Settings.getInstance().getDataMode().equals("full")) {
			imageHost = AuthorFull.getAuthor().getImageHost();
		} else {
			imageHost = AuthorSimple.getImageHost();
		}
		return imageHost;
	}
}
