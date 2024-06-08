package edu.lieineyes.exception;

public class AlreadyAuthenticatedException extends RuntimeException {
    public AlreadyAuthenticatedException() {
    }

    public AlreadyAuthenticatedException(String s) {
        super(s);
    }

    public AlreadyAuthenticatedException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public AlreadyAuthenticatedException(Throwable throwable) {
        super(throwable);
    }

    public AlreadyAuthenticatedException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
