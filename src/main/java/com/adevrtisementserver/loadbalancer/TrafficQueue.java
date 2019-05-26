package com.adevrtisementserver.loadbalancer;


import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.adevrtisementserver.model.Traffic;


@Component
public class TrafficQueue {
	private Logger logger = LoggerFactory.getLogger(TrafficQueue.class);

	private LinkedList<Traffic> traffics = new LinkedList<>();

	public void add(Traffic t) {
		synchronized (this) {
			try {
				traffics.add(t);
				notifyAll();
			} catch (Exception e) {
				logger.error("Traffic queing error", e);
			}
		}
	}

	public int getQueueSize() {
		synchronized (this) {
			if (traffics.isEmpty())
				return 0;
			else
				return traffics.size();
		}
	}

	public boolean isEmpty() {
		synchronized (this) {
			return traffics.isEmpty();
		}
	}

	public Traffic getNextTraffic() {
		synchronized (this) {
			while (traffics.isEmpty()) {
				try {
					wait();
				} catch (InterruptedException e) {
					logger.error("Next Traffic Getting Error", e);
				}
			}
			return traffics.poll();
		}
	}

	public void clear() {
		synchronized (this) {
			traffics.clear();
			notifyAll();
		}
	}
}
