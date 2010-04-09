package com.vlapan.gwt.photobank.client.ui.selector;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;

public class ActionWidget extends Image implements Selectable,
		MouseOverHandler, MouseOutHandler {
	public ActionWidget(ImageResource icon) {
		super(icon);
		this.setSize("32px", "32px");
		this.addMouseOverHandler(this);
		this.addMouseOutHandler(this);
		this.blur();
	}
	
	public void blur() {
		getElement().getStyle().setProperty("filter", "alpha(opacity=50)");
		getElement().getStyle().setProperty("opacity", "0.5");
	}

	public void select() {
		getElement().getStyle().setProperty("filter", "alpha(opacity=100)");
		getElement().getStyle().setProperty("opacity", "1");
	}

	public void onMouseOver(MouseOverEvent event) {
		this.select();
	}

	public void onMouseOut(MouseOutEvent event) {
		this.blur();
	}
}