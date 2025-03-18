package com.mtr.utility;

import javafx.scene.paint.Color;

public class Utility {
	
	public static Color INITIAL_COLOR = Color.BLACK;
	public static Color CURRENT_LINE_COLOR = Color.RED;
	public static Color SORTED_COLOR = Color.GREEN;
	public static Color I_INDEX_COLOR = Color.CYAN;
	
	public static void sleep(int milisecond) {
		try {
            Thread.sleep(milisecond);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
	}

}
