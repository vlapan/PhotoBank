package com.vlapan.gwt.photobank.client;

/**
 * @author vlapan
 * 
 */
public class Logs {
	private static class LogsHolder {
		private static final Logs INSTANCE = new Logs();
	}

	/**
	 * @return Logs
	 */
	public static Logs getInstance() {
		return LogsHolder.INSTANCE;
	}

	/**
	 * Private constructor prevents instantiation from other classes
	 */
	private Logs() {
	}

	/**
	 * @param classObj
	 * @param message
	 *            Print logs message to out
	 */
	public void add(final Class<?> classObj, final String message) {
		System.out.println(classObj.getName() + ":" + message);
	}
}
