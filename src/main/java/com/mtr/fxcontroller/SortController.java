package com.mtr.fxcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mtr.service.DrawingService;
import com.mtr.service.SortService;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;

@Component
public class SortController {
	
	@Value("${application.algorithms}")
	private String[] algorithms;
	@FXML
	public Button btnDraw;
	@FXML
	public Button btnSort;
	
	@FXML
	public HBox hbox;
	
	@FXML
	public ChoiceBox<String> choiceBox;
	
	@FXML
	public Slider slider;
	@FXML
	public Slider speedSlider;
	
	@FXML
	public Label counter;
	@FXML
	public Label visualSpeed;
	
	List<Line> lineList = new ArrayList<>();

	@Autowired
	private SortService sortService;
	
	@Autowired
	private DrawingService drawingService;

	public void initialize() {
		//populate choice box to choose the algorithm
		//String[] algorithms = {"Bubble Sort", "Selection Sort", "Insertion Sort", "Merge Sort"};
		this.choiceBox.getItems().addAll(algorithms);
		this.choiceBox.setValue("Choose Algorithm");
		
		//Counter Slider properties
		this.slider.setMin(1);
		this.slider.setMax(150);
		this.slider.setValue(30);
		this.slider.setShowTickLabels(true);
		this.slider.setShowTickMarks(true);
		this.counter.setText(String.valueOf((int)this.slider.getValue()));
		
		//Visual Speed slider
		this.speedSlider.setMin(1);
		this.speedSlider.setMax(300);
		this.speedSlider.setValue(50);
		this.speedSlider.setShowTickLabels(true);
		this.speedSlider.setShowTickMarks(true);
		visualSpeed.setText(String.valueOf((int) speedSlider.getValue()) + "ms");
		
		//initially draw default line with default size
		drawingService.drawLine(lineList, hbox, (int) this.slider.getValue());
		
		/////////these are all action methods/////////////
		//counter slider action method
		this.slider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				drawingService.drawLine(lineList, hbox, (int) slider.getValue());
				counter.setText(String.valueOf((int)slider.getValue()));
			}
		});
		
		//visual slider action method
		this.speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				visualSpeed.setText(String.valueOf((int) speedSlider.getValue()) + "ms");
			}
		});
		
		//DrawButton action
		this.btnDraw.setOnAction(actionEvent -> {
			//this.hbox.setStyle("-fx-border-color: black; -fx-border-width: 2;");
			this.drawingService.drawLine(this.lineList, this.hbox, (int) this.slider.getValue());
		});
		
		//Sorting function running with a new thread otherwise FX line drawing will be block
		this.btnSort.setOnAction(actionEvent -> {
			String value = this.choiceBox.getValue();
			System.out.println(value);
			if (value.equalsIgnoreCase("Choose Algorithm")) {
			    new Alert(AlertType.INFORMATION, "Please Select A Sorting Algorithm").showAndWait();
			} else {
			    new Thread(() -> {
			        if (value.equalsIgnoreCase("Bubble Sort")) {
			            sortService.bubbleSort(this.lineList, this.hbox, (int) speedSlider.getValue());
			        } else if (value.equalsIgnoreCase("Selection Sort")) {
			            sortService.selectionSort(this.lineList, this.hbox, (int) speedSlider.getValue());
			        } else if (value.equalsIgnoreCase("Insertion Sort")) {
			            sortService.insertionSort(this.lineList, this.hbox, (int) speedSlider.getValue());
			        } else if (value.equalsIgnoreCase("Merge Sort")) {
			            sortService.mergeSort(this.lineList, this.hbox, (int) speedSlider.getValue());
			        }
			    }).start();
			}
		});
	}

	
	
}
