package it.polimi.ingsw.server.model;

public class IllegalModelMoveException extends RuntimeException{

    public IllegalModelMoveException() {
    }

    public IllegalModelMoveException(String message) {
        super(message);
    }

    public IllegalModelMoveException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalModelMoveException(Throwable cause) {
        super(cause);
    }
}
