package com.mtr.fxcontroller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@Component
public class MainController {
	@Autowired
	private ApplicationContext context;

	@FXML
	private void openSnakeGame() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/snake.fxml"));
			loader.setControllerFactory(context::getBean);
			Parent root = loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Sort Visualizer");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void openSortVisualizer() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/sortVisualizer.fxml"));
			loader.setControllerFactory(context::getBean);
			Parent root = loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Sort Visualizer");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}