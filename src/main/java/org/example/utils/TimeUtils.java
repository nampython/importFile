package org.example.utils;

import lombok.extern.log4j.Log4j2;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Log4j2
public class TimeUtils {
	public static LocalDateTime toLocalDateTime(String timestamp) {
		try {
			long millis = Long.parseLong(timestamp);
			return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
		} catch (Exception e) {
			log.error("Invalid timestamp format: {}", timestamp);
			throw new IllegalArgumentException("Invalid timestamp format: " + timestamp);
		}
	}
}
