package it.polimi.ingsw.model.charactercards;

public enum AvailableCharacter {
    MONK(Monk.class),
    CHEESEMERCHANT(CheeseMerchant.class),
    FLAGBEARER(FlagBearer.class),
    MESSENGER(Messenger.class),
    WITCH(Witch.class),
    CENTAUR(Centaur.class),
    JUGGLER(Juggler.class),
    KNIGHT(Knight.class),
    MUSHROOMMERCHANT(MushroomMerchant.class),
    MUSICIAN(Musician.class),
    PRINCESS(Princess.class),
    USURER(Usurer.class);

    private Class<?> characterClass;
    private AvailableCharacter(Class<?> characterClass){
        this.characterClass = characterClass;
    }

    public Class<?> getCharacterClass() {
        return characterClass;
    }
}
