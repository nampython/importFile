package org.example.services;

import org.example.dto.GetDevicesDto;
import org.example.dto.GetUserInfoDto;


public interface ThingsBoardService {
	GetUserInfoDto.Response getInfoUser();
	GetDevicesDto.Response getDevices();
}
