package it.polimi.ingsw.networkmessages;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.ProfessorManager;
import it.polimi.ingsw.model.studentmanagers.Bag;
import it.polimi.ingsw.model.studentmanagers.Cloud;
import it.polimi.ingsw.model.studentmanagers.IslandTile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.UUID;

public class GameState implements Serializable {
    private final int bag;
    private final EnumMap<PawnColor, UUID> professorOwners;
    private final ArrayList<EnumMap<PawnColor, Integer>> clouds;
    private final ArrayList<EnumMap<PawnColor, Integer>> islands;
    private final HashMap<UUID, EnumMap<PawnColor, Integer>> entrances;
    private final HashMap<UUID, EnumMap<PawnColor, Integer>> diningRooms;
    private final HashMap<UUID, ArrayList<Integer>> assistantDecks;
    private final HashMap<UUID, Integer> coinsMap;
    private final ArrayList<UUID> characterCards;
    private final HashMap<UUID, Integer> playedAssistantCards;
    private final UUID motherNaturePosition;
    private final HashMap<UUID, UUID> islandOwners;
    private final ArrayList<String> nicknames;

    public GameState(GameModel gameModel) {
        bag = gameModel.countStudentsInBag();
        professorOwners = gameModel.getProfessorOwners();
        clouds = gameModel.getClouds();
        islands = gameModel.getIslands();
        entrances = gameModel.getEntrances();
        diningRooms = gameModel.getDiningRooms();
        assistantDecks = gameModel.getDecks();
    }
}
