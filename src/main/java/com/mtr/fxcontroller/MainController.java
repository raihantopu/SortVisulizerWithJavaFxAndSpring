package com.mtr.fxcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

@Component
public class MainController {
	private final HostServices hostServices;

	@FXML
	public Label label;

	@FXML
	public Button button;
	
	@FXML
	public HBox hbox;

	public MainController(HostServices hostServices) {
		this.hostServices = hostServices;
	}

	public void initialize() {
		this.button.setOnAction(actionEvent -> {
			this.label.setText(this.hostServices.getDocumentBase());
			this.hbox.setStyle("-fx-border-color: black; -fx-border-width: 2;");
			drawLine(10);
		});
	}
	
	private void drawLine(int size) {
		List<Line> lineList = new ArrayList<>();
		
		for(int i = 0; i < size; i++) {
			// Create a vertical line
			Line verticalLine = new Line();
			int random = getRandom();
			// Set the start (x, y) and end (x, y) points of the line
			verticalLine.setStartX(0); // x position of the line
			verticalLine.setStartY(random);  // starting y position
			verticalLine.setEndX(0);   // x position of the line (same as startX to keep it vertical)
			verticalLine.setEndY(0);   // ending y position
			
			// Optional: Set the stroke color and width
			verticalLine.setStroke(Color.BLACK);
			verticalLine.setStrokeWidth(2);
			
			lineList.add(verticalLine);
		}
        hbox.getChildren().addAll(lineList);
	}
	
	private int getRandom() {
		Random random = new Random();
        return random.nextInt(150) + 1;
	}
}
