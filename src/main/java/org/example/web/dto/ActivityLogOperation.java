package org.example.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityLogOperation {
	private Integer no;
	private CsvData oldData;
	private CsvData newData;
	private Action action;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
