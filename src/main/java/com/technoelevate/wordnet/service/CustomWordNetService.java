package com.technoelevate.wordnet.service;

import com.technoelevate.wordnet.dto.CustomWordDto;
import com.technoelevate.wordnet.dto.WordSourceTarget;

public interface CustomWordNetService {
	CustomWordDto getWord(String word);

	CustomWordDto getWordWithPre(String word);

	boolean createWordToSynset(WordSourceTarget wordSourceTarget);

	boolean deleteWord(WordSourceTarget wordSourceTarget);

	boolean addWordToSynset(WordSourceTarget wordSourceTarget);
	
	void createPos(String pos);

}
