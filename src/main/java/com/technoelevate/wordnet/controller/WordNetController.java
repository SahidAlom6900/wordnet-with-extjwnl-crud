//package com.technoelevate.wordnet.controller;
//
//import static com.technoelevate.wordnet.common.WordNetConstants.SOME_THING_WENT_WRONG;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.technoelevate.wordnet.dto.WordDto;
//import com.technoelevate.wordnet.response.ResponseMessage;
//import com.technoelevate.wordnet.service.WordNetService;
//
//@RestController
//@RequestMapping("api/v1/word-net")
//public class WordNetController {
//
//	@Autowired
//	private WordNetService wordNetService;
//
//	@GetMapping("{word}")
//	public ResponseEntity<ResponseMessage> wordCheck(@PathVariable("word") String word) {
//		WordDto wordCheck = wordNetService.wordCheck(word);
//		if (wordCheck != null)
//			return new ResponseEntity<>(new ResponseMessage(false, "Words all senses", wordCheck), HttpStatus.OK);
//		return new ResponseEntity<>(new ResponseMessage(true, SOME_THING_WENT_WRONG, wordCheck), HttpStatus.OK);
//	}
//
//	@GetMapping("compound/{word}")
//	public ResponseEntity<ResponseMessage> compoundWord(@PathVariable("word") String word) {
//		List<WordDto> compoundNuon = wordNetService.compoundNuon(word);
//		if (!compoundNuon.isEmpty())
//			return new ResponseEntity<>(new ResponseMessage(false, "Compound Word all senses", compoundNuon),
//					HttpStatus.OK);
//		return new ResponseEntity<>(new ResponseMessage(true, SOME_THING_WENT_WRONG, compoundNuon), HttpStatus.OK);
//	}
//
//	@GetMapping("synonym/{word}")
//	public ResponseEntity<ResponseMessage> getSynonyms(@PathVariable("word") String word) {
//		WordDto synonyms = wordNetService.getSynonyms(word);
//		if (synonyms != null)
//			return new ResponseEntity<>(new ResponseMessage(false, "Fetch All Synonyms", synonyms), HttpStatus.OK);
//		return new ResponseEntity<>(new ResponseMessage(true, SOME_THING_WENT_WRONG, synonyms), HttpStatus.OK);
//	}
//
//	@GetMapping("antonym/{word}")
//	public ResponseEntity<ResponseMessage> getAntonyms(@PathVariable("word") String word) {
//		List<String> antonyms = wordNetService.getAntonyms(word);
//		if (!antonyms.isEmpty())
//			return new ResponseEntity<>(new ResponseMessage(false, "Fetch All antonyms", antonyms), HttpStatus.OK);
//		return new ResponseEntity<>(new ResponseMessage(true, SOME_THING_WENT_WRONG, antonyms), HttpStatus.OK);
//	}
//
//	@GetMapping("hypernym/{word}")
//	public ResponseEntity<ResponseMessage> getHypernyms(@PathVariable("word") String word) {
//		List<String> hypernyms = wordNetService.getHypernyms(word);
//		if (!hypernyms.isEmpty())
//			return new ResponseEntity<>(new ResponseMessage(false, "Fetch All hypernyms ", hypernyms), HttpStatus.OK);
//		return new ResponseEntity<>(new ResponseMessage(true, SOME_THING_WENT_WRONG, hypernyms), HttpStatus.OK);
//	}
//
//	@GetMapping("hyponym/{word}")
//	public ResponseEntity<ResponseMessage> getHyponyms(@PathVariable("word") String word) {
//		List<String> hyponyms = wordNetService.getHyponyms(word);
//		if (!hyponyms.isEmpty())
//			return new ResponseEntity<>(new ResponseMessage(false, "Fetch All hyponyms ", hyponyms), HttpStatus.OK);
//		return new ResponseEntity<>(new ResponseMessage(true, SOME_THING_WENT_WRONG, hyponyms), HttpStatus.OK);
//	}
//
//	@GetMapping("meronym/{word}")
//	public ResponseEntity<ResponseMessage> getMeronyms(@PathVariable("word") String word) {
//		List<String> meronyms = wordNetService.getMeronyms(word);
//		if (!meronyms.isEmpty())
//			return new ResponseEntity<>(new ResponseMessage(false, "Fetch All Meronyms ", meronyms), HttpStatus.OK);
//		return new ResponseEntity<>(new ResponseMessage(true, SOME_THING_WENT_WRONG, meronyms), HttpStatus.OK);
//	}
//
//	@GetMapping("holonym/{word}")
//	public ResponseEntity<ResponseMessage> getHolonyms(@PathVariable("word") String word) {
//		List<String> holonyms = wordNetService.getHolonyms(word);
//		if (!holonyms.isEmpty())
//			return new ResponseEntity<>(new ResponseMessage(false, "Fetch All Holonym ", holonyms), HttpStatus.OK);
//		return new ResponseEntity<>(new ResponseMessage(true, SOME_THING_WENT_WRONG, holonyms), HttpStatus.OK);
//	}
//}
