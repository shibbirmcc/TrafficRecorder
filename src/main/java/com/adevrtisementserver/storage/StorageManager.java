package com.adevrtisementserver.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.adevrtisementserver.loadbalancer.TrafficQueue;
import com.adevrtisementserver.model.Traffic;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class StorageManager {
	private static Logger logger = LoggerFactory.getLogger(StorageManager.class);

	@Autowired
	private TrafficQueue queue;

	@Value("${storage.queuefile}")
	private String queuefileName;

	private ObjectMapper mapper = new ObjectMapper();

	@PostConstruct
	public void init() throws FileNotFoundException {

		if (new File(queuefileName).exists()) {
			Scanner scanner = new Scanner(new File(queuefileName), "UTF-8");

			// TODO: hasNext is not enough, need to handle EOF perfectly inside the while
			// loop,
			// may be there could be unnecessary characters or blank lines
			while (scanner.hasNext()) {
				String jsonLine = scanner.nextLine();
				try {
					Traffic traffic = mapper.readValue(jsonLine, Traffic.class);
					queue.add(traffic);
				} catch (JsonParseException jpe) {
					logger.error("Parsing Exception while reading from storage", jpe);
				} catch (JsonMappingException jme) {
					logger.error("Mapping Exception while reading from storage", jme);
				} catch (IOException jme) {
					logger.error("IO Exception: ", jme);
				}
			}
			scanner.close();

			try {
				FileChannel.open(Paths.get(queuefileName), StandardOpenOption.WRITE).truncate(0).close();
			} catch (IOException ioe) {
				logger.error("Storage cleaning error", ioe);
			}
		} else {
			logger.warn("[" + queuefileName + "] doesn't exist, can't read from storage");
		}
	}

	public void pushToQueue(Traffic t) {
		queue.add(t);
	}

	public synchronized void saveToStorage(Traffic t) {
		try {
			String jsonLine = mapper.writeValueAsString(t);
			jsonLine = jsonLine.replace("\n", "").replace("\r", "").concat("\r\n");
			FileChannel.open(Paths.get(queuefileName), StandardOpenOption.APPEND)
					.write(ByteBuffer.wrap(jsonLine.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	@PreDestroy
	public void saveRemainingQueue() {
		try {
			logger.info("Queue Flush To Storage Initiated");
			while (!queue.isEmpty()) {
				Traffic t = queue.getNextTraffic();
				try {
					String jsonLine = mapper.writeValueAsString(t);
					jsonLine = jsonLine.replace("\n", "").replace("\r", ""); // removing line breaks
					FileChannel.open(Paths.get(queuefileName), StandardOpenOption.APPEND)
							.write(ByteBuffer.wrap(jsonLine.getBytes(StandardCharsets.UTF_8)));
				} catch (Exception e) {
					// TODO: Handle exception
					e.printStackTrace();
				}
			}
			logger.info("Queue Flush To Storage Complete");

			queue.clear();
		} catch (Exception e) {
			logger.error("", e);
		}
	}

}
