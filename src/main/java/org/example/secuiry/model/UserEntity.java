package org.example.secuiry.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.web.dto.ActivityLog;
import org.example.web.dto.FileInfo;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("user")
public class UserEntity {
	private String id;
	private String userName;
	private String password;
	private List<FileInfo> fileInfos;
	private List<ActivityLog> activityLogs;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
