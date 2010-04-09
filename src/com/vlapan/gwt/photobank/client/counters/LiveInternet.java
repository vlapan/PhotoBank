package com.vlapan.gwt.photobank.client.counters;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Image;
import com.vlapan.gwt.photobank.client.Logs;

public class LiveInternet extends Image implements LoadHandler {
	public LiveInternet() {
		Logs.getInstance().add(getClass(), "init");
		Style style = getElement().getStyle();
		style.setProperty("filter", "alpha(opacity=1)");
		style.setProperty("opacity", "0.01");
		this.addLoadHandler(this);
		this.setUrl(LiveInternet.getUrlString());
	}

	public static native String getUrlString() /*-{
		return "http://counter.yadro.ru/hit?t26.1;r"+
		escape(document.referrer)+((typeof(screen)=="undefined")?"":
		";s"+screen.width+"*"+screen.height+"*"+(screen.colorDepth?
		screen.colorDepth:screen.pixelDepth))+";u"+escape(document.URL)+
		";"+Math.random()+"'"
	}-*/;

	public void onLoad(LoadEvent event) {
		Logs.getInstance().add(getClass(), "loaded");
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				removeFromParent();
				Logs.getInstance().add(getClass(), "removed");
			}
		});
	}
}
