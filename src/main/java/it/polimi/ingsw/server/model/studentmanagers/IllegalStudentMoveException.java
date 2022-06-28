package it.polimi.ingsw.server.model.studentmanagers;

public class IllegalStudentMoveException extends RuntimeException{
    public IllegalStudentMoveException() {
    }

    public IllegalStudentMoveException(String message) {
        super(message);
    }

    /*public IllegalStudentMoveException(String message, Throwable cause) {
        super(message, cause);
    }*/

    /*public IllegalStudentMoveException(Throwable cause) {
        super(cause);
    }*/
}
