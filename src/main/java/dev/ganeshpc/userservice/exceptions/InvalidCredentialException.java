package dev.ganeshpc.userservice.exceptions;

public class InvalidCredentialException extends Exception {
    
    public InvalidCredentialException(String emailId) {
        super("Invalid password for emailId: " + emailId);
    }
}
