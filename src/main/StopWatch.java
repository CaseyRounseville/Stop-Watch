package main;

import javafx.application.Application;

import javafx.stage.Stage;

import javafx.scene.Scene;

import javafx.scene.Group;

public class StopWatch extends Application {
	@Override
	public void start(Stage stage) {
		Group root = new Group();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(StopWatch.class, args);
	}
}