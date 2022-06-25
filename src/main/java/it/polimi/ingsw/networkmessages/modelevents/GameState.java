package it.polimi.ingsw.networkmessages.modelevents;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.client.CliViewIdConverter;
import it.polimi.ingsw.cliview.Bullet;
import it.polimi.ingsw.cliview.Color;
import it.polimi.ingsw.cliview.Matrix;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.model.charactercards.Juggler;
import it.polimi.ingsw.model.charactercards.Monk;
import it.polimi.ingsw.model.charactercards.Princess;

import java.io.Serializable;
import java.util.*;

public class GameState implements Serializable, ModelEvent, Identifiable {
    private final int bag;
    private final UUID uuid = UUID.randomUUID();
    private final EnumMap<PawnColor, UUID> professorOwners;
    private final LinkedHashMap<UUID, ArrayList<PawnColor>> clouds;
    private final LinkedHashMap<UUID, ArrayList<PawnColor>> islands;
    private final LinkedHashMap<UUID, Integer> islandSizes;
    private final ArrayList<UUID> islandsWithBan;
    private final HashMap<UUID, EnumMap<PawnColor, Integer>> entrances;
    private final HashMap<UUID, EnumMap<PawnColor, Integer>> diningRooms;
    private final HashMap<UUID, ArrayList<Integer>> assistantDecks;
    private final HashMap<UUID, Integer> coinsMap;
    private final HashMap<AvailableCharacter, Boolean> characterCards;
    private final HashMap<UUID, Integer> playedAssistantCards;
    private final UUID motherNaturePosition;
    private final LinkedHashMap<UUID, UUID> islandOwners;  //first UUID of the island and second of the player
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
        islandSizes = gameModel.getIslandSizes();
        islandsWithBan = gameModel.getWhichIslandsHaveBan();
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
    public HashMap<UUID, Integer> getCoinsMap(){ return new HashMap<>(coinsMap); }

    public HashMap<AvailableCharacter, Boolean> getCharacterCards(){ return new HashMap<>(characterCards); }

    public LinkedHashMap<UUID, Integer> getNumOfTowersUsed(){ return new LinkedHashMap<>(numOfTowersUsed); }

    public LinkedHashMap<UUID, TowerColor> getColorPlayersTowers(){ return new LinkedHashMap<>(colorPlayersTowers); }

    public EnumMap<PawnColor, UUID> getProfessorOwners(){ return new EnumMap<>(professorOwners); }

    public LinkedHashMap<UUID, ArrayList<PawnColor>> getClouds() {
        return new LinkedHashMap<>(clouds);
    }

    public LinkedHashMap<UUID, ArrayList<PawnColor>> getIslands() {
        return new LinkedHashMap<>(islands);
    }

    public HashMap<UUID, String> getNicknames() {
        return new HashMap<>(nicknames);
    }

    public HashMap<AvailableCharacter, ArrayList<PawnColor>> getCharacterCardsStudents() {
        return new HashMap<>(characterCardsStudents);
    }
    public LinkedHashMap<UUID, Boolean> getBanOnIslands() {
        return new LinkedHashMap<>(banOnIslands);
    }

    public HashMap<UUID, EnumMap<PawnColor, Integer>> getEntrances() {
        return new HashMap<>(entrances);
    }

    public HashMap<UUID, EnumMap<PawnColor, Integer>> getDiningRooms() {
        return new HashMap<>(diningRooms);
    }

    public HashMap<UUID, Integer> getPlayedAssistantCards() {
        return new HashMap<>(playedAssistantCards);
    }

    public UUID getMotherNaturePosition() {
        return motherNaturePosition;
    }

    public LinkedHashMap<UUID, UUID> getIslandOwners() { return new LinkedHashMap<>(islandOwners);
    }
    public LinkedHashMap<UUID, Integer> getIslandSizes() {return new LinkedHashMap<>(islandSizes);}

    @Override
    public void processMessage(ViewInterface viewInterface, EventManager<ModelEvent> eventManager) {
        eventManager.notify(this);
    }

    public String drawGameState(UUID ofPlayer, ArrayList<UUID> initialIslandIds) {
        CliViewIdConverter converter = new CliViewIdConverter(this);

        StringBuilder sb = new StringBuilder("GAME STATE\n");
        sb.append("bag:              " + bag + "\n");
        sb.append("\nclouds:           ");
        /*
        for (UUID c: clouds.keySet()){
            sb.append(converter.idToName(c, CliViewIdConverter.converterSetting.CLOUD) +" = " + clouds.get(c) +"\n                  ");
        }*/

        for (UUID id : clouds.keySet()){
            sb.append(converter.idToName(id, CliViewIdConverter.ConverterSetting.CLOUD) + ": ");
            //ArrayList<Bullet> cloud = new ArrayList<>();
            for (PawnColor color : clouds.get(id)){
                sb.append(new Bullet(Color.pawnColorConverter(color)));
                sb.append(" ");
                //cloud.add(new Bullet(Color.pawnColorConverter(color)));
            }
            //sb.append(cloud);
            sb.append("\t\t");

        }
        /*
        sb.append("\nislands:          ");
        for (UUID i: islands.keySet()){
            sb.append(converter.idToName(i, CliViewIdConverter.converterSetting.ISLAND) +" : size = " + islandSizes.get(i) + " | colors = "+ islands.get(i) );
            if (characterCards!=null && islandsWithBan.contains(i)) sb.append(" | ban active!");
            sb.append(" | owner = ");
            sb.append(islandOwners.get(i) == null? "none" : nicknames.get(islandOwners.get(i)));
            if(motherNaturePosition==i) sb.append(" <-- Mother nature position");
            sb.append("\n                  ");
        }*/
        /*
        sb.append("\nislands:          \n");
        Matrix m = new Matrix(converter, islandOwners, colorPlayersTowers, banOnIslands, motherNaturePosition, islandsSize, islands);
        sb.append(m);*/
        sb.append("\n");
        Matrix mt = new Matrix(converter, islandOwners, colorPlayersTowers, banOnIslands, motherNaturePosition, islandsSize, initialIslandIds, islands);
        sb.append(mt);

        if (characterCards != null){
            sb.append("\ncharacters:       ");
            for (Map.Entry<AvailableCharacter, Boolean> character :characterCards.entrySet()){
                sb.append(character.getKey() + ": initial cost = " + character.getKey().getInitialCost() + " | increased = "+ character.getValue());
                if (characterCardsStudents.containsKey(character.getKey())){
                    sb.append(" | students = ");
                    for (PawnColor color : characterCardsStudents.get(character.getKey())){
                        sb.append(new Bullet(Color.pawnColorConverter(color)));
                        sb.append(" ");
                    }
                }
                sb.append("\n                  ");
            }/*
            sb.append(characterCards + " ");
            sb.append(characterCardsStudents + "\n");*/
        }

        /*
        sb.append("\nprofessor owners: ");
        for (PawnColor prof : PawnColor.values()){
            sb.append(prof + " = ");
            sb.append(professorOwners.get(prof) == null? "none":professorOwners.get(prof));
            sb.append("\n                  ");
        }*/

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
            /*
            sb.append("entrance:         "+entrances.get(player));
            sb.append("\ndining room:      "+diningRooms.get(player));*/
            if (player.equals(ofPlayer)) sb.append("\nassistant deck:   "+assistantDecks.get(player));
            sb.append("\ncoins:            "+coinsMap.get(player));
            String playedAssistant = playedAssistantCards.get(player) == null? "none" : Integer.toString(playedAssistantCards.get(player));
            sb.append("\nplayed assistant: "+playedAssistant +"\n\n");

            sb.append(
                    "|entrance          |prof t. \n" +
                    "|       |dining r. | |towers  \n");

            //sb.append(matrices.get(player) + "\n\n");

            Matrix matrix = new Matrix(nicknames.size(), numOfTowersUsed.get(player), player, colorPlayersTowers.get(player), entrances.get(player), diningRooms.get(player), professorOwners);
            sb.append(matrix.toString());
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public UUID getId() {
        return uuid;
    }
}
