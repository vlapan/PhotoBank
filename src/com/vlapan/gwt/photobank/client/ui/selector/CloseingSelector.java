package com.vlapan.gwt.photobank.client.ui.selector;

import com.google.gwt.user.client.ui.Widget;
import com.vlapan.gwt.photobank.client.animation.MyAnimation;
import com.vlapan.gwt.photobank.client.enums.Status;

public abstract class CloseingSelector<T extends Widget> extends Selector<T> {
	public Status status = Status.OPENED;
	protected MyAnimation animation;
	protected final int animationSpeed = 700;
	protected final int padding = 5;

	public CloseingSelector(MyAnimation animation) {
		this.animation = animation;
	}
	
	public void open() {
		open(false);
	}

	public void open(boolean force) {
		if (status == Status.OPENED && force == false)
			return;
		status = Status.OPENED;
		animation.run(this, getOpenPx(), animationSpeed);
	}

	public void close() {
		close(false);
	}

	public void close(boolean force) {
		if (status == Status.CLOSED && force == false)
			return;
		status = Status.CLOSED;
		animation.run(this, getClosePx(), animationSpeed);
	}

	public abstract int getClosePx();

	public abstract int getOpenPx();

}
