package org.example.web.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CsvData {
	private Integer no;
	private String name;
	private String type;
	private String number;
	private String keeperOne;
	private String keeperSecond;
	private String borrowDate;
	private String dateReceived;
	private String productCode;
	private String transferMethod;
	private String code;
}
