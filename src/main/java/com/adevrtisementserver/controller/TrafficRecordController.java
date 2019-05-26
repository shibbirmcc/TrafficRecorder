package com.adevrtisementserver.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.adevrtisementserver.model.Traffic;
import com.adevrtisementserver.service.TrafficRecordService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/recorder")
public class TrafficRecordController {

	private Logger logger = LoggerFactory.getLogger(TrafficRecordController.class);
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private TrafficRecordService recordService;

	// TODO: Do something about the path /
//	@GetMapping

	@PostMapping("/traffic")
	@SuppressWarnings("rawtypes")
	public @ResponseBody ResponseEntity insertTrafficInfo(@RequestBody String recordPayload) {
		try {
			Traffic traffic = mapper.readValue(recordPayload, Traffic.class);
			System.out.println(traffic.toString());
			recordService.insertTraffic(traffic);
			return new ResponseEntity(HttpStatus.OK);
		} catch (JsonParseException jpe) {
			logger.error("Parsing Exception: ", jpe);
			return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
		} catch (JsonMappingException jme) {
			logger.error("Mapping Exception: ", jme);
			return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
		} catch (IOException e) {
			logger.error("IO Exception: ", e);
			return ResponseEntity.notFound().build();
		}
	}

}
