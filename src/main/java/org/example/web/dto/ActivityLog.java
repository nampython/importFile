package org.example.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityLog {
	private List<ActivityLogOperation> activityLogOperation;
	private List<ActivityLogUpdateRow> activityLogUpdateRow;
}
