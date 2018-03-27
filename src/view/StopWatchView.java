package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.VBox;

import javafx.scene.paint.Color;

import javafx.scene.input.KeyCode;

import java.util.Random;

import model.StopWatchModel;

import event.StopWatchEventSystem;
import event.StopWatchEventHandler;
import event.StateChangedEvent;
import event.StartEvent;
import event.PauseEvent;
import event.ResetEvent;
import event.GoalChangedEvent;
import event.RemainingChangedEvent;

public class StopWatchView extends VBox {
	private StopWatchModel stopWatchModel;
	
	private Label lblGoal;
	private Label lblElapsed;
	
	private Button btnStart;
	private Button btnPause;
	private Button btnReset;
	
	public StopWatchView(StopWatchModel stopWatchModel) {
		this.stopWatchModel = stopWatchModel;
		
		lblGoal = new Label("");
		lblGoal.setStyle("-fx-font-size:20pt");
		StopWatchEventSystem.getInstance().registerEventHandler(GoalChangedEvent.class, new StopWatchEventHandler<GoalChangedEvent>(false) {
			@Override
			public void handle(GoalChangedEvent event) {
				lblGoal.setText("" + stopWatchModel.getGoal());
			}
		});
		
		lblElapsed = new Label("0");
		lblElapsed.setStyle("-fx-font-size:100pt; -fx-font-weight:bold");
		StopWatchEventSystem.getInstance().registerEventHandler(RemainingChangedEvent.class, new StopWatchEventHandler<RemainingChangedEvent>(false) {
			@Override
			public void handle(RemainingChangedEvent event) {
				System.out.println("handling elapsed changed event");
				lblElapsed.setText("" + (stopWatchModel.getRemaining() + 1));
				
				Random r = new Random();
				lblElapsed.setTextFill(Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
			}
		});
		
		btnStart = new Button("Start");
		btnStart.setOnAction((event) -> {
			
			StopWatchEventSystem.getInstance().dispatchEvent(new StartEvent());
		});
		btnStart.setPrefWidth(150);
		btnStart.setPrefHeight(50);
		
		StopWatchEventSystem.getInstance().registerEventHandler(StateChangedEvent.class, new StopWatchEventHandler<StateChangedEvent>(false) {
			@Override
			public void handle(StateChangedEvent event) {
				switch (stopWatchModel.getState()) {
				case StopWatchModel.STATE_RUNNING:
					btnStart.setDisable(true);
					btnPause.setDisable(false);
					break;
				case StopWatchModel.STATE_PAUSED:
					btnStart.setDisable(false);
					btnPause.setDisable(true);
					break;
				case StopWatchModel.STATE_FINISH:
					btnStart.setDisable(true);
					btnPause.setDisable(true);
				}
			}
		});
		
		btnPause = new Button("Pause");
		btnPause.setDisable(true);
		btnPause.setOnAction((event) -> {
			btnStart.setDisable(false);
			btnPause.setDisable(true);
			StopWatchEventSystem.getInstance().dispatchEvent(new PauseEvent());
		});
		btnPause.setPrefWidth(150);
		btnPause.setPrefHeight(50);
		
		btnReset = new Button("Reset");
		btnReset.setOnAction((event) -> {
			StopWatchEventSystem.getInstance().dispatchEvent(new ResetEvent());
		});
		btnReset.setPrefWidth(150);
		btnReset.setPrefHeight(50);
		
		getChildren().add(lblGoal);
		getChildren().add(lblElapsed);
		
		getChildren().add(btnStart);
		getChildren().add(btnPause);
		getChildren().add(btnReset);
		
		setOnKeyPressed((event) -> {
			if (event.getCode().equals(KeyCode.ESCAPE)) {
				btnReset.fire();
			} else if (event.getCode().equals(KeyCode.S)) {
				btnStart.fire();
			} else if (event.getCode().equals(KeyCode.P)) {
				btnPause.fire();
			}
		});
	}
}