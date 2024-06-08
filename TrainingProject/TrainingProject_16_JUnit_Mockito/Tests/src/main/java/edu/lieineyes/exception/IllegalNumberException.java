package edu.lieineyes.exception;

public class IllegalNumberException extends RuntimeException {
    public IllegalNumberException() {
    }

    public IllegalNumberException(String s) {
        super(s);
    }

    public IllegalNumberException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public IllegalNumberException(Throwable throwable) {
        super(throwable);
    }

    public IllegalNumberException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
