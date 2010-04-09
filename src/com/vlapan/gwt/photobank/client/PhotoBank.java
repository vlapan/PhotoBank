package com.vlapan.gwt.photobank.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vlapan.gwt.photobank.client.counters.LiveInternet;
import com.vlapan.gwt.photobank.client.data.Settings;
import com.vlapan.gwt.photobank.client.data.full.AuthorFull;
import com.vlapan.gwt.photobank.client.ui.NotificationPanel;
import com.vlapan.gwt.photobank.client.ui.PhotoBrowser;
import com.vlapan.gwt.photobank.client.ui.PhotoBrowserSimple;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PhotoBank extends Widget implements EntryPoint {
	public static final NotificationPanel np = new NotificationPanel();
	public static final AbsolutePanel main = new AbsolutePanel();
	public static PhotoBrowser photoBrowser = null;
	public static final AbsolutePanel managerWidget = new AbsolutePanel();

	public void onModuleLoad() {
		Logs.getInstance().add(getClass(), "init:" + GWT.getVersion());
		if (Settings.getInstance().getDataMode().equals("full")) {
			photoBrowser = new PhotoBrowser();
		}else{
			photoBrowser = new PhotoBrowserSimple();
		}
		RootPanel.get("pb_viewPort").add(main);
		main.add(photoBrowser);
		photoBrowser.init();
		if (Settings.getInstance().getLiveInternet()) {
			main.add(new LiveInternet(), 0, 0);
		}
		if (Settings.getInstance().getTitleChange()) {
			Window.setTitle(AuthorFull.getAuthor().getTitle() + " :: "
					+ Window.getTitle());
		}
		Logs.getInstance().add(getClass(), "started");
	}
}