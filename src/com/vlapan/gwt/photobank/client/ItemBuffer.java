package com.vlapan.gwt.photobank.client;

import java.util.HashMap;

import com.google.gwt.user.client.ui.Widget;

public class ItemBuffer<T extends Widget> extends HashMap<Integer, T> {
	private static final long serialVersionUID = 4437644659866066058L;
	public static final int LIMIT = 24;

	@Override
	public T put(final Integer key, final T widget) {
		if (this.size() > LIMIT) {
			this.removeFirst();
		}
		super.put(key, widget);
		return widget;
	}

	private void removeFirst() {
		for (Integer key : this.keySet()) {
			this.remove(key);
			break;
		}
	}
}
