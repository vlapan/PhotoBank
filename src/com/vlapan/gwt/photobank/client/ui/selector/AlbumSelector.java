package com.vlapan.gwt.photobank.client.ui.selector;

import java.util.HashMap;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.vlapan.gwt.photobank.client.ItemBuffer;
import com.vlapan.gwt.photobank.client.Logs;
import com.vlapan.gwt.photobank.client.PhotoBank;
import com.vlapan.gwt.photobank.client.animation.SlideHorizontal;
import com.vlapan.gwt.photobank.client.data.Data;
import com.vlapan.gwt.photobank.client.data.full.AlbumFull;
import com.vlapan.gwt.photobank.client.data.full.AuthorFull;
import com.vlapan.gwt.photobank.client.enums.Status;
import com.vlapan.gwt.photobank.client.ui.PhotoBrowser;

public class AlbumSelector extends CloseingSelector<AlbumWidget> {
	public ItemBuffer<AlbumWidget> buffer = new ItemBuffer<AlbumWidget>();

	public Timer close = new Timer() {
		@Override
		public void run() {
			close();
		}
	};
	public boolean delaySelect = false;
	public Timer photoSelect = new Timer() {
		@Override
		public void run() {
			PhotoBank.photoBrowser.photoSelector.setPosition();
			if (AuthorFull.getAuthor().getAlbums().get(getSelected().index)
					.getPhotos().length() < PhotoBank.photoBrowser.photoSelector.show) {
				PhotoBank.photoBrowser.photoSelector.setPosition(AuthorFull
						.getAuthor().getAlbums().get(getSelected().index)
						.getPhotos().length());
			}
			PhotoBank.photoBrowser.photoSelector.buffer.clear();
			PhotoBank.photoBrowser.photoSelector
					.select(getSelected().currentPhoto);
		}
	};

	public class AlbumProcessor implements Command {
		int albumIndex = 0;
		int photoIndex = 0;
		int start = 0;
		int stop = 0;
		JsArray<AlbumFull> albums = AuthorFull.getAuthor().getAlbums();

		public void set(int albumIndex, int photoIndex) {
			this.albumIndex = albumIndex;
			this.photoIndex = photoIndex;
			start = albumIndex - ((show - 1) / 2) > 0 ? albumIndex
					- ((show - 1) / 2) : 0;
			if (start + show < AuthorFull.getAuthor().getAlbums().length()) {
				stop = start + show;
			} else {
				stop = albums.length();
				if (albums.length() - show > 0)
					start = albums.length() - show;
				else
					start = 0;
			}
			Logs.getInstance().add(getClass(),
					"select:" + albumIndex + "(" + start + "-" + stop + ")");
		}

		public void execute() {
			AlbumSelector.this.clear();
			for (int i = start; i < stop; i++) {
				AlbumWidget albumWidget;
				if (buffer.containsKey(i)) {
					albumWidget = buffer.get(i);
					add(albumWidget);
				} else {
					albumWidget = generateWidget(i);
				}
				if (i == albumIndex) {
					Data.albumSelected = albumIndex;
					setSelected(albumWidget);
					albumWidget.select();
					if (photoIndex > -1) {
						albumWidget.currentPhoto = photoIndex;
					} else {
						albumWidget.currentPhoto = 0;
					}
					Image.prefetch(albums.get(i).getPhotos().get(
							albumWidget.currentPhoto).getOriginalUrl());
					if (stop + 1 < albums.length()) {
						Image.prefetch(albums.get(albumIndex).getPhotos().get(
								stop + 1).getUrlPreview75());
					}
					if (!delaySelect) {
						photoSelect.run();
					} else {
						photoSelect.schedule(700);
					}
					delaySelect = true;
				} else {
					albumWidget.blur();
				}
			}
		}
	}

	public AlbumSelector() {
		super(new SlideHorizontal());
		this.setWidth("75px");
		this.setOrientation(Orientation.VERTICAL);
	}

	@Override
	public void setPosition() {
		setPosition(-1);
	}

	public void setPosition(int maxItems) {
		setShow(maxItems);
		final int left = status == Status.OPENED ? getOpenPx() : getClosePx();
		final int top = PhotoBank.photoBrowser.getOffsetHeight() / 2
				- (show * size / 2);
		this.getElement().getStyle().setPropertyPx("top", top);
		this.getElement().getStyle().setPropertyPx("left", left);
	}

	public void select() {
		select(0, 0);
	}

	public void select(int index) {
		select(index, -1);
	}

	public void select(int albumIndex, int photoIndex) {
		photoSelect.cancel();
		PhotoBank.photoBrowser.photoView.setLoad(true);
//		PhotoBank.np.stay();
//		PhotoBank.np.setVisible(false);
//		PhotoBank.np.status = NotificationPanel.Status.CLOSED;
		final HashMap<String, Integer> var = new HashMap<String, Integer>();
		var.put("a", albumIndex);
		PhotoBrowser.historyManager.change(var);
		if (AuthorFull.getAuthor().getAlbums().length() < show) {
			setPosition(AuthorFull.getAuthor().getAlbums().length());
		}
		AlbumProcessor albumProcessor = new AlbumProcessor();
		albumProcessor.set(albumIndex, photoIndex);
		DeferredCommand.addCommand(albumProcessor);
		PhotoBank.photoBrowser.focusCatcher.setFocus(true);
	}

	public AlbumWidget generateWidget(int index) {
		AlbumFull album = AuthorFull.getAuthor().getAlbums().get(index);
		AlbumWidget albumWidget = new AlbumWidget();
		add(albumWidget);
		albumWidget.setAlbum(album);
		final boolean last = index == AuthorFull.getAuthor().getAlbums().length() - 1 ? true
				: false;
		albumWidget.setIndex(index, last);
		albumWidget.setSize(size + "px", size + "px");
		albumWidget.image.setSize(size + "px", size + "px");
		buffer.put(index, albumWidget);
		return albumWidget;
	}

	@Override
	public void next() {
		if (this.getSelected() == null)
			return;
		final int i = this.getSelected().index;
		if (i == AuthorFull.getAuthor().getAlbums().length() - 1)
			return;
		final int nextIndex = i + 1;
		select(nextIndex);
	}

	@Override
	public void prev() {
		if (this.getSelected() == null)
			return;
		final int i = this.getSelected().index;
		if (i == 0)
			return;
		final int prevIndex = i - 1;
		select(prevIndex);
	}

	public void touch() {
		close.cancel();
		this.open();
		close.schedule(2000);
	}

	public void add(AlbumWidget album) {
		super.add(album);
	}

	@Override
	public int getClosePx() {
		return padding - this.getOffsetWidth();
	}

	@Override
	public int getOpenPx() {
		return 0;
	}
}
