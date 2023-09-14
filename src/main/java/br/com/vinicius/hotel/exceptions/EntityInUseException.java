package br.com.vinicius.hotel.exceptions;

public class EntityInUseException extends RuntimeException {
    public EntityInUseException(String msg) {
        super(msg);
    }
}
