package com.vlapan.gwt.photobank.client;

import java.util.HashMap;

import com.google.gwt.user.client.History;

public class HistoryManager {
	public final HashMap<String, Integer> vars = new HashMap<String, Integer>();
	private boolean innerChange = false;

	public void change(final HashMap<String, Integer> var) {
		vars.putAll(var);
		boolean first = true;
		StringBuffer token = new StringBuffer();
		for (String key : vars.keySet()) {
			if (!first)
				token.append("&");
			token.append(key + "=" + vars.get(key));
			first = false;
		}
		setInnerChange(true);
		History.newItem(token.toString());
	}

	public void check() {
		final String token = History.getToken();
		if (token.length() < 2)
			return;
		try {
			final String[] tokenVars = token.split("&");
			for (int i = 0; i < tokenVars.length; i++) {
				final String[] var = tokenVars[i].split("=");
				vars.put(var[0], Integer.valueOf(var[1]));
			}
		} catch (RuntimeException e) {
			// System.out.println("History parse failed.");
			// e.printStackTrace();
		}
	}

	public void setInnerChange(boolean innerChange) {
		this.innerChange = innerChange;
	}

	public boolean isInnerChange() {
		return innerChange;
	}
}
