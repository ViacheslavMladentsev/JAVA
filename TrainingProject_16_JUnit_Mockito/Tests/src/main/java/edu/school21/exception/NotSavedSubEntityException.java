package edu.school21.exception;

public class NotSavedSubEntityException extends RuntimeException {

    public NotSavedSubEntityException() {
    }

    public NotSavedSubEntityException(String s) {
        super(s);
    }

    public NotSavedSubEntityException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public NotSavedSubEntityException(Throwable throwable) {
        super(throwable);
    }

    public NotSavedSubEntityException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}

