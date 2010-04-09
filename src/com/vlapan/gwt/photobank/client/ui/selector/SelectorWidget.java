package com.vlapan.gwt.photobank.client.ui.selector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.vlapan.gwt.photobank.client.enums.Status;

public class SelectorWidget extends Composite implements Selectable {
	private static SelectorWidgetUiBinder uiBinder = GWT
			.create(SelectorWidgetUiBinder.class);

	interface SelectorWidgetUiBinder extends UiBinder<Widget, SelectorWidget> {
	}

	@UiField
	AbsolutePanel main;
	@UiField
	public Image image;
	@UiField
	Label label;

	public Status status = Status.READY;
	public int index;
	public static final int width = 75;
	public static final int height = 75;

	public SelectorWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		Style style = this.getElement().getStyle();
		style.setProperty("display", "inline");
		this.blur();
		this.setSize(width + "px", height + "px");
		image.setSize(width + "px", height + "px");
	}

	public void setIndex(int index, boolean last) {
		this.index = index;
		label.setText((index + 1) + "");
		Style style = label.getElement().getStyle();
		if (index == 0)
			style.setProperty("color", "#0f0");
		else if (last)
			style.setProperty("color", "#f00");
		else
			style.setProperty("color", "#fff");
		style.setProperty("fontSize", "10px");
		main.setWidgetPosition(label, 2, 0);
	}

	public void select() {
		Style style = getElement().getStyle();
		style.setProperty("filter", "alpha(opacity=100)");
		style.setProperty("opacity", "1");
	}

	public void blur() {
		Style style = getElement().getStyle();
		style.setProperty("filter", "alpha(opacity=40)");
		style.setProperty("opacity", "0.4");
	}
}
