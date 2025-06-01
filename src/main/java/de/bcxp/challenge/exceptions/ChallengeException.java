package de.bcxp.challenge.exceptions;

public class ChallengeException extends RuntimeException {
    
    public ChallengeException(String message) {
        super(message);
    }

    public ChallengeException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class CSVReadException extends ChallengeException {
        public CSVReadException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}