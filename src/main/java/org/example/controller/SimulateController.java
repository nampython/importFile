package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@AllArgsConstructor
@Log4j2
@EnableScheduling
public class SimulateController {

	private final RestTemplate restTemplate;



}
