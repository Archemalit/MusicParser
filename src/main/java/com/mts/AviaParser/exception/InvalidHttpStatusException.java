package com.mts.AviaParser.exception;

import lombok.Getter;

@Getter
public class InvalidHttpStatusException extends RuntimeException {
    private final int statusCode;

    public InvalidHttpStatusException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}