package util;

import org.lwjgl.Sys;

public class Timer {
	/**
	 * Get the time in milliseconds.
	 * 
	 * @return time in milliseconds
	 */
	public static long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
}
