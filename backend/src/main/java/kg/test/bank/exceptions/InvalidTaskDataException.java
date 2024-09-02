package kg.test.bank.exceptions;

public class InvalidTaskDataException extends RuntimeException {
    public InvalidTaskDataException(String message) {
        super(message);
    }
}