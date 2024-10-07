package com.kb.EDUBOARD.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardCategory {
	private String type;
	private String name;
	private int level;
	private int orderNo;
	
}
