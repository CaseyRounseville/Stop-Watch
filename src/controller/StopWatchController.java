package controller;

import model.StopWatchModel;

import event.StopWatchEventSystem;
import event.StopWatchEventHandler;
import event.StartEvent;
import event.PauseEvent;
import event.ResetEvent;
import event.StateChangedEvent;

public class StopWatchController {
	private StopWatchModel stopWatchModel;
	private TimerThread timerThread;
	
	public StopWatchController(StopWatchModel stopWatchModel, TimerThread timerThread) {
		this.stopWatchModel = stopWatchModel;
		this.timerThread = timerThread;
		
		StopWatchEventSystem.getInstance().registerEventHandler(StartEvent.class, new StopWatchEventHandler<StartEvent>(false) {
			@Override
			public void handle(StartEvent event) {
				stopWatchModel.setState(StopWatchModel.STATE_RUNNING);
				System.out.println("handling start event");
			}
		});
		
		StopWatchEventSystem.getInstance().registerEventHandler(PauseEvent.class, new StopWatchEventHandler<PauseEvent>(false) {
			@Override
			public void handle(PauseEvent event) {
				stopWatchModel.setState(StopWatchModel.STATE_PAUSED);
			}
		});
		
		StopWatchEventSystem.getInstance().registerEventHandler(ResetEvent.class, new StopWatchEventHandler<ResetEvent>(false) {
			@Override
			public void handle(ResetEvent event) {
				stopWatchModel.reset();
			}
		});
	}
}