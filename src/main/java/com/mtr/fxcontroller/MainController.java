package com.mtr.fxcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import javafx.application.HostServices;
import javafx.application.Platform;
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
	public Button sort;
	
	@FXML
	public HBox hbox;
	
	private final Color initialColor = Color.RED;
	private final Color currentLineColor = Color.BLACK;
	private final Color sortedColor = Color.GREEN;
	
	List<Line> lineList = new ArrayList<>();

	public MainController(HostServices hostServices) {
		this.hostServices = hostServices;
	}

	public void initialize() {
		this.button.setOnAction(actionEvent -> {
			this.label.setText(this.hostServices.getDocumentBase());
			this.hbox.setStyle("-fx-border-color: black; -fx-border-width: 2;");
			drawLine(30);
		});
		
		this.sort.setOnAction(actionEvent -> {
			System.out.println("Starting sorting.");
			new Thread(() -> {
				sort(lineList);
			}).start();
			
			this.hbox.getChildren().clear();
			this.hbox.getChildren().addAll(lineList);
			System.out.println("Sorting done.");
		});
	}

	private List<Line> sort(List<Line> lines) {
		int n = lines.size() - 1;
		for(int i = 0; i < n-1; i++) {
			lines.get(i).setStroke(this.currentLineColor);
			Platform.runLater(() -> reDraw(lines));
			sleep();
			for(int j = 0; j < n-i; j++) {
				lines.get(j).setStroke(this.currentLineColor);
				if( Integer.parseInt(lines.get(j).getId()) > Integer.parseInt(lines.get(j+1).getId()) ) {
					Line temp = lines.get(j);
					lines.set(j, lines.get(j+1));
					lines.get(j+1).setStroke(this.sortedColor);
					lines.set(j+1, temp);
					Platform.runLater(() -> reDraw(lines));
					sleep();
				}
				lines.get(j).setStroke(this.sortedColor);
			}
			lines.get(i).setStroke(this.sortedColor);
		}
		return lines;
	}
	
	private void sleep() {
		try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
	}
	
	private void reDraw(List<Line> lines) {
		this.hbox.getChildren().clear();
		this.hbox.getChildren().addAll(lines);
	}
	
	private void drawLine(int size) {
		this.lineList.clear();
		for(int i = 0; i < size; i++) {
			Line verticalLine = new Line();
			int random = getRandom();
			// Set the start (x, y) and end (x, y) points of the line
			verticalLine.setStartX(0); // x position of the line
			verticalLine.setStartY(random);  // starting y position
			verticalLine.setEndX(0);   // x position of the line (same as startX to keep it vertical)
			verticalLine.setEndY(0);   // ending y position
			
			verticalLine.setId(String.valueOf(random));
			verticalLine.setStroke(this.initialColor);
			verticalLine.setStrokeWidth(3);
			
			lineList.add(verticalLine);
		}
		hbox.getChildren().clear();
        hbox.getChildren().addAll(lineList);
	}
	
	private int getRandom() {
		Random random = new Random();
        return random.nextInt(150) + 1;
	}
}
