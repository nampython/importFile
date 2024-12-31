package org.example.services;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.dto.GetDevicesDto;
import org.example.dto.GetUserInfoDto;
import org.example.response.DeviceResponse;
import org.example.response.UserResponse;
import org.springframework.stereotype.Service;
import org.thingsboard.rest.client.RestClient;
import org.thingsboard.server.common.data.Device;
import org.thingsboard.server.common.data.User;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.PageLink;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
public class ThingsBoardServiceImpl implements ThingsBoardService {

	private final RestClient client;

	@Override
	public GetUserInfoDto.Response getInfoUser() {
		try {
			Optional<User> user = client.getUser();

			if (user.isPresent()) {
				log.info("User: {}", user.get());
				User userRp = user.get();
				UserResponse userResponse = UserResponse.toUserResponse(userRp);
				return GetUserInfoDto.Response.builder()
						.user(userResponse)
						.build();
			}
			return null;
		} catch (Exception e) {
			log.error("Error: ", e);
			throw new RuntimeException(e);
		} finally {
//			client.logout();
//			client.close();
		}
	}


	@Override
	public GetDevicesDto.Response getDevices() {
		PageData<Device> devices;
		PageLink pageLink = new PageLink(10);
		List<Device> deviceList = new ArrayList<>();

		do {
			devices = client.getTenantDevices("", pageLink);
			devices.getData().forEach(System.out::println);
			deviceList.addAll(devices.getData());
			pageLink = pageLink.nextPageLink();
		} while (devices.hasNext());

		List<DeviceResponse> deviceResponses = new ArrayList<>();
		deviceList.forEach(device -> {
			DeviceResponse deviceResponse = DeviceResponse.toDeviceResponse(device);
			deviceResponses.add(deviceResponse);
		});

		return GetDevicesDto.Response.builder()
				.devices(deviceResponses)
				.build();
	}
}
