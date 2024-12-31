package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.GetDeviceDataById;
import org.example.services.DeviceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DeviceController {

	private final DeviceService deviceService;

	@PostMapping("/device")
	public GetDeviceDataById.Response getDeviceDataById(@RequestBody GetDeviceDataById.Request request) {
		return deviceService.getDeviceDataById(request);
	}
}
