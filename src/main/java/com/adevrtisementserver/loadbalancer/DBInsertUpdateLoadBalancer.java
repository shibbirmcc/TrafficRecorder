package com.adevrtisementserver.loadbalancer;

import java.net.ConnectException;
import java.sql.DataTruncation;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.adevrtisementserver.model.Traffic;
import com.adevrtisementserver.service.TrafficRecordService;

@Component
public class DBInsertUpdateLoadBalancer implements Runnable{

	private Logger logger = LoggerFactory.getLogger(DBInsertUpdateLoadBalancer.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private TrafficQueue trafficQueue;
	@Autowired
	private TrafficRecordService recordService;
	
	private Thread trafficInsertThread;
	private AtomicBoolean run = new AtomicBoolean();

	@PostConstruct
	public void init() {
		run.set(true);
		trafficInsertThread = new Thread(this);
		trafficInsertThread.start();
	}
	
	@Override
	public void run() {
		while (run.get()) {
			Traffic traffic = null;
			try {
				traffic = trafficQueue.getNextTraffic();
				jdbcTemplate.update(
						"INSERT INTO publisherTraffic(publisherID, campaignID, ipAddress, trafficReceivingParamValue, trafficSendingParamValue, trafficReceivingDate, trafficReceivingTime, campaignType) "
								+ "VALUES(" + traffic.getPublisherID() + ", " + traffic.getCampaignID() + ", '"
								+ traffic.getIpAddress() + "', '" + traffic.getTrafficReceivingParamValue() + "', '"
								+ traffic.getTrafficSendingParamValue() + "'," + " '"
								+ traffic.getTrafficReceivingDate() + "' ," + " '" + traffic.getTrafficReceivingTime()
								+ "' , " + traffic.getCampaignType() + ")");
			} catch (NullPointerException npe) {
				logger.error("NullPointer ", npe);
			} catch (DataAccessException dae) {
				if (dae.contains(DataTruncation.class)) {
					logger.warn("Data Truncation Error Found");
				} else if (dae.contains(SQLIntegrityConstraintViolationException.class)) {
					logger.warn("Duplicate Entry Error Found");
				} else if (dae.contains(ConnectException.class)) {
					logger.warn("Connection Error Found, saving Traffic");
					if (traffic != null)
						recordService.saveToRetry(traffic);
				}
			}
		}
	}
	
	@PreDestroy
	public void interrupt(){
		try{
			run.set(false);
			trafficInsertThread.interrupt();
		}catch(Exception e){
			
		}
	}
	
}
