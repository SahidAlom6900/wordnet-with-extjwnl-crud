package com.technoelevate.wordnet;

import java.io.FileInputStream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import net.sf.extjwnl.dictionary.Dictionary;

@SpringBootApplication
public class WordnetApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordnetApplication.class, args);
	}

	@Bean
	public Dictionary dictionary() {
		Dictionary dictionary = null;
		try {
			dictionary = Dictionary
					.getInstance(new FileInputStream("C:/WordNetWithExtJwnl/file_properties.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dictionary;
	}
}
