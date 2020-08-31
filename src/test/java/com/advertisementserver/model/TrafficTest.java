package com.advertisementserver.model;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.adevrtisementserver.model.Traffic;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TrafficTest {

	private ObjectMapper mapper = new ObjectMapper();

	@Test
	public void testDeSerialization() throws JsonParseException, JsonMappingException, IOException {
		String json = "{\"publisherID\":5,\"campaignID\":5,\"ipAddress\":\"127.0.0.1\",\"trafficReceivingParamValue\":\"123456\",\"trafficSendingParamValue\":\"67890\",\"trafficReceivingDate\":1559088000000,\"trafficReceivingTime\":\"00:50:10\"}";
		Traffic traffic = mapper.readValue(json, Traffic.class);
		assertTrue(traffic.getPublisherID() == 5);
	}

	@Test
	public void testSerialization() throws JsonProcessingException {
		Traffic traffic = new Traffic();
		traffic.setPublisherID(2);
		traffic.setCampaignID(1);
		String json = mapper.writeValueAsString(traffic);
		String lookup = "\"campaignID\":1,";

		assertTrue(json.contains(lookup));
	}

}
