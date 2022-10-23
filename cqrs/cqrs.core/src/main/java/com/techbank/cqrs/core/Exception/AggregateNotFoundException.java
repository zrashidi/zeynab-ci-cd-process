package com.techbank.cqrs.core.Exception;

public class AggregateNotFoundException  extends RuntimeException{
    public AggregateNotFoundException(String message) {
        super(message);
    }
}
