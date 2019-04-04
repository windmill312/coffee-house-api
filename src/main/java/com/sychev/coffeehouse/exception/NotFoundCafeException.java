package com.sychev.coffeehouse.exception;

public class NotFoundCafeException extends RuntimeException {

    private static final long serialVersionUID = -52339063298993183L;

    public NotFoundCafeException() {
        super();
    }

    public NotFoundCafeException(String message) {
        super(message);
    }
}
