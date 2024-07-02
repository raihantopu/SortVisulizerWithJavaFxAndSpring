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
		Canvas canvas = new Canvas(width, height);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setStroke(Color.GREY);
		gc.setLineWidth(1);
		
		for(int x = 0; x < width; x += unit_size) {
			gc.strokeLine(x, 0, x, width);
		}
		
		for(int y = 0; y < height; y += unit_size) {
			gc.strokeLine(0, y, height, y);
		}
		
		this.anchorPane.getChildren().add(canvas);
	}
	
}
