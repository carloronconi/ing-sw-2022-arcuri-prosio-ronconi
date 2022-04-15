package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.ProfessorManager;

public class CheeseMerchant extends Character {
    private final ProfessorManager professorManager;

    protected CheeseMerchant(ProfessorManager professorManager) {
        super(2);
        this.professorManager = professorManager;
    }

    public void useEffect(){
        professorManager.assertCheeseMerchantEffect();
        if(!isCostIncreased()) assertCostIncreased();
    }
}
