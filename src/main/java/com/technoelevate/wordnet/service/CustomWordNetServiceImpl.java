package com.technoelevate.wordnet.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technoelevate.wordnet.dto.CustomWordDto;
import com.technoelevate.wordnet.dto.WordSourceTarget;

import ch.qos.logback.core.joran.conditional.IfAction;
import lombok.extern.slf4j.Slf4j;
import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.Pointer;
import net.sf.extjwnl.data.PointerType;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.data.Word;
import net.sf.extjwnl.dictionary.Dictionary;

@Slf4j
@Service
public class CustomWordNetServiceImpl implements CustomWordNetService {
	@Autowired
	private Dictionary dictionary;
	private Map<Set<String>, String> customWordDto;
	private int i;

	@Override
	public CustomWordDto getWord(String word) {
		customWordDto = new LinkedHashMap<>();
		return fetchWord(word);
	}

	@Override
	public boolean createWordToSynset(WordSourceTarget wordSourceTarget) {
		try {
			if (!dictionary.isEditable()) {
				dictionary.edit();
			}
			String lemma = wordSourceTarget.getSourceWord().get(0);
			IndexWord indexWord = dictionary.lookupIndexWord(POS.NOUN, lemma);

			if (indexWord != null) {
				Synset sourceSynset = targetSynset(indexWord, lemma);
				if (sourceSynset == null)
					return false;
				sourceSynset.setGloss(sourceSynset.getGloss() + " // " + wordSourceTarget.getGloss());
				dictionary.save();
			} else {
				Iterator<Synset> synsets = dictionary.getSynsetIterator(POS.NOUN);
				Synset newSynset = dictionary.createSynset(POS.NOUN);
				newSynset.setGloss(wordSourceTarget.getGloss() + " " + i);
				indexWord = dictionary.createIndexWord(newSynset.getPOS(), lemma, newSynset);
				if (!synsets.hasNext()) {
					dictionary.save();
					return true;
				}
				String targetWord = wordSourceTarget.getTargetWord();
				if (targetWord == null) {
					dictionary.save();
					return true;
				}
				IndexWord targetIndexWord = dictionary.lookupIndexWord(POS.NOUN, targetWord);
				if (targetIndexWord == null) {
					return false;
				}
				Synset sourceSynset = targetSynset(indexWord, lemma);
				Synset targetSynset = targetSynset(targetIndexWord, targetWord);
				saveWordSynset(targetSynset, sourceSynset, lemma);
			}
			log.info(indexWord.toString());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private CustomWordDto fetchWord(@NotNull String word) {
		word = word.toLowerCase();
		try {
			IndexWord indexWord = dictionary.lookupIndexWord(POS.NOUN, word);
			if (indexWord == null)
				return null;
			List<Synset> senses = indexWord.getSenses();
			Set<String> lemmas = new LinkedHashSet<>();
			lemmas.add(word);
			for (Synset synset0 : senses) {
				if (synset0.getWords().get(0).getLemma().equalsIgnoreCase(word)) {
					for (Word word0 : synset0.getWords()) {
						lemmas.add(word0.getLemma());
					}
				} else if (synset0.containsWord(word)) {
					lemmas.add(synset0.getWords().get(0).getLemma());
				}
			}
			Synset synset = targetSynset(indexWord, word);
			if (synset == null)
				return null;
			customWordDto.put(lemmas, synset.getGloss());
			return new CustomWordDto(customWordDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private IndexWord lookupIndexWord(POS pos, String sourceWord) {
		try {
			return dictionary.lookupIndexWord(pos, sourceWord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Synset targetSynset(IndexWord lookupIndexWord, String lemma) {
		for (Synset targetSynset : lookupIndexWord.getSenses()) {
			if (targetSynset.getWords().get(0).getLemma().equalsIgnoreCase(lemma)) {
				return targetSynset;
			}
		}
		return null;
	}

	private void saveWordSynset(Synset targetSynset, Synset sourceSynset, String lemma) {
		try {
			targetSynset.getWords().add(new Word(dictionary, sourceSynset, lemma));
			Pointer newPointer = new Pointer(PointerType.SIMILAR_TO, targetSynset, sourceSynset);
			targetSynset.getPointers().add(newPointer);
			dictionary.save();
		} catch (JWNLException e) {
			e.printStackTrace();
		}
	}

	private List<String> targetSynset(String targetWord, List<String> sourceWords) {
		int f = 0;
		List<String> finalSourceWord = new ArrayList<>();
		Synset targetSynsets = targetSynset(lookupIndexWord(POS.NOUN, targetWord), targetWord);
		for (String lemma : sourceWords) {
			Synset sourceSynsets = targetSynset(lookupIndexWord(POS.NOUN, lemma), lemma);
			f = 0;
			for (Word word : targetSynsets.getWords()) {
				if (word.getLemma().equalsIgnoreCase(sourceSynsets.getWords().get(0).getLemma())) {
					f = 1;
					break;
				}
			}
			if (f == 0) {
				finalSourceWord.add(lemma);
			}

		}
		return finalSourceWord;
	}

	@Override
	public boolean addWordToSynset(WordSourceTarget wordSourceTarget) {
		int index = 0;
		try {
			if (!dictionary.isEditable()) {
				dictionary.edit();
			}
			IndexWord lookupIndexWord = dictionary.lookupIndexWord(POS.NOUN, wordSourceTarget.getTargetWord());
			if (lookupIndexWord == null) {
				return false;
			}
			List<String> sourceWord2 = targetSynset(wordSourceTarget.getTargetWord(), wordSourceTarget.getSourceWord());
			if (sourceWord2.isEmpty()) {
				return false;
			}
			List<IndexWord> indexWords = sourceWord2.stream()
					.filter(sourceWord -> lookupIndexWord(POS.NOUN, sourceWord) != null)
					.map(sourceWord -> lookupIndexWord(POS.NOUN, sourceWord)).collect(Collectors.toList());

			Synset targetSynset = targetSynset(lookupIndexWord, wordSourceTarget.getTargetWord());

			for (IndexWord indexWord : indexWords) {
				String lemma = sourceWord2.get(index++);
				Synset sourceSynset = targetSynset(indexWord, lemma);
				saveWordSynset(targetSynset, sourceSynset, lemma);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteWord(WordSourceTarget wordSourceTarget) {
		int index = 0, count = 0;
		try {
			if (!dictionary.isEditable()) {
				dictionary.edit();
			}
			List<IndexWord> indexWords = wordSourceTarget.getSourceWord().stream()
					.filter(sourceWord -> lookupIndexWord(POS.NOUN, sourceWord) != null)
					.map(sourceWord -> lookupIndexWord(POS.NOUN, sourceWord)).collect(Collectors.toList());
			List<String> sourceWord2 = wordSourceTarget.getSourceWord();
			for (IndexWord indexWord : indexWords) {
				String lemma = sourceWord2.get(index++);
				Synset sourceSynset = targetSynset(indexWord, lemma);
				List<Synset> senses = indexWord.getSenses();
				int size = senses.size();
				for (i = 0; i < size; i++) {
					List<Word> words = senses.get(count).getWords();
					if (words.get(0).getLemma().equalsIgnoreCase(lemma)) {
						--i;
						count = 1;
					}
					int len = words.size();
					for (int j = 0; j < len; j++) {
						if (words.get(j).getLemma().equalsIgnoreCase(lemma)) {
							words.remove(j);
							break;
						}
					}
					if (senses.size() == 1)
						break;
				}
				List<Word> words = senses.get(0).getWords();
				int len = words.size();
				for (int j = 0; j < len; j++) {
					if (words.get(j).getLemma().equalsIgnoreCase(lemma)) {
						words.remove(j);
						break;
					}
				}
				dictionary.removeSynset(sourceSynset);
				dictionary.save();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public CustomWordDto getWordWithPre(String word) {
		Set<String> wordSenseSet = customWordDto.entrySet().stream()
				.flatMap(entrySet -> entrySet.getKey().stream().filter(wordSense -> wordSense.equalsIgnoreCase(word)))
				.collect(Collectors.toSet());
		if (wordSenseSet.isEmpty())
			return null;
		return fetchWord(word);
	}

	@Override
	public void createPos(String pos) {
		try {
			if (!dictionary.isEditable()) {
				dictionary.edit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
