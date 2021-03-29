package com.degg.famateur.exception;

public class BookableAssetNotFoundException extends RuntimeException{
    public BookableAssetNotFoundException(String message) {
        super(message);
    }
}
