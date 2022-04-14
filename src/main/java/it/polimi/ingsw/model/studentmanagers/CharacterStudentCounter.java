package it.polimi.ingsw.model.studentmanagers;

import it.polimi.ingsw.model.PawnColor;

public class CharacterStudentCounter extends  StudentCounter{
    public CharacterStudentCounter() {
        super();
    }

    public PawnColor takeStudentFrom(StudentCounter other) throws IllegalArgumentException {
        return movePawnFrom(other);
    }
}
