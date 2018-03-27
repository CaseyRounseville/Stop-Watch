package controller;

import javafx.application.Platform;

import event.StopWatchEventSystem;
import event.FinishEvent;

import model.StopWatchModel;

import log.Log;

public class TimerThread extends Thread {
	private StopWatchModel stopWatchModel;
	
	private Runnable runnable;
	
	private boolean running;
	
	public TimerThread(StopWatchModel stopWatchModel) {
		this.stopWatchModel = stopWatchModel;
		running = true;
		
		runnable = () -> {
			long last = 0;
			long curr = 0;
			long elapsed = 0;
			
			if (stopWatchModel.getState() == StopWatchModel.STATE_PAUSED || stopWatchModel.getState() == StopWatchModel.STATE_FINISH) {
				last = System.nanoTime();
				stopWatchModel.setLast(last);
				//curr = last;
				return;
			} else {
				last = stopWatchModel.getLast();
				curr =  System.nanoTime();
				elapsed = curr - last;
			}
			
			System.out.println("elapsed: " + elapsed);
			
			stopWatchModel.setRemainingNS(stopWatchModel.getRemainingNS() - elapsed);
			stopWatchModel.setLast(curr);
		};
		
		start();
	}

	@Override
	public void run() {
		System.out.println("timer thread running");
		while (running) {
			Platform.runLater(runnable);
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				Log.err.println(e);
			}
		}
	}
}