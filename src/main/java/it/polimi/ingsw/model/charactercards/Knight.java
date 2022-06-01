package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.ConverterUtility;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.charactercards.effectarguments.EffectWithPlayer;
import it.polimi.ingsw.model.studentmanagers.IslandManager;

import java.util.List;
import java.util.UUID;

public class Knight extends Character implements EffectWithPlayer {
    private final IslandManager islandManager;
    private final List<Player> players;
    private UUID player;

    public Knight(IslandManager islandManager, List<Player> players) {
        super(AvailableCharacter.KNIGHT.getInitialCost());
        this.islandManager = islandManager;
        this.players = players;
    }

    public void useEffect() throws NoSuchFieldException {
        if (player == null) throw new IllegalStateException();
        Player p = ConverterUtility.idToElement(player, players);
        islandManager.assertKnightEffect(p);
        if (!isCostIncreased()) increaseCost();
        player = null;
    }


    @Override
    public void setEffectPlayer(UUID player) {
        this.player = player;
    }
}
