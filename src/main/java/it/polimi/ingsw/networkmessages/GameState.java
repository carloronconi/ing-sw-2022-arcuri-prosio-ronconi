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
    private final ArrayList<HashMap<UUID, ArrayList<PawnColor>>> clouds;
    private final ArrayList<HashMap<UUID, ArrayList<PawnColor>>> islands;
    private final HashMap<UUID, EnumMap<PawnColor, Integer>> entrances;
    private final HashMap<UUID, EnumMap<PawnColor, Integer>> diningRooms;
    private final HashMap<UUID, ArrayList<Integer>> assistantDecks;
    private final HashMap<UUID, Integer> coinsMap;
    private final HashMap<UUID, Boolean> characterCards;
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
        coinsMap = gameModel.getCoinsMap();
        characterCards = gameModel.getAvailableCharacterCardIds();
        playedAssistantCards = gameModel.getPlayedAssistantCards();
        motherNaturePosition = gameModel.getMotherNaturePosition();
        islandOwners = gameModel.getIslandOwners();
        nicknames = gameModel.getPlayerNicknames();
    }

    public int getBag() {
        return bag;
    }

    public EnumMap<PawnColor, UUID> getProfessorOwners() {
        return professorOwners;
    }

    public ArrayList<HashMap<UUID, ArrayList<PawnColor>>> getClouds() {
        return clouds;
    }

    public ArrayList<HashMap<UUID, ArrayList<PawnColor>>> getIslands() {
        return islands;
    }

    public HashMap<UUID, EnumMap<PawnColor, Integer>> getEntrances() {
        return entrances;
    }

    public HashMap<UUID, EnumMap<PawnColor, Integer>> getDiningRooms() {
        return diningRooms;
    }

    public HashMap<UUID, ArrayList<Integer>> getAssistantDecks() {
        return assistantDecks;
    }

    public HashMap<UUID, Integer> getCoinsMap() {
        return coinsMap;
    }

    public HashMap<UUID, Boolean> getCharacterCards() {
        return characterCards;
    }

    public HashMap<UUID, Integer> getPlayedAssistantCards() {
        return playedAssistantCards;
    }

    public UUID getMotherNaturePosition() {
        return motherNaturePosition;
    }

    public HashMap<UUID, UUID> getIslandOwners() {
        return islandOwners;
    }

    public ArrayList<String> getNicknames() {
        return nicknames;
    }
}
