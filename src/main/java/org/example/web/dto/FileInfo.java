package org.example.web.dto;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FileInfo {
	@Id
	private ObjectId id;
	private String fileName;
	private List<CsvData> csvData;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
