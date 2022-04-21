package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ProfessorManager;
import it.polimi.ingsw.model.studentmanagers.Bag;
import it.polimi.ingsw.model.studentmanagers.IslandManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CharacterFactory {
    private static ArrayList<AvailableCharacter> uninstantiatedCharacters;
    private final Bag bag;
    private final IslandManager islandManager;
    private final GameModel gameModel;
    private final List<Player> players;

    /**
     * creates new instance of class, creates new list uninstantiatedCharacters with all available characters only if no other instance of characterFactory ever created one
     * @param bag to create characters that need it
     * @param islandManager to create characters that need it
     */
    public CharacterFactory(Bag bag, IslandManager islandManager, GameModel gameModel, List<Player> players) {
        this.bag = bag;
        this.islandManager = islandManager;
        this.gameModel = gameModel;
        this.players = players;
        if(uninstantiatedCharacters == null){
            uninstantiatedCharacters = new ArrayList<>();
            Collections.addAll(uninstantiatedCharacters, AvailableCharacter.values());

        }
    }

    /**
     * private method used by createUninstantiatedCharacter to create a character from the enum AvailableCharacters
     * @param character to be created
     * @return new instance of the character
     * @throws EnumConstantNotPresentException if character doesn't exist
     */
    private Character create(AvailableCharacter character) throws EnumConstantNotPresentException{
        switch (character){
            case MONK: return new Monk(bag,islandManager);
            case CHEESE_MERCHANT: return new CheeseMerchant(gameModel);
            case FLAG_BEARER: return new FlagBearer(islandManager);
            case MESSENGER: return new Messenger(gameModel);
            case WITCH: return new Witch(islandManager);
            case CENTAUR: return new Centaur(islandManager);
            case JUGGLER: return new Juggler(bag, players);
            case KNIGHT: return new Knight(islandManager);
            case MUSHROOM_MERCHANT: return new MushroomMerchant(islandManager);
            case MUSICIAN: return new Musician(players);
            case PRINCESS: return new Princess(bag, players);
            case USURER: return new Usurer(players, bag);
            default: throw new EnumConstantNotPresentException(AvailableCharacter.class, "other");
        }
    }

    /**
     * creates an instance of one of the characters (subclasses of Character) that has never been instantiated before
     * @return instance of a character
     * @throws IllegalStateException if all characters have already been instantiated
     */
    public Character createUninstantiatedCharacter() throws IllegalStateException{
        if (uninstantiatedCharacters.isEmpty()) throw new IllegalStateException();
        Random random = new Random();
        int index = random.nextInt(uninstantiatedCharacters.size());
        AvailableCharacter character = uninstantiatedCharacters.get(index);
        uninstantiatedCharacters.remove(index);
        return create(character);
    }
}
