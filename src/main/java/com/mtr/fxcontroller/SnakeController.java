package com.mtr.fxcontroller;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

@Component
public class SnakeController {
	
	@FXML
	public AnchorPane anchorPane;

	private int width = 600;
	private int height= 600;
	private int unit_size = 25;
	
	public void initialize() {
		int x = 10;
		int pow = 2;
		
		Canvas canvas = new Canvas(width, height);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setStroke(Color.GREY);
		gc.setLineWidth(1);
		
		for(int i = 0; i <= width/unit_size; i ++) {
			gc.strokeLine(i, 0, i, unit_size);
		}
		
		this.anchorPane.getChildren().add(canvas);
	}
	
	private int pow(int base, int p) {
		if(p != 0) {
			return pow(base  base);
		}
	}
}
