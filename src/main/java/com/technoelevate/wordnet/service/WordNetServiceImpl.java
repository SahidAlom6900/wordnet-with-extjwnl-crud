//package com.technoelevate.wordnet.service;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.technoelevate.wordnet.dto.WordDto;
//import com.technoelevate.wordnet.exception.InvalidWordException;
//
//import lombok.extern.slf4j.Slf4j;
//import net.didion.jwnl.JWNLException;
//import net.didion.jwnl.data.IndexWord;
//import net.didion.jwnl.data.POS;
//import net.didion.jwnl.data.PointerUtils;
//import net.didion.jwnl.data.Synset;
//import net.didion.jwnl.data.Word;
//import net.didion.jwnl.data.list.PointerTargetNode;
//import net.didion.jwnl.data.list.PointerTargetNodeList;
//import net.didion.jwnl.dictionary.Dictionary;
//
//@Slf4j
//@Service
//public class WordNetServiceImpl implements WordNetService {
//
//	@Autowired
//	private Dictionary dictionary;
//
//	private WordDto wordDto;
//	private IndexWord indexWord;
//	private List<String> synsets;
//
//	@Override
//	public WordDto getSynonyms(String word) {
//		if (dictionary == null)
//			return null;
//		List<POS> wordPos = getWordPos(word);
//		wordDto = new WordDto();
//		for (POS pos : wordPos) {
//			try {
//				indexWord = dictionary.lookupIndexWord(pos, word);
//				Synset[] senses = indexWord.getSenses();
//				getWordDto(senses, pos);
//			} catch (JWNLException e) {
//				e.printStackTrace();
//			}
//		}
//		return wordDto;
//	}
//
//	@Override
//	public WordDto wordCheck(String word) {
//		List<POS> wordPos = getWordPos(word);
//		wordDto = new WordDto();
//		for (POS pos : wordPos) {
//			try {
//				indexWord = dictionary.lookupIndexWord(pos, word);
//				Synset[] senses = indexWord.getSenses();
//				getWordDto(senses, pos);
//			} catch (JWNLException e) {
//				e.printStackTrace();
//			}
//		}
//		return wordDto;
//	}
//
//	@SuppressWarnings("unchecked")
//	private List<POS> getWordPos(String word) {
//		List<POS> poses = POS.getAllPOS();
//		List<POS> poseList = new ArrayList<>();
//		for (POS pos : poses) {
//			IndexWord indexWord1;
//			try {
//				indexWord1 = dictionary.lookupIndexWord(pos, word);
//				if (indexWord1 != null) {
//					poseList.add(pos);
//				}
//			} catch (JWNLException e) {
//				e.printStackTrace();
//			}
//		}
//		if (poseList.isEmpty())
//			throw new InvalidWordException("Please Enter the Correct Word");
//		return poseList;
//	}
//
//	private List<Object> setWordDto(Synset[] senses) {
//		List<Object> object = new ArrayList<>();
//		Map<String, String> map = new LinkedHashMap<>();
//		for (Synset sense : senses) {
//			Word[] words = sense.getWords();
//			for (Word word : words) {
//				String string = "";
//				if (map.containsKey(word.getLemma())) {
//					string = " %||% " + map.get(word.getLemma());
//				}
//				map.put(word.getLemma(), word.getSynset().getGloss() + string);
//			}
//		}
//		for (Map.Entry<String, String> object2 : map.entrySet()) {
//			object.add(object2.getKey() + " :-> " + object2.getValue());
//		}
//		return object;
//	}
//
//	private void getWordDto(Synset[] senses, POS pos) {
//		if (pos == POS.NOUN) {
//			wordDto.setNoun(setWordDto(senses));
//		} else if (pos == POS.VERB) {
//			wordDto.setVerb(setWordDto(senses));
//		} else if (pos == POS.ADVERB) {
//			wordDto.setAdverd(setWordDto(senses));
//		} else {
//			wordDto.setAdjective(setWordDto(senses));
//		}
//	}
//
//	@Override
//	public List<String> getHypernyms(String word) {
//		List<POS> wordPos = getWordPos(word);
//		synsets = new ArrayList<>();
//		for (POS pos : wordPos) {
//			try {
//				indexWord = dictionary.lookupIndexWord(pos, word);
//				Synset[] senses = indexWord.getSenses();
//				for (Synset sense : senses) {
//					PointerTargetNodeList hypernyms = PointerUtils.getInstance().getDirectHypernyms(sense);
//					for (Object object : hypernyms) {
//						PointerTargetNode pointerTargetNode = (PointerTargetNode) object;
//						Word[] words = pointerTargetNode.getSynset().getWords();
//						getWord(words);
//					}
//				}
//			} catch (JWNLException e) {
//				e.printStackTrace();
//			}
//		}
//		return synsets;
//	}
//
//	@Override
//	public List<String> getAntonyms(String word) {
//		List<POS> wordPos = getWordPos(word);
//		synsets = new ArrayList<>();
//		for (POS pos : wordPos) {
//			try {
//				indexWord = dictionary.lookupIndexWord(pos, word);
//				Synset[] senses = indexWord.getSenses();
//				for (Synset sense : senses) {
//					PointerTargetNodeList antonyms = PointerUtils.getInstance().getAntonyms(sense);
//					for (Object object : antonyms) {
//						PointerTargetNode pointerTargetNode = (PointerTargetNode) object;
//						Word[] words = pointerTargetNode.getSynset().getWords();
//						getWord(words);
//					}
//				}
//			} catch (JWNLException e) {
//				e.printStackTrace();
//			}
//		}
//		return synsets;
//	}
//
//	@Override
//	public List<String> getHyponyms(String word) {
//		List<POS> wordPos = getWordPos(word);
//		synsets = new ArrayList<>();
//		for (POS pos : wordPos) {
//			try {
//				indexWord = dictionary.lookupIndexWord(pos, word);
//				Synset[] senses = indexWord.getSenses();
//				for (Synset sense : senses) {
//					PointerTargetNodeList hyponyms = PointerUtils.getInstance().getDirectHyponyms(sense);
//					for (Object object : hyponyms) {
//						PointerTargetNode pointerTargetNode = (PointerTargetNode) object;
//						Word[] words = pointerTargetNode.getSynset().getWords();
//						getWord(words);
//					}
//				}
//			} catch (JWNLException e) {
//				e.printStackTrace();
//			}
//		}
//		return synsets;
//	}
//
//	@Override
//	public List<String> getMeronyms(String word) {
//		synsets = new ArrayList<>();
//		List<POS> wordPos = getWordPos(word);
//		for (POS pos : wordPos) {
//			try {
//				indexWord = dictionary.lookupIndexWord(pos, word);
//				Synset[] senses = indexWord.getSenses();
//				for (Synset sense : senses) {
//					PointerTargetNodeList meronyms = PointerUtils.getInstance().getMeronyms(sense);
//					for (Object object : meronyms) {
//						PointerTargetNode pointerTargetNode = (PointerTargetNode) object;
//						Word[] words = pointerTargetNode.getSynset().getWords();
//						getWord(words);
//					}
//				}
//			} catch (JWNLException e) {
//				e.printStackTrace();
//			}
//		}
//		return synsets;
//	}
//
//	@Override
//	public List<String> getHolonyms(String word) {
//		List<POS> wordPos = getWordPos(word);
//		synsets = new ArrayList<>();
//		for (POS pos : wordPos) {
//			try {
//				indexWord = dictionary.lookupIndexWord(pos, word);
//				Synset[] senses = indexWord.getSenses();
//				for (Synset sense : senses) {
//					PointerTargetNodeList holonyms = PointerUtils.getInstance().getHolonyms(sense);
//					for (Object object : holonyms) {
//						PointerTargetNode pointerTargetNode = (PointerTargetNode) object;
//						Word[] words = pointerTargetNode.getSynset().getWords();
//						getWord(words);
//					}
//				}
//			} catch (JWNLException e) {
//				e.printStackTrace();
//			}
//		}
//		return synsets;
//	}
//
//	private void getWord(Word[] words) {
//		for (Word word : words) {
//			synsets.add(word.getLemma() + " :-> " + word.getSynset().getGloss());
//		}
//		if (synsets.isEmpty())
//			throw new InvalidWordException("Please Enter the Correct Word");
//	}
//
//	@Override
//	public List<WordDto> compoundNuon(String word) {
//		List<WordDto> wordDtos = new ArrayList<>();
//		try {
//			getWord(word);
//			IndexWord indexWord2 = dictionary.lookupIndexWord(POS.NOUN, word);
//			Synset[] senses = indexWord2.getSenses();
//			for (Synset synset : senses) {
//				log.debug(synset.toString());
//			}
//		} catch (JWNLException e) {
//			e.printStackTrace();
//		}
//		return wordDtos;
//	}
//
//	private List<String> getWord(String word) {
//		String[] words = word.split("\\s");
//		for (String string : words) {
//			log.debug(string);
//		}
//		return Collections.emptyList();
//	}
//
//}
