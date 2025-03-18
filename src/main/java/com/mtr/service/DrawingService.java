package com.mtr.service;

import static com.mtr.utility.Utility.INITIAL_COLOR;
import static com.mtr.utility.Utility.SORTED_COLOR;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;

@Service
public class DrawingService {

	public void changeToSortedColor(List<Line> lines) {
        for (Line line : lines) {
            line.setStroke(SORTED_COLOR);
        }
    }
	
	public void reDraw(List<Line> lines, HBox hbox) {
	    Platform.runLater(() -> {
	    	hbox.getChildren().setAll(lines); 
	    });
	}

	public void drawLine(List<Line> lineList, HBox hbox, int size) {
		lineList.clear();
		for(int i = 0; i < size; i++) {
			Line verticalLine = new Line();
			int random = getRandom(size);
			// Set the start (x, y) and end (x, y) points of the line
			verticalLine.setStartX(0); // x position of the line
			verticalLine.setStartY(random);  // starting y position
			verticalLine.setEndX(0);   // x position of the line (same as startX to keep it vertical)
			verticalLine.setEndY(0);   // ending y position
			
			verticalLine.setId(String.valueOf(random));
			verticalLine.setStroke(INITIAL_COLOR);
			verticalLine.setStrokeWidth(3);
			
			lineList.add(verticalLine);
		}
		hbox.getChildren().clear();
        hbox.getChildren().addAll(lineList);
	}
	
	public int getRandom(int n) {
		Random random = new Random();
        return random.nextInt(n) + 1;
	}
}
