package com.mtr;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;

public class JavaFxDemoApplication extends Application{

	private ConfigurableApplicationContext context;
	
	@Override
	public void init() throws Exception {
		ApplicationContextInitializer<GenericApplicationContext> initilizer = ac -> {
			ac.registerBean(Application.class, () -> JavaFxDemoApplication.this);
			ac.registerBean(Parameters.class, this::getParameters);
			ac.registerBean(HostServices.class, this::getHostServices);
		};
		
		this.context = new SpringApplicationBuilder()
				.sources(BootifulJavaFxApplication.class)
				.initializers(initilizer)
				.run(getParameters().getRaw().toArray(new String[0]));
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.context.publishEvent(new StageRedyEvent(primaryStage));
	}
	
	@Override
	public void stop() throws Exception {
		this.context.close();
		Platform.exit();
	}

	
	@SuppressWarnings("serial")
	class StageRedyEvent extends ApplicationEvent{

		public Stage getStage() {
			return Stage.class.cast(getSource());
		}
		
		public StageRedyEvent(Stage source) {
			super(source);
		}
	}
}
