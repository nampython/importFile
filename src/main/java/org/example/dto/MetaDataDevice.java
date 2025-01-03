package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MetaDataDevice {
	private Double temperature;
	private Double humidity;
	private Double airQuality;
	private Double lightIntensity;
}
