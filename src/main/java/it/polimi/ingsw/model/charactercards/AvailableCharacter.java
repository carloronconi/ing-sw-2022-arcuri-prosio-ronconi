package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.PawnColor;

import java.util.ArrayList;

public enum AvailableCharacter {
    MONK(Monk.class, 1),
    CHEESEMERCHANT(CheeseMerchant.class, 2),
    FLAGBEARER(FlagBearer.class, 3),
    MESSENGER(Messenger.class, 1),
    WITCH(Witch.class, 2),
    CENTAUR(Centaur.class, 3),
    JUGGLER(Juggler.class, 1),
    KNIGHT(Knight.class, 2),
    MUSHROOMMERCHANT(MushroomMerchant.class, 3),
    MUSICIAN(Musician.class, 1),
    PRINCESS(Princess.class, 2),
    USURER(Usurer.class, 3);

    private final Class<? extends Character> characterClass;
    private int initialCost;
    AvailableCharacter(Class<? extends Character> characterClass, int initialCost){
        this.characterClass = characterClass;
        this.initialCost = initialCost;
    }

    public Class<? extends Character> getCharacterClass() {
        return characterClass;
    }

    public int getInitialCost() {
        return initialCost;
    }

    public int getMaxColorSwaps(){
        if (Musician.class == characterClass) {
            return Musician.getMaxColorSwaps();
        } else if (Juggler.class == characterClass) {
            return Juggler.getMaxColorSwaps();
        }
        return 0;
    }

    public ArrayList<PawnColor> getStudents(){
        if (Juggler.class == characterClass){
            return Juggler.getStudents();
        } else if(Monk.class == characterClass){
            return Monk.getStudents();
        } else if (Princess.class == characterClass){
            return Princess.getStudents();
        }
        return null;
    }
}
