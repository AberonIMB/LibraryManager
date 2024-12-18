package org.example.exceptions.stateExceptions;

public class ReaderIsNullException extends StateValidationException {

    public ReaderIsNullException(String readerId) {
        super("Читатель с ID %s не надйен".formatted(readerId));
    }
}