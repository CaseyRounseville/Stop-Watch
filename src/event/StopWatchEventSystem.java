package event;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import log.Log;

public class StopWatchEventSystem {
	private static final StopWatchEventSystem instance = new StopWatchEventSystem();
	
	private Map<Class<? extends StopWatchEvent>, List<StopWatchEventHandler<? extends StopWatchEvent>>> eventTypeToHandlerList;
	
	private StopWatchEventSystem() {
		eventTypeToHandlerList = new HashMap<Class<? extends StopWatchEvent>, List<StopWatchEventHandler<? extends StopWatchEvent>>>();
	}
	
	public static StopWatchEventSystem getInstance() {
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends StopWatchEvent> void dispatchEvent(T event) {
		if (!eventTypeToHandlerList.containsKey(event.getClass())) {
			Log.out.println("StopWatchEventSystem: dispatched event " + event.getClass() + ", but nobody handled it");
			return;
		}
		
		Log.out.println("StopWatchEventSystem: handling event " + event.getClass());
		
		List<StopWatchEventHandler<? extends StopWatchEvent>> handlers = eventTypeToHandlerList.get(event.getClass());
		
		for (int i = 0; i < handlers.size(); i++) {
			StopWatchEventHandler<? extends StopWatchEvent> h = handlers.get(i);
			((StopWatchEventHandler<T>)h).handle(event);
			if (h.isOneShot()) {
				handlers.remove(i--);
			}
		}
		
		if (handlers.isEmpty()) {
			eventTypeToHandlerList.remove(event.getClass());
		}
	}
	
	public <T extends StopWatchEvent> void registerEventHandler(Class<T> c, StopWatchEventHandler<T> h) {
		if (!eventTypeToHandlerList.containsKey(c)) {
			eventTypeToHandlerList.put(c, new ArrayList<StopWatchEventHandler<? extends StopWatchEvent>>());
		}
		
		Log.out.println("StopWatchEventSystem: registering handler " + h.getClass().getName() + " for event " + c.getName());
		
		eventTypeToHandlerList.get(c).add(h);
	}
	
	public <T extends StopWatchEvent> void unregisterEventHandler(Class<T> c, StopWatchEventHandler<T> h) {
		if (!eventTypeToHandlerList.containsKey(c)) {
			Log.err.println("StopWatchEventSystem: TRIED TO UNREGISTER " + c.getName() + " EVENT HANDLER, BUT IT WAS NOT REGISTERED");
			return;
		}
		
		Log.out.println("StopWatchEventSystem: unregistering handler " + h.getClass().getName() + " for event " + c.getName());
		
		eventTypeToHandlerList.get(c).remove(h);
	}
}