package org.example.exceptions;

public class ProgramShouldBeClosedException extends RuntimeException {
    public ProgramShouldBeClosedException() {
        super("Program expected to be closed");
    }
}
