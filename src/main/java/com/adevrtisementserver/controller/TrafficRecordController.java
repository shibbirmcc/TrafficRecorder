package com.adevrtisementserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adevrtisementserver.model.Traffic;
import com.adevrtisementserver.service.TrafficRecordService;

@RestController
@RequestMapping("/recorder")
public class TrafficRecordController {

	@Autowired
	private TrafficRecordService recordService;

	@PostMapping("/traffic")
	public void insertTrafficInfo(@RequestBody Traffic traffic) {
		recordService.insertTraffic(traffic);
	}
}
