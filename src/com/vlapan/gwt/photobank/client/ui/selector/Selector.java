package com.vlapan.gwt.photobank.client.ui.selector;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vlapan.gwt.photobank.client.Logs;
import com.vlapan.gwt.photobank.client.PhotoBank;

public abstract class Selector<T extends Widget> extends FlowPanel {
	public enum Orientation {
		VERTICAL, HORIZONTAL
	};

	private Orientation orientation = Orientation.HORIZONTAL;
	public int show = 0;
	public final static int sizeMax = 75;
	public final static int sizeStep = 25;
	public int size = sizeMax;
	private T selected;

	public Selector() {
		Style style = this.getElement().getStyle();
		style.setZIndex(255);
	}

	public abstract void setPosition();

	public abstract void next();

	public abstract void prev();

	public void setSelected(T selected) {
		this.selected = selected;
	}

	public T getSelected() {
		return selected;
	}

	public int countShow(int size) {
		Logs.getInstance().add(getClass(), "countShow( size = " + size + " )");
		final int area = getOrientation() == Orientation.HORIZONTAL ? PhotoBank.photoBrowser
				.getOffsetWidth()
				: PhotoBank.photoBrowser.getOffsetHeight();
		int show = (area - (size * 2)) / size;
		if (show % 2 == 0) {
			show--;
		}
		return show > 0 ? show : 0;
	}

	public void setShow() {
		setShow(-1);
	}

	public void setShow(int max) {
		Logs.getInstance().add(getClass(), "setShow( max = " + max + " )");
		int showTmp = countShow(sizeMax);
		if (showTmp >= 5) {
			size = sizeMax;
			show = showTmp;
		} else {
			showTmp = countShow(sizeMax - sizeStep);
			if (showTmp >= 3) {
				size = sizeMax - sizeStep;
				show = showTmp;
			} else {
				size = sizeMax - sizeStep * 2;
				show = countShow(sizeMax - sizeStep * 2);
			}
		}
		if (max > 0 && max < show) {
			show = max;
		}
		final int width = getOrientation() == Orientation.HORIZONTAL ? show * size
				: size;
		final int height = getOrientation() == Orientation.HORIZONTAL ? size : show
				* size;
		this.setHeight(height + "px");
		this.setWidth(width + "px");
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	public Orientation getOrientation() {
		return orientation;
	}
}
