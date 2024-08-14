package org.example.web.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityLogUpdateRow {
	private Integer row;
	private Integer column;
	private String columnName;
	private String oldValue;
	private String newValue;
	private Action action;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Boolean isNull() {
		return this.row == null && this.column == null && this.columnName == null && this.oldValue == null && this.newValue == null;
	}
}
