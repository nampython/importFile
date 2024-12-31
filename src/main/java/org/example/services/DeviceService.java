package org.example.services;

import org.example.dto.GetDeviceDataById;

public interface DeviceService {
	GetDeviceDataById.Response getDeviceDataById(GetDeviceDataById.Request request);
}
