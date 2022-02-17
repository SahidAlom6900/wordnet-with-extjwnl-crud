package com.technoelevate.wordnet.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordDto {
	private List<Object> noun;
	private List<Object> verb;
	private List<Object> adverd;
	private List<Object> adjective;
}
