package com.vlapan.gwt.photobank.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vlapan.gwt.photobank.client.Logs;
import com.vlapan.gwt.photobank.client.animation.HasOpacity;
import com.vlapan.gwt.photobank.client.animation.OpacityDecrease;
import com.vlapan.gwt.photobank.client.animation.OpacityIncrease;
import com.vlapan.gwt.photobank.client.enums.Status;
import com.vlapan.gwt.photobank.client.event.PhotoSelectEvent;
import com.vlapan.gwt.photobank.client.event.PhotoSelectHandler;
import com.vlapan.gwt.photobank.client.event.PhotoShowedEvent;
import com.vlapan.gwt.photobank.client.event.PhotoShowedHandler;

public class NotificationPanel extends Composite implements HasOpacity {
	private static NotificationPanelUiBinder uiBinder = GWT
			.create(NotificationPanelUiBinder.class);

	private final class OpenAnimation extends OpacityIncrease {
		private OpenAnimation(HasOpacity widget, double opacity) {
			super(widget, opacity);
		}

		@Override
		protected void onStart() {
			super.onStart();
			setOpacity(0);
			setVisible(true);
			getElement().getStyle().setPropertyPx("top",
					top - main.getOffsetHeight());
			status = Status.OPENING;
		}

		@Override
		protected void onCancel() {
			if (status != Status.OPENING) {
				return;
			}
			super.onCancel();
			if (autoHide) {
				setVisible(false);
			}
			status = Status.CLOSED;
		}

		@Override
		protected void onComplete() {
			super.onComplete();
			status = Status.OPENED;
			if (autoHide) {
				t.schedule(2500);
			}
		}
	}

	private final class HideAnimation extends OpacityDecrease {
		private HideAnimation(HasOpacity widget, double opacity) {
			super(widget, opacity);
		}

		@Override
		protected void onStart() {
			super.onStart();
			status = Status.CLOSING;
		}

		@Override
		protected void onCancel() {
			if (status != Status.CLOSING) {
				return;
			}
			super.onCancel();
			if (autoHide) {
				setVisible(false);
			}
			status = Status.CLOSED;
		}

		@Override
		protected void onComplete() {
			super.onComplete();
			if (autoHide) {
				setVisible(false);
			}
			status = Status.CLOSED;
			// NotificationPanel.this.setVisible(false);
		}
	}

	interface NotificationPanelUiBinder extends
			UiBinder<Widget, NotificationPanel> {
	}

	@UiField
	SimplePanel main;
	@UiField
	VerticalPanel vp;
	@UiField
	Label title;
	@UiField
	Label message;

	public Status status = Status.CLOSED;
	public Timer t = new Timer() {
		@Override
		public void run() {
			hide.run(600);
		}
	};
	public boolean autoHide = true;
	public final static double opacity = 0.6;
	public int top = 0;

	protected OpacityDecrease hide = new HideAnimation(this, opacity);
	protected OpacityIncrease show = new OpenAnimation(this, opacity);

	public NotificationPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		PhotoShowedEvent.handlers.add(new PhotoShowedHandler() {
			@Override
			public void onPhotoLoaded(PhotoWidget photo) {
				Logs.getInstance().add(getClass(), "onPhotoLoaded");
				set(photo);
				show();
			}
		});
		PhotoSelectEvent.handlers.add(new PhotoSelectHandler() {
			@Override
			public void onPhotoSelect(int photoId) {
				Logs.getInstance().add(getClass(), "onPhotoSelect");
				setVisible(false);
				stay();
			}
		});
	}

	public void set(PhotoWidget photoWidget) {
		if (photoWidget.photo.getTitle().isEmpty()
				&& photoWidget.photo.getPlace().isEmpty()) {
			status = Status.EMPTY;
			return;
		}
		status = Status.CLOSED;
		if (photoWidget.photo.getTitle().isEmpty()) {
			title.setText("***");
		} else {
			title.setText(photoWidget.photo.getTitle());
		}
		message.setText(photoWidget.photo.getPlace());
		final Style style = NotificationPanel.this.getElement().getStyle();
		main.setWidth(photoWidget.getOffsetWidth() + "px");
		style.setPropertyPx("top", photoWidget.getOffsetHeight());
		style.setPropertyPx("left", photoWidget.getAbsoluteLeft());
		top = photoWidget.getOffsetHeight();
	}

	public void show() {
		Logs.getInstance().add(getClass(), "show.status("+status+")");
		if (status == Status.OPENING || status == Status.EMPTY) {
			return;
		}
		if (status == Status.OPENED) {
			t.cancel();
			if (autoHide) {
				t.schedule(2500);
			}
			return;
		}
		status = Status.OPENING;
		show.run(200);
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		Logs.getInstance().add(getClass(), "setVisible("+visible+")");
		// if (visible == false) {
		// stay();
		// }
	}

	public void stay() {
		// Logs.getInstance().add(getClass(), "!!!!!!!!!!!!!!!");
		show.cancel();
		hide.cancel();
		t.cancel();
	}

	public void setOpacity(double opacity) {
		this.getElement().getStyle().setProperty("opacity", opacity + "");
	}
}