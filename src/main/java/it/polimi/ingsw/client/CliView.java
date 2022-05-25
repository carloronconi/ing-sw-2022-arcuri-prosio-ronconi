package it.polimi.ingsw.client;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.controller.GameMode;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.model.charactercards.SwapperCharacter;
import it.polimi.ingsw.model.charactercards.effectarguments.EffectWithColor;
import it.polimi.ingsw.model.charactercards.effectarguments.EffectWithIsland;
import it.polimi.ingsw.model.charactercards.effectarguments.EffectWithPlayer;
import it.polimi.ingsw.networkmessages.modelevents.GameState;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CliView implements ViewInterface {
    private EventManager<ViewEvent> eventManager;
    private Scanner scanner;
    private GameState gameState;

    public CliView(ServerHandler handler) {
        eventManager = new EventManager<>();
        eventManager.subscribe(handler);
        scanner = new Scanner(System.in);

    }

    private String askUserInput(String message, InputParser parser){
        while (true){
            System.out.println(message);
            String input = scanner.nextLine();
            while(input.isEmpty()){
                input = scanner.nextLine();
            }
            if (parser.isStringAccepted(input)){
                return input;
            } else {
                System.out.println("Input not accepted! Try again.");
            }
        }
    }

    @Override
    public void sendAcknowledgement() {
        System.out.println("Acknowledgement received by server");
    }

    @Override
    public void askPlayAgain() {

    }

    @Override
    public void chooseCharacter() {

        String textualCharacter = askUserInput("Choose a character card or write NO:", s->{
            if (s.equals("NO")) return true;
            try{
                AvailableCharacter c = AvailableCharacter.valueOf(s.toUpperCase());
                return true;
            } catch (IllegalArgumentException e){
                return false;
            }
        });

        if (textualCharacter.equals("NO")){
            eventManager.notify(new ChosenCharacter(null));
            return;
        }

        AvailableCharacter characterEnum = AvailableCharacter.valueOf(textualCharacter.toUpperCase());

        eventManager.notify(new ChosenCharacter(characterEnum));
    }

    @Override
    public void chooseCloud() {

        String cloud = askUserInput("Choose a cloud id:", s->{
            ArrayList<UUID> cloudIds = new ArrayList<>();
            for (HashMap<UUID, ArrayList<PawnColor>> c : gameState.getClouds()) {
                for (UUID id : c.keySet()){
                    cloudIds.add(id);
                }
            }
            UUID uuid = null;
            try{
                uuid = UUID.fromString(s);
            } catch (IllegalArgumentException e){
                return false;
            }

            return cloudIds.contains(uuid);
        });

        UUID uuid = UUID.fromString(cloud);

        eventManager.notify(new ChosenCloud(uuid));
    }

    @Override
    public void getAssistantCard() {
        String text = askUserInput("Choose an Assistant Card:", s->{
            try{
                int number = Integer.parseInt(s);
                return number>=1 && number <=10;
            } catch (NumberFormatException e){
                return false;
            }

        });
        int card = Integer.parseInt(text);
        eventManager.notify(new SetAssistantCard(card));

    }

    @Override
    public void invalidAssistantCard() {
        System.out.println("Invalid assistant card! Try again:");
        getAssistantCard();
    }

    @Override
    public void getNickname() {
        String text = askUserInput("Nickname?", s->true);
        eventManager.notify(new SetNickname(text));
    }

    @Override
    public void getPreferences() {
        String text = askUserInput("Easy or hard? (E/H)", s-> (s.equals("E") || s.equals("H")));

        GameMode gameMode = text.equals("H")? GameMode.HARD : GameMode.EASY;

        String numString = askUserInput("Number of players?", s->(s.equals("2") || s.equals("3")));

        int numOfPlayers = Integer.parseInt(numString);

        eventManager.notify(new SetPreferences(numOfPlayers, gameMode));
    }

    @Override
    public void letsPlay(){
        System.out.println("Waiting for other players...");
        eventManager.notify(new ReadyToPlay());
    }


    @Override
    public void playerTurn() {

    }

    @Override
    public void invalidCharacterChoice() {
        System.out.println("Invalid character choice! Try again:");
        chooseCharacter();
    }

    @Override
    public void invalidMNMove() {
        System.out.println("Invalid mother nature move! Try again:");
        moveMotherNature();
    }

    @Override
    public void invalidNickname() {
        System.out.println("Invalid nickname! Try again:");
        getNickname();
    }

    @Override
    public void invalidStudentMove() {
        System.out.println("Invalid student move! Try again:");
        moveStudent();
    }

    @Override
    public void moveMotherNature() {
        String text = askUserInput("How many mother nature steps?", s->{
            try{
                int number = Integer.parseInt(s);
                return number>=1 && number <=5;
            } catch (NumberFormatException e){
                return false;
            }
        });
        int steps = Integer.parseInt(text);
        eventManager.notify(new MovedMotherNature(steps));
    }

    @Override
    public void moveStudent() {
        String textualColor = askUserInput("Choose a color of student to be moved:", s->{
            try{
                PawnColor color = PawnColor.valueOf(s.toUpperCase());
                return true;
            } catch (IllegalArgumentException e){
                return false;
            }
        });

        PawnColor color = PawnColor.valueOf(textualColor.toUpperCase());

        String textualChoice = askUserInput("Move the student to dining room or island? (D/I):", s->{
            return (s.equalsIgnoreCase("D") || s.equalsIgnoreCase("I"));
        });

        boolean isIsland = textualChoice.equalsIgnoreCase("I");

        UUID islandId = null;
        if(isIsland){
            String textualIsland = askUserInput("Choose the island (id):", s->{
                ArrayList<UUID> islandIds = new ArrayList<>();
                for (HashMap<UUID, ArrayList<PawnColor>> island : gameState.getIslands()) {
                    for (UUID id : island.keySet()){
                        islandIds.add(id);
                    }
                }
                UUID uuid = null;
                try{
                    uuid = UUID.fromString(s);
                } catch (IllegalArgumentException e){
                    return false;
                }

                return islandIds.contains(uuid);
            });

            islandId = UUID.fromString(textualIsland);

        }

        eventManager.notify(new MovedStudent(color, islandId));
    }

    @Override
    public void gameOver() {

    }

    @Override
    public void getCharacterSettings(AvailableCharacter forCharacter) {

        Class<?> characterClass = forCharacter.getCharacterClass();

        PawnColor color = null;
        UUID island = null;
        UUID player = null;
        PawnColor giveColor = null;
        PawnColor takeColor = null;

        InputParser colorInputParser = new InputParser() {
            @Override
            public boolean isStringAccepted(String input) {
                try{
                    PawnColor col = PawnColor.valueOf(input.toUpperCase());
                    return true;
                } catch (IllegalArgumentException e){
                    return false;
                }
            }
        };

        if (characterClass.isInstance((EffectWithColor) c -> {})){
            String text = askUserInput("Select a color for the character effect:", colorInputParser);

            color = PawnColor.valueOf(text.toUpperCase());
        }
        if (characterClass.isInstance((EffectWithIsland) i -> {})){
            String text = askUserInput("Select an island for the character effect:", s->{
                ArrayList<UUID> islandIds = new ArrayList<>();
                for (HashMap<UUID, ArrayList<PawnColor>> isl : gameState.getIslands()) {
                    for (UUID id : isl.keySet()){
                        islandIds.add(id);
                    }
                }
                UUID uuid = null;
                try{
                    uuid = UUID.fromString(s);
                } catch (IllegalArgumentException e){
                    return false;
                }

                return islandIds.contains(uuid);
            });

            island = UUID.fromString(text);

        }
        if (characterClass.isInstance((EffectWithPlayer) i -> {})){
            String text = askUserInput("Select a player for the character effect:", s->{
                ArrayList<UUID> playerIds = new ArrayList<>();
                for (UUID p : gameState.getEntrances().keySet()) {
                    playerIds.add(p);
                }
                UUID uuid = null;
                try{
                    uuid = UUID.fromString(s);
                } catch (IllegalArgumentException e){
                    return false;
                }

                return playerIds.contains(uuid);
            });

            player = UUID.fromString(text);
        }
        if (characterClass.isInstance(new SwapperCharacter(0, null, 0) {
            public void useEffect() throws IllegalStateException, NoSuchFieldException {}
        })){
            String giveText = askUserInput("Select color to be given for the character effect:", colorInputParser);
            giveColor = PawnColor.valueOf(giveText);

            String takeText = askUserInput("Select color to be taken for the character effect:", colorInputParser);
            takeColor = PawnColor.valueOf(takeText);
        }

        eventManager.notify(new SetCharacterSettings(color, player, island, giveColor, takeColor));

    }


    public void update(ModelEvent modelEvent) {
        if(modelEvent instanceof GameState){
            gameState = (GameState) modelEvent;
            System.out.println(modelEvent.toString());
        }

    }
}
