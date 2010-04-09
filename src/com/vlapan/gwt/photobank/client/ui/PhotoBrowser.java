package com.vlapan.gwt.photobank.client.ui;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.vlapan.gwt.photobank.client.HistoryManager;
import com.vlapan.gwt.photobank.client.PhotoBank;
import com.vlapan.gwt.photobank.client.SlideShow;
import com.vlapan.gwt.photobank.client.data.Data;
import com.vlapan.gwt.photobank.client.data.full.AuthorFull;
import com.vlapan.gwt.photobank.client.ui.selector.ActionSelector;
import com.vlapan.gwt.photobank.client.ui.selector.AlbumSelector;
import com.vlapan.gwt.photobank.client.ui.selector.AlbumWidget;
import com.vlapan.gwt.photobank.client.ui.selector.PhotoSelector;

public class PhotoBrowser extends Composite implements MouseMoveHandler,
		KeyDownHandler {

	private static PhotoBrowserUiBinder uiBinder = GWT
			.create(PhotoBrowserUiBinder.class);

	interface PhotoBrowserUiBinder extends UiBinder<Widget, PhotoBrowser> {
	}

	public Timer resize = new Timer() {
		@Override
		public void run() {
			albumSelector.setPosition();
			photoSelector.setPosition();
			albumSelector.delaySelect = false;
			albumSelector.select(Data.albumSelected, Data.photoSelected);
			albumSelector.setVisible(true);
			albumSelector.close(true);
			actionSelector.setVisible(true);
			actionSelector.close(true);
			photoSelector.setVisible(true);
			photoSelector.close(true);
		}
	};

	@UiField
	AbsolutePanel main;
	@UiField
	public ActionSelector actionSelector;
	@UiField
	public AlbumSelector albumSelector;
	@UiField
	public PhotoSelector photoSelector;
	@UiField
	public FocusPanel focusCatcher;
	@UiField
	public PhotoView photoView;

	public SlideShow slideShow;
	public final static HistoryManager historyManager = new HistoryManager();

	public PhotoBrowser() {
		initWidget(uiBinder.createAndBindUi(this));
		slideShow = new SlideShow(photoSelector);
		main.setWidgetPosition(albumSelector, 0, 0);
		main.setWidgetPosition(actionSelector, 0, 0);
		main.setWidgetPosition(photoSelector, 0, 0);
		main.add(new Logotype(AuthorFull.getAuthor().getTitle()), 0, 0);
	}

	public void setPosition() {
		actionSelector.setPosition();
		photoSelector.setPosition();
		albumSelector.setPosition();
	}

	public void init() {
		setPosition();
		albumSelector.close(true);
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
					Image.prefetch(AuthorFull.getAuthor().getAlbums().get(0)
							.getPhotos().get(0).getOriginalUrl());
					albumSelector.select();
				} else {
					int a = 0;
					int p = 0;
					if (vars.containsKey("a")) {
						a = vars.get("a");
					}
					if (vars.containsKey("p")) {
						p = vars.get("p");
					}
					Image.prefetch(AuthorFull.getAuthor().getAlbums().get(a)
							.getPhotos().get(p).getOriginalUrl());
					albumSelector.select(a, p);
				}

			}
		});
		History.fireCurrentHistoryState();
		Window.addResizeHandler(new ResizeHandler() {
			public void onResize(ResizeEvent event) {
				PhotoBank.np.setVisible(false);
				albumSelector.setVisible(false);
				actionSelector.setVisible(false);
				photoSelector.setVisible(false);
				photoView.photo.setVisible(false);
				photoView.setLoad(true);
				resize.schedule(1000);
			}
		});
	}

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
		if (event.getClientX() > albumSelector.size) {
			albumSelector.close();
		} else {
			albumSelector.close.cancel();
			albumSelector.open();
		}
	}

	@UiHandler("focusCatcher")
	public void onKeyDown(final KeyDownEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_RIGHT) {
			photoSelector.next();
		} else if (event.getNativeKeyCode() == KeyCodes.KEY_LEFT) {
			slideShow.stop();
			photoSelector.prev();
		} else if (event.getNativeKeyCode() == KeyCodes.KEY_UP) {
			slideShow.stop();
			albumSelector.prev();
			albumSelector.touch();
		} else if (event.getNativeKeyCode() == KeyCodes.KEY_DOWN) {
			slideShow.stop();
			albumSelector.next();
			albumSelector.touch();
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