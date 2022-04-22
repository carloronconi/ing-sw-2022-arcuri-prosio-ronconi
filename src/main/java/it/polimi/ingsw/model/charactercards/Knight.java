package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.ConverterUtility;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.studentmanagers.IslandManager;

import java.util.List;
import java.util.UUID;

public class Knight extends Character {
    private final IslandManager islandManager;
    private final List<Player> players;

    public Knight(IslandManager islandManager, List<Player> players) {
        super(2);
        this.islandManager = islandManager;
        this.players = players;
    }

    public void useEffect(UUID player) throws NoSuchFieldException {
        Player p = ConverterUtility.idToElement(player, players);
        islandManager.assertKnightEffect(p);
    }
}
