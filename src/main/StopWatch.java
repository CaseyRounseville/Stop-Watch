package main;

import javafx.application.Application;

import javafx.stage.Stage;

import javafx.scene.Scene;

import javafx.geometry.Pos;

import model.StopWatchModel;

import view.StopWatchView;

import controller.StopWatchController;
import controller.TimerThread;

public class StopWatch extends Application {
	@Override
	public void start(Stage stage) {		
		StopWatchModel stopWatchModel = new StopWatchModel();
		StopWatchView stopWatchView = new StopWatchView(stopWatchModel);
		stopWatchView.setAlignment(Pos.CENTER);
		stopWatchModel.setGoal(5);
		TimerThread timerThread = new TimerThread(stopWatchModel);
		
		Scene scene = new Scene(stopWatchView);
		stage.setScene(scene);
		
		StopWatchController stopWatchController = new StopWatchController(stopWatchModel, timerThread);
		
		stage.setWidth(800);
		stage.setHeight(450);
		
		stage.setTitle("Stop Watch");
		
		stage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(StopWatch.class, args);
	}
}