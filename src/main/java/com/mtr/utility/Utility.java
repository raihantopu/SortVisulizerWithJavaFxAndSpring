package com.mtr.utility;

import javafx.scene.paint.Color;

public class Utility {
	
	public static Color initialColor = Color.BLACK;
	public static Color currentLineColor = Color.RED;
	public static Color sortedColor = Color.GREEN;
	public static Color iIndexColor = Color.CYAN;
	
	public static void sleep(int milisecond) {
		try {
            Thread.sleep(milisecond);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
	}

}
