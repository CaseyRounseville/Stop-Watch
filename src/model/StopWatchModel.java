package model;

import event.StopWatchEventSystem;
import event.RemainingChangedEvent;
import event.GoalChangedEvent;
import event.StateChangedEvent;

public class StopWatchModel {
	public static final int STATE_RUNNING		= 0,
							STATE_PAUSED		= 1,
							STATE_FINISH		= 2;
	
	private int remaining;
	private int goal;
	private long lastNS;
	private long currNS;
	private long remainingNS;
	private int state;
	
	public StopWatchModel() {
		state = STATE_PAUSED;
		setGoal(5);
		remaining = goal;
		remainingNS = goal * 1000000000L;
	}
	
	public int getRemaining() {
		return remaining;
	}
	
	public int getGoal() {
		return goal;
	}
	
	public long getLast() {
		return lastNS;
	}
	
	public long getCurr() {
		return currNS;
	}
	
	public long getRemainingNS() {
		return remainingNS;
	}
	
	public int getState() {
		return state;
	}
	
	public void setRemaining(int remaining) {
		System.out.println("setting remaining");
		this.remaining = remaining;
		StopWatchEventSystem.getInstance().dispatchEvent(new RemainingChangedEvent());
		if (remaining < 0) {
			setState(STATE_FINISH);
			//StopWatchEventSystem.getInstance().dispatchEvent(new RemainingChangedEvent());
		}
	}
	
	public void setGoal(int goal) {
		this.goal = goal;
		StopWatchEventSystem.getInstance().dispatchEvent(new GoalChangedEvent());
	}
	
	public void setLast(long lastNS) {
		this.lastNS = lastNS;
	}
	
	public void setCurr(long currNS) {
		this.currNS = currNS;
	}
	
	public void setRemainingNS(long remainingNS) {
		System.out.println("setting remaining ns" + (remainingNS / 1000000000L));
		this.remainingNS = remainingNS;
		if (remainingNS < 0 && remaining != -1) {
			setRemaining(-1);
		} else if (remainingNS < 1000000000L && remaining != 0) {
			setRemaining(0);
		} else if (remainingNS / 1000000000L < remaining) {
			setRemaining((int) (remainingNS / 1000000000L));
		}
	}
	
	public void setState(int state) {
		this.state = state;
		StopWatchEventSystem.getInstance().dispatchEvent(new StateChangedEvent());
	}
	
	public void reset() {
		remaining = goal;
		setRemainingNS(goal* 1000000000L);
		setState(STATE_PAUSED);
	}
}