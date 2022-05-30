package it.polimi.ingsw.networkmessages.modelevents;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.cliview.Bullet;
import it.polimi.ingsw.cliview.Color;
import it.polimi.ingsw.cliview.Matrix;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TowerColor;
import it.polimi.ingsw.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.model.charactercards.Juggler;
import it.polimi.ingsw.model.charactercards.Monk;
import it.polimi.ingsw.model.charactercards.Princess;

import java.io.Serializable;
import java.util.*;

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
    private final HashMap<UUID, UUID> islandOwners;  //first UUID of the island and second of the player
    private final LinkedHashMap<UUID, String> nicknames;
    private final LinkedHashMap<UUID, Integer> numOfTowersUsed;
    private final LinkedHashMap<UUID, TowerColor> colorPlayersTowers;

    private final LinkedHashMap<UUID, Integer> islandsSize; //UUID of the islands and their size
    private final LinkedHashMap<UUID, Boolean> banOnIslands; //UUID of the islands and true or false if they are banned or not

    private final HashMap<AvailableCharacter, ArrayList<PawnColor>> characterCardsStudents = new HashMap<>();





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

        numOfTowersUsed = new LinkedHashMap<>();
        for (UUID id : nicknames.keySet()){
            numOfTowersUsed.put(id, gameModel.getNumOfTowers(id));
        }

        colorPlayersTowers = new LinkedHashMap<>();
        for (Player player : gameModel.getPlayers()){
            colorPlayersTowers.put(player.getId(), player.getTowerColor());
        }

        islandsSize = gameModel.islandsSize();
        banOnIslands = gameModel.banOnIslands();



    }








    public int getBag() {
        return bag;
    }

    public HashMap<UUID, ArrayList<PawnColor>> getClouds() {
        return clouds;
    }

    public HashMap<UUID, ArrayList<PawnColor>> getIslands() {
        return islands;
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
        /*for (UUID c: clouds.keySet()){
            sb.append(c +" = " + clouds.get(c) +"\n                  ");
        }*/
        int numCloud=0;
        for (UUID id : clouds.keySet()){
            ++numCloud;
            sb.append(numCloud + ": ");
            //ArrayList<Bullet> cloud = new ArrayList<>();
            for (PawnColor color : clouds.get(id)){
                sb.append(new Bullet(Color.pawnColorConverter(color)));
                sb.append(" ");
                //cloud.add(new Bullet(Color.pawnColorConverter(color)));
            }
            //sb.append(cloud);
            sb.append("\t\t");

        }

        /*FATTO DA CARLO
        sb.append("\nislands:          ");
        for (UUID i: islands.keySet()){
            sb.append(i +" = " + islands.get(i));
            sb.append(" | owner = ");
            sb.append(islandOwners.get(i) == null? "none" : nicknames.get(i));
            if(motherNaturePosition==i) sb.append(" <-- Mother nature position");
            sb.append("\n                  ");
        }*/

        sb.append("\nislands:          \n");
        Matrix m = new Matrix(islandOwners, colorPlayersTowers, banOnIslands, motherNaturePosition, islandsSize, islands);
        sb.append(m);





        sb.append("\ncharacters:       ");
        sb.append(characterCards + " ");
        sb.append(characterCardsStudents + "\n");

        sb.append("\nprofessor owners: ");
        for (PawnColor prof : PawnColor.values()){
            //sb.append(prof + " = ");
            sb.append(new Bullet(Color.pawnColorConverter(prof)) + " = ");
            sb.append(professorOwners.get(prof) == null? "none":nicknames.get(professorOwners.get(prof)));
            sb.append("\n                  ");
        }

        sb.append("\n");

        for (UUID player : nicknames.keySet()){
            sb.append(nicknames.get(player) + "'s school board\n");
            //sb.append("entrance:         "+entrances.get(player));
            //sb.append("\ndining room:      "+diningRooms.get(player));
            sb.append("\nassistant deck:   "+assistantDecks.get(player));
            sb.append("\ncoins:            "+coinsMap.get(player));
            sb.append("\nplayed assistant: "+playedAssistantCards.get(player) +"\n\n");

            /*sb.append(
                    "|entrance          |prof t. \n" +
                    "|       |dining r. | |towers  \n");

            sb.append(matrices.get(player) + "\n\n");*/

            Matrix matrix = new Matrix(nicknames.size(), numOfTowersUsed.get(player), player, colorPlayersTowers.get(player), entrances.get(player), diningRooms.get(player), professorOwners);
            sb.append(matrix.toString());

        }

        return sb.toString();
    }
}
