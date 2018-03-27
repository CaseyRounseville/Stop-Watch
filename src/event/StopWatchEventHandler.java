package event;

public abstract class StopWatchEventHandler<T extends StopWatchEvent> {
	private boolean oneShot;
	
	public StopWatchEventHandler() {
		oneShot = false;
	}
	
	public StopWatchEventHandler(boolean oneShot) {
		this.oneShot = oneShot;
	}
	
	public boolean isOneShot() {
		return oneShot;
	}
	
	public abstract void handle(T t);
}