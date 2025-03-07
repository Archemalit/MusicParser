package com.mts.AviaParser.exception;

import lombok.Getter;

@Getter
public class ParsingException extends RuntimeException {
    private final int statusCode;

    public ParsingException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}