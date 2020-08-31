package com.adevrtisementserver.loadbalancer;

import java.net.ConnectException;
import java.sql.DataTruncation;
import java.sql.SQLIntegrityConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.adevrtisementserver.model.Traffic;
import com.adevrtisementserver.service.TrafficRecordService;

@Component
public class DBInsertUpdateLoadBalancer {

	private Logger logger = LoggerFactory.getLogger(DBInsertUpdateLoadBalancer.class);

	private JdbcTemplate jdbcTemplate;
	private TrafficQueue trafficQueue;
	private TrafficRecordService recordService;

	@Autowired
	public DBInsertUpdateLoadBalancer(JdbcTemplate jdbcTemplate, TrafficQueue trafficQueue,
			TrafficRecordService recordService) {
		this.jdbcTemplate = jdbcTemplate;
		this.trafficQueue = trafficQueue;
		this.recordService = recordService;
	}

	@Scheduled(fixedDelay = 10)
	public void insertToDBFromQueue() {
		Traffic traffic = null;
		try {
			traffic = trafficQueue.getNextTraffic();
			jdbcTemplate.update(
					"INSERT INTO publisherTraffic(publisherID, campaignID, ipAddress, trafficReceivingParamValue, trafficSendingParamValue, trafficReceivingDate, trafficReceivingTime) "
							+ "VALUES(" + traffic.getPublisherID() + ", " + traffic.getCampaignID() + ", '"
							+ traffic.getIpAddress() + "', '" + traffic.getTrafficReceivingParamValue() + "', '"
							+ traffic.getTrafficSendingParamValue() + "'," + " '" + traffic.getTrafficReceivingDate()
							+ "' ," + " '" + traffic.getTrafficReceivingTime() + "')");
		} catch (NullPointerException npe) {
			logger.error("NullPointer ", npe);
		} catch (DataAccessException dae) {
			if (dae.contains(DataTruncation.class)) {
				logger.warn("Data Truncation Error Found", dae);
			} else if (dae.contains(SQLIntegrityConstraintViolationException.class)) {
				logger.warn("Duplicate Entry Error Found", dae);
			} else if (dae.contains(ConnectException.class)) {
				logger.warn("Connection Error Found, saving Traffic", dae);
				if (traffic != null) {
					recordService.saveToRetry(traffic);
				}
			}
		}
	}

}
