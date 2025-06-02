module com.mtr {
	// JavaFX modules
	requires javafx.controls;
	requires javafx.fxml;

	// Spring modules
	requires spring.boot;
	requires spring.boot.autoconfigure;
	requires spring.context;
	requires spring.beans;
	requires spring.core;

	exports com.mtr; 
	exports com.mtr.service;
	exports com.mtr.fxcontroller; 
	
	opens com.mtr to spring.core;
	opens com.mtr.service to spring.beans;
	opens com.mtr.fxcontroller to javafx.fxml, spring.core, spring.beans; // 👈 This is critical for reflection-based instantiation
}
