package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.dto.GetDevicesDto;
import org.example.dto.GetUserInfoDto;
import org.example.services.ThingsBoardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@AllArgsConstructor
public class ThingsBoardController {

	private final ThingsBoardService thingsBoardService;

	@GetMapping("/thingsboard/info")
	public GetUserInfoDto.Response getInfoUser() {
		return thingsBoardService.getInfoUser();
	}

	@GetMapping("/thingsboard/devices")
	public GetDevicesDto.Response getDevices() {
		return thingsBoardService.getDevices();
	}
}
