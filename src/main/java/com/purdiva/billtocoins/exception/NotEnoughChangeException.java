package com.purdiva.billtocoins.exception;

public class NotEnoughChangeException extends RuntimeException {
    public NotEnoughChangeException(String message) { super(message); }
}
