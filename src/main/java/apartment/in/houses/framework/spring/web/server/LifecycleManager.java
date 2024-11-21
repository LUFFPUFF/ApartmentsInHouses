package apartment.in.houses.framework.spring.web.server;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.startup.Tomcat;

import java.util.*;

public class LifecycleManager {
    private final Map<String, List<LifecycleListener>> listeners = new HashMap<>();

    public void addListener(String eventType, LifecycleListener listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    public void removeListener(String eventType, LifecycleListener listener) {
        List<LifecycleListener> eventListeners = listeners.get(eventType);
        if (eventListeners != null) {
            eventListeners.remove(listener);
        }
    }

    private void notifyListeners(LifecycleEvent event) {
        List<LifecycleListener> eventListeners = listeners.get(event.getType());
        if (eventListeners != null) {
            for (LifecycleListener listener : eventListeners) {
                listener.lifecycleEvent(event);
            }
        }
    }

    public void startServer(Tomcat tomcat) throws LifecycleException {
        try {
            Lifecycle lifecycle = tomcat.getHost();
            notifyListeners(new LifecycleEvent(lifecycle, Lifecycle.BEFORE_INIT_EVENT, null));
            tomcat.init();
            notifyListeners(new LifecycleEvent(lifecycle, Lifecycle.AFTER_INIT_EVENT, null));

            notifyListeners(new LifecycleEvent(lifecycle, Lifecycle.BEFORE_START_EVENT, null));
            tomcat.start();
            notifyListeners(new LifecycleEvent(lifecycle, Lifecycle.START_EVENT, null));
            notifyListeners(new LifecycleEvent(lifecycle, Lifecycle.AFTER_START_EVENT, null));

        } catch (Exception e) {
            throw new LifecycleException("Error during server startup", e);
        }
    }


    public void stopServer(Tomcat tomcat) throws LifecycleException {
        try {
            Lifecycle lifecycle = tomcat.getHost();

            notifyListeners(new LifecycleEvent(lifecycle, Lifecycle.BEFORE_STOP_EVENT, null));
            tomcat.stop();
            notifyListeners(new LifecycleEvent(lifecycle, Lifecycle.STOP_EVENT, null));
            notifyListeners(new LifecycleEvent(lifecycle, Lifecycle.AFTER_STOP_EVENT, null));

            notifyListeners(new LifecycleEvent(lifecycle, Lifecycle.BEFORE_DESTROY_EVENT, null));
            tomcat.destroy();
            notifyListeners(new LifecycleEvent(lifecycle, Lifecycle.AFTER_DESTROY_EVENT, null));

        } catch (LifecycleException e) {
            throw new LifecycleException("Error during server shutdown", e);
        }
    }
}
