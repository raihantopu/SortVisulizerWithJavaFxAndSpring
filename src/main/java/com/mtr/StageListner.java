package com.mtr;

import java.io.IOException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.mtr.JavaFxDemoApplication.StageRedyEvent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@Component
public class StageListner implements ApplicationListener<StageRedyEvent> {
	
	private final String applictionTitle;
	private final Resource fxml;
	private final ApplicationContext ac;
	
	StageListner(
			@Value("${appliction.ui.title}") String applictionTitle,
			@Value("${application.ui.to.open}") Resource resource,
			ApplicationContext ac
			){
		this.applictionTitle = applictionTitle;
		this.fxml = resource;
		this.ac = ac;
	}

	@Override
	public void onApplicationEvent(StageRedyEvent event) {
		try {
			Stage stage = event.getStage();
			URL url = this.fxml.getURL();
			FXMLLoader fxmlLoader = new FXMLLoader(url);
			fxmlLoader.setControllerFactory(ac::getBean);
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			//stage.setResizable(false);
			stage.setTitle(this.applictionTitle);
			stage.show();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}

}
