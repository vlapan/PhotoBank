package com.vlapan.gwt.photobank.client.ui;

import java.util.HashMap;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Image;
import com.vlapan.gwt.photobank.client.data.simple.AuthorSimple;

public class PhotoBrowserSimple extends PhotoBrowser {

	public PhotoBrowserSimple() {
		super();
		albumSelector.setVisible(false);
	}

	@Override
	public void setPosition() {
		actionSelector.setPosition();
		photoSelector.setPosition();
	}

	@Override
	public void init() {
		setPosition();
		photoSelector.close(true);
		actionSelector.close(true);

		focusCatcher.setFocus(true);

		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(final ValueChangeEvent<String> event) {
				if (historyManager.isInnerChange()) {
					historyManager.setInnerChange(false);
					return;
				}
				historyManager.check();
				HashMap<String, Integer> vars = historyManager.vars;
				if (vars.isEmpty()) {
					Image.prefetch(AuthorSimple.getPhoto().get(0).getOriginalUrl());
					photoSelector.select();
				} else {
					int p = 0;
					if (vars.containsKey("p")) {
						p = vars.get("p");
					}
					Image.prefetch(AuthorSimple.getPhoto().get(p).getOriginalUrl());
					photoSelector.select(p);
				}

			}
		});
		History.fireCurrentHistoryState();
	}

	@Override
	@UiHandler("focusCatcher")
	public void onMouseMove(final MouseMoveEvent event) {
		if (event.getClientY() > photoSelector.size) {
			photoSelector.close();
		} else {
			photoSelector.open();
		}
		if (event.getClientY() >= this.getOffsetHeight() - 32) {
			actionSelector.open();
		} else {
			actionSelector.close();
		}
	}

	@Override
	@UiHandler("focusCatcher")
	public void onKeyDown(final KeyDownEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_RIGHT) {
			photoSelector.next();
		} else if (event.getNativeKeyCode() == KeyCodes.KEY_LEFT) {
			slideShow.stop();
			photoSelector.prev();
		} else if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			slideShow.trigger();
		} else if (event.getNativeKeyCode() == 187) {
			slideShow.increaseDelay();
		} else if (event.getNativeKeyCode() == 189) {
			slideShow.decreaseDelay();
		} else if (event.getNativeKeyCode() == KeyCodes.KEY_HOME) {
			photoSelector.select();
		} else if (event.getNativeKeyCode() == KeyCodes.KEY_END) {
			photoSelector.end();
		} else if (event.getNativeKeyCode() == 67) {
			// if (main.getWidget() == photoBrowser) {
			// main.setWidget(managerWidget);
			// } else {
			// main.setWidget(photoBrowser);
			// }
		}
		// System.out.println("KEY:" + event.getNativeKeyCode() +
		// ":CTRL:" + event.isControlKeyDown());
	}
}