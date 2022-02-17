package com.technoelevate.wordnet.controller;

import static com.technoelevate.wordnet.common.WordNetConstants.SOME_THING_WENT_WRONG;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technoelevate.wordnet.dto.CustomWordDto;
import com.technoelevate.wordnet.dto.WordSourceTarget;
import com.technoelevate.wordnet.response.ResponseMessage;
import com.technoelevate.wordnet.service.CustomWordNetService;

@RestController
@RequestMapping("api/v1/custom/word-net")
public class CustomWordNetController {
	@Autowired
	private CustomWordNetService service;

	@GetMapping("{word}")
	public ResponseEntity<ResponseMessage> getWord(@PathVariable(name = "word") String word) {
		CustomWordDto word2 = service.getWord(word);
		if (word2 != null)
			return new ResponseEntity<>(new ResponseMessage(false, "Fetch Word Successfully !!!", word2), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, SOME_THING_WENT_WRONG, word2), HttpStatus.OK);
	}
	
	@GetMapping("store/{word}")
	public ResponseEntity<ResponseMessage> getWordWithPre(@PathVariable(name = "word") String word) {
		 CustomWordDto wordWithPre = service.getWordWithPre(word);
		if (wordWithPre!=null)
			return new ResponseEntity<>(new ResponseMessage(false, "Fetch Word Successfully !!!", wordWithPre), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, SOME_THING_WENT_WRONG, wordWithPre), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ResponseMessage> createWord(@RequestBody WordSourceTarget wordSourceTarget) {
		if (service.createWordToSynset(wordSourceTarget))
			return new ResponseEntity<>(new ResponseMessage(false, "Add Word or Synset or Gloss Successfully !!!", wordSourceTarget),
					HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, SOME_THING_WENT_WRONG, wordSourceTarget), HttpStatus.OK);
	}

	@PostMapping("word-synset")
	public ResponseEntity<ResponseMessage> addWordToSynset(@RequestBody WordSourceTarget wordSourceTarget) {
		if (service.addWordToSynset(wordSourceTarget))
			return new ResponseEntity<>(new ResponseMessage(false, "Add Word to Synset Successfully !!!", wordSourceTarget),
					HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, SOME_THING_WENT_WRONG, null), HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<ResponseMessage> removeSynset(@RequestBody WordSourceTarget wordSourceTarget) {
		if (service.deleteWord(wordSourceTarget))
			return new ResponseEntity<>(new ResponseMessage(false, "Remove Synset from Dictionary Successfully !!!", wordSourceTarget),
					HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, SOME_THING_WENT_WRONG, wordSourceTarget), HttpStatus.OK);
	}
}
