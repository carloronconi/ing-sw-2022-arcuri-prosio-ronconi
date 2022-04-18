package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.ProfessorManager;

public class CheeseMerchant extends Character {
    private final ProfessorManager professorManager;

    /**
     * creates cheeseMerchant with cost of 2
     * @param professorManager needed for the special effect
     */
    protected CheeseMerchant(ProfessorManager professorManager) {
        super(2);
        this.professorManager = professorManager;
    }

    /**
     * calls method on professorManager to make sure it performs the comparison of number of students in the special way
     */
    public void useEffect(){
        professorManager.assertCheeseMerchantEffect();
        if (!isCostIncreased()) increaseCost();
    }
}
