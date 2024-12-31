package org.example.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.thingsboard.server.common.data.Device;
import org.thingsboard.server.common.data.id.CustomerId;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DeviceResponse {
	private CustomerId customerId;
	private String name;
	private String type;
	private String label;

	public static DeviceResponse toDeviceResponse(Device device) {
		return DeviceResponse.builder()
				.customerId(device.getCustomerId())
				.name(device.getName())
				.type(device.getType())
				.label(device.getLabel())
				.build();
	}
}
