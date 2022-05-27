package it.polimi.ingsw.networkmessages.modelevents;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.cliview.Color;
import it.polimi.ingsw.cliview.Matrix;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ProfessorManager;
import it.polimi.ingsw.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.model.charactercards.Juggler;
import it.polimi.ingsw.model.charactercards.Monk;
import it.polimi.ingsw.model.charactercards.Princess;
import it.polimi.ingsw.model.studentmanagers.Bag;
import it.polimi.ingsw.model.studentmanagers.Cloud;
import it.polimi.ingsw.model.studentmanagers.IslandTile;
import it.polimi.ingsw.model.studentmanagers.*;
import it.polimi.ingsw.networkmessages.viewevents.ViewEvent;

import java.io.Serializable;
import java.util.*;
import java.util.*;

import static it.polimi.ingsw.model.PawnColor.*;

public class GameState implements Serializable, ModelEvent {
    private final int bag;
    private final EnumMap<PawnColor, UUID> professorOwners;
    private final LinkedHashMap<UUID, ArrayList<PawnColor>> clouds;
    private final LinkedHashMap<UUID, ArrayList<PawnColor>> islands;
    private final HashMap<UUID, EnumMap<PawnColor, Integer>> entrances;
    private final HashMap<UUID, EnumMap<PawnColor, Integer>> diningRooms;
    private final HashMap<UUID, ArrayList<Integer>> assistantDecks;
    private final HashMap<UUID, Integer> coinsMap;
    private final HashMap<AvailableCharacter, Boolean> characterCards;
    private final HashMap<UUID, Integer> playedAssistantCards;
    private final UUID motherNaturePosition;
    private final HashMap<UUID, UUID> islandOwners;
    private final LinkedHashMap<UUID, String> nicknames;

    private final HashMap<AvailableCharacter, ArrayList<PawnColor>> characterCardsStudents = new HashMap<>();



    private ArrayList<Matrix> matrix;


    public GameState(GameModel gameModel) {
        bag = gameModel.countStudentsInBag();
        professorOwners = gameModel.getProfessorOwners();
        clouds = gameModel.getClouds();
        islands = gameModel.getIslands();
        entrances = gameModel.getEntrances();
        diningRooms = gameModel.getDiningRooms();
        assistantDecks = gameModel.getDecks();
        coinsMap = gameModel.getCoinsMap();
        characterCards = gameModel.getAvailableCharacterCards();
        playedAssistantCards = gameModel.getPlayedAssistantCards();
        motherNaturePosition = gameModel.getMotherNaturePosition();
        islandOwners = gameModel.getIslandOwners();
        nicknames = gameModel.getPlayerNicknames();
        if(characterCards != null){
            if(characterCards.containsKey(AvailableCharacter.MONK)){
                characterCardsStudents.put(AvailableCharacter.MONK, Monk.getStudents());
            }
            if(characterCards.containsKey(AvailableCharacter.JUGGLER)){
                characterCardsStudents.put(AvailableCharacter.JUGGLER, Juggler.getStudents());
            }
            if(characterCards.containsKey(AvailableCharacter.PRINCESS)){
                characterCardsStudents.put(AvailableCharacter.PRINCESS, Princess.getStudents());
            }
        }



        matrix = new ArrayList<>();

        for (String s : nicknames.values()){
            for (Player p : gameModel.getPlayers()){
                if (s.equals(p.getNickname())){
                    matrixCreation(nicknames.size(), gameModel.getNumOfTowers(p.getId()), p, professorOwners);
                }
            }
        }

    }

    public ArrayList<Matrix> getMatrix(){
        return matrix;
    }


    public void matrixCreation(int numPlayers, int numTowersUsed, Player player, EnumMap<PawnColor, UUID> professorOwners){
        matrix.add(new Matrix(numPlayers, numTowersUsed, player, professorOwners));
    }







    public int getBag() {
        return bag;
    }

    public EnumMap<PawnColor, UUID> getProfessorOwners() {
        return professorOwners;
    }

    public HashMap<UUID, ArrayList<PawnColor>> getClouds() {
        return clouds;
    }

    public HashMap<UUID, ArrayList<PawnColor>> getIslands() {
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

    public HashMap<AvailableCharacter, Boolean> getCharacterCards() {
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

    public HashMap<UUID, String> getNicknames() {
        return nicknames;
    }

    @Override
    public void processMessage(ViewInterface viewInterface, EventManager<ModelEvent> eventManager) {
        eventManager.notify(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("GAME STATE\n");
        sb.append("bag:              " + bag + "\n");
        sb.append("\nclouds:           ");
        for (UUID c: clouds.keySet()){
            sb.append(c +" = " + clouds.get(c) +"\n                  ");
        }
        sb.append("\nislands:          ");
        for (UUID i: islands.keySet()){
            sb.append(i +" = " + islands.get(i));
            sb.append(" | owner = ");
            sb.append(islandOwners.get(i) == null? "none" : nicknames.get(i));
            if(motherNaturePosition==i) sb.append(" <-- Mother nature position");
            sb.append("\n                  ");
        }

        sb.append("\ncharacters:       ");
        sb.append(characterCards + " ");
        sb.append(characterCardsStudents + "\n");

        sb.append("\nprofessor owners: ");
        for (PawnColor prof : PawnColor.values()){
            sb.append(prof + " = ");
            sb.append(professorOwners.get(prof) == null? "none":professorOwners.get(prof));
            sb.append("\n                  ");
        }

        sb.append("\n");

        for (UUID player : nicknames.keySet()){
            sb.append(nicknames.get(player) + "'s school board\n");
            sb.append("entrance:         "+entrances.get(player));
            sb.append("\ndining room:      "+diningRooms.get(player));
            sb.append("\nassistant deck:   "+assistantDecks.get(player));
            sb.append("\ncoins:            "+coinsMap.get(player));
            sb.append("\nplayed assistant: "+playedAssistantCards.get(player) +"\n\n");

        }
        /*
        return "GameState{" +
                "\nbag=" + bag +
                ", \nprofessorOwners=" + professorOwners +
                ", \nclouds=" + clouds +
                ", \nislands=" + islands +
                ", \nentrances=" + entrances +
                ", \ndiningRooms=" + diningRooms +
                ", \nassistantDecks=" + assistantDecks +
                ", \ncoinsMap=" + coinsMap +
                ", \ncharacterCards=" + characterCards +
                ", \ncharacterCards=" + characterCardsStudents +
                ", \nplayedAssistantCards=" + playedAssistantCards +
                ", \nmotherNaturePosition=" + motherNaturePosition +
                ", \nislandOwners=" + islandOwners +
                ", \nnicknames=" + nicknames +
                "}";*/
        return sb.toString();
    }
}
