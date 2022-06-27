package it.polimi.ingsw.server.model.studentmanagers;

import it.polimi.ingsw.server.model.PawnColor;

public class CharacterStudentCounter extends  StudentCounter{
    public CharacterStudentCounter() {
        super();
    }

    public PawnColor takeStudentFrom(StudentCounter other) throws IllegalArgumentException {
        return movePawnFrom(other);
    }
}
