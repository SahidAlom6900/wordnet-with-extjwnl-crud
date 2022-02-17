package com.technoelevate.wordnet.dto;

import java.util.List;

import lombok.Data;

@Data
public class WordSourceTarget {
	private List<String> sourceWord;
	private String gloss;
	private String targetWord;

}
