package com.adevrtisementserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.adevrtisementserver.model.Traffic;
import com.adevrtisementserver.storage.StorageManager;

@Service
public class TrafficRecordService {
	@Autowired
	private StorageManager storageManager;

	public void insertTraffic(Traffic t) {
		storageManager.pushToQueue(t);
	}

	public void saveToRetry(Traffic t) {
		storageManager.saveToStorage(t);
	}

}
