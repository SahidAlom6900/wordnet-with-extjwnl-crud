package com.technoelevate.wordnet.exception;

@SuppressWarnings("serial")
public class InvalidWordException extends RuntimeException {
	public InvalidWordException(String msg) {
		super(msg);
	}
}
