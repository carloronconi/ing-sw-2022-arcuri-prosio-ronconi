package it.polimi.ingsw.server.model;

import it.polimi.ingsw.networkmessages.modelevents.GameState;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.server.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.server.model.charactercards.Character;
import it.polimi.ingsw.server.model.charactercards.CharacterFactory;
import it.polimi.ingsw.server.model.charactercards.Messenger;
import it.polimi.ingsw.server.model.studentmanagers.*;
import it.polimi.ingsw.utilities.EventManager;

import java.util.*;

public class GameModel {
    private final ArrayList<Player> players;
    private final IslandManager islandManager;
    private final ProfessorManager professorManager;
    private final Bag bag;
    private final ArrayList<Cloud> clouds;
    private final Map<Player, Integer> playedCards;
    private int bank;
    private ArrayList<Character> characters = null;
    private Player cheeseMerchantEffectPlayer;
    private int messengerEffect;
    private final EventManager<ModelEvent> eventManager;

    public GameModel(boolean expertMode, List<String> playerNicknames, EventManager<ModelEvent> eventManager){
        if (playerNicknames.size() < 2 || playerNicknames.size() > 3) throw new IllegalArgumentException("Number of players not supported");

        players=new ArrayList<>();
        bag=new Bag();
        professorManager=new ProfessorManager();
        islandManager=new IslandManager(bag, professorManager);
        clouds=new ArrayList<>();
        playedCards = new HashMap<>();
        this.eventManager = eventManager; //eventManager should already have subscribers
        messengerEffect = 0;
        bank = expertMode? 20 : 0;
        if (expertMode){
            CharacterFactory factory = new CharacterFactory(bag, islandManager, this, players);
            characters = new ArrayList<>();
            for (int i = 0; i<3; i++){
                characters.add(factory.createUninstantiatedCharacter());
            }
        }

        //initialize clouds in a number according to the number of players
        int numOfClouds = 0;
        if (playerNicknames.size()==2) numOfClouds = 2;
        else if (playerNicknames.size() == 3) numOfClouds = 3;
        for (int i = 0; i < numOfClouds; i++) {
            Cloud cloud = new Cloud(bag);
            clouds.add(cloud);
        }

        //initialize players and their entrance and diningRoom
        int numOfStudents = 0;
        if (playerNicknames.size()==2) numOfStudents = 7;
        else if (playerNicknames.size() == 3) numOfStudents = 9;
        for (String nickname : playerNicknames){
            Entrance entrance = new Entrance(bag,clouds,numOfStudents);
            DiningRoom diningRoom = new DiningRoom(entrance);
            Player player = new Player(entrance, diningRoom, nickname);
            players.add(player);
        }

        eventManager.notify(new GameState(this));

    }

    public ArrayList<Player> getPlayers(){
        return new ArrayList<>(players);
    }

    /**
     * transfers a student of a certain color from a player's entrance to the corresponding diningRoom.
     * If the bank has a number of coins greater than 0 and the number of students of that color is multiples of 3,
     * then one coin from the bank is transferred to the player. Subsequently, the correspondence between professors
     * and players in professorManager is updated
     * @param pawnColor color of the student that will be transferred from the entrance to the dining room
     * @param playerId player from whom the student will be transferred
     */
    public void moveStudentToDining(PawnColor pawnColor, UUID playerId) throws NoSuchFieldException {
        int playerIndex=ConverterUtility.idToIndex(playerId, players);
        if(players.get(playerIndex).moveStudentToDining(pawnColor,bank>0)){
            bank--;
        }
        updateProfessorManager();
        eventManager.notify(new GameState(this));
    }

    /**
     * Updates the correspondence between teachers and players in professorManager
     */
    public void updateProfessorManager(){

        Player supportPlayer =null;
        for(PawnColor pawnColor : PawnColor.values()){
            /*this is the case where the value associated with a color has a value that is defined by a player.
              This player is taken as a support player for the comparison so has to be replaced in case there is
              another player with a greater number of students of the same color or remain in case he is the one
              with the greater number */
            if(professorManager.getProfessorOwner(pawnColor)!=null){
                supportPlayer=professorManager.getProfessorOwner(pawnColor);

                for(Player player : players){
                    int cheeseMerchantBonus = (player == cheeseMerchantEffectPlayer)? 1: 0;

                    if(player.getDiningRoom().count(pawnColor) + cheeseMerchantBonus>supportPlayer.getDiningRoom().count(pawnColor)){
                        supportPlayer=player;
                    }
                }
                professorManager.setProfessorOwner(pawnColor, supportPlayer);
            }else{
                /*this is the case when there is no player associated with the color under consideration.
                  What you do is set a negative value as the number of students.
                  Then you iterate over all the players and the previously set value allows you to choose
                  the first player as a support player for the comparison, so at the first iteration
                  you enter the first branch. For the other iterations, if the player has a higher number of students
                  then he substitutes himself, instead if the player has a lower number of students
                  the substitution does not take place. If the player has an even number of students,
                  it means that neither of them will own the professor; however, the number of students will be saved
                  because if there is a third player with a higher number of students than the previous ones,
                  then he will own the professor.
                 */
                int studentsNumber=-1;

                for(Player player : players){
                    if(player.getDiningRoom().count(pawnColor)>studentsNumber){
                        supportPlayer=player;
                        studentsNumber=player.getDiningRoom().count(pawnColor);
                    }else if(player.getDiningRoom().count(pawnColor)==studentsNumber){
                        supportPlayer=null;
                    }
                }
                professorManager.setProfessorOwner(pawnColor, supportPlayer);
            }

        }
        cheeseMerchantEffectPlayer = null;
        eventManager.notify(new GameState(this));
    }

    /**
     * updates messengerEffect to be active for the next turn
     */
    public void useMessengerEffect(){

        boolean found = false;
        for (Character c : characters){
            if (c instanceof Messenger) {
                found = true;
                break;
            }
        }
        if(found) messengerEffect = 2;
        else throw new IllegalStateException("There is no messenger in the characters of this match");
        eventManager.notify(new GameState(this));
    }

    /**
     * this method considers a player and an island and transfers all students from the cloud in question
     * to the player's entrance
     * @param whichCloud cloud from which students are transferred
     * @param idPlayer player who receives students
     */
    public void moveCloudToEntrance(UUID whichCloud, UUID idPlayer) throws NoSuchFieldException {
        int playerIndex=ConverterUtility.idToIndex(idPlayer, players);
        players.get(playerIndex).getEntrance().fill(whichCloud);
        eventManager.notify(new GameState(this));
    }

    /**
     * this method allows the player to play the card identified by the number passed in entry.
     * It is verified that the chosen card has not already been played and therefore placed in the discard pile
     * @param idPlayer id of the player who will have to play the considered card
     * @param cardNumber number of the card to be played
     * @throws IllegalArgumentException if card already played by someone else in current turn
     */
    public void playAssistantCard(UUID idPlayer, int cardNumber) throws IllegalArgumentException, NoSuchFieldException {
        if (isAssistantCardIllegal(idPlayer,cardNumber)) throw new IllegalModelMoveException();

        Player player = ConverterUtility.idToElement(idPlayer, players);
        player.playAssistantCard(cardNumber);
        playedCards.put(player, cardNumber);
        eventManager.notify(new GameState(this));
    }

    public boolean isAssistantCardIllegal(UUID idPlayer, int cardNumber) throws NoSuchFieldException {
        Player player = ConverterUtility.idToElement(idPlayer, players);
        if (!player.getDeck().contains(cardNumber)) return true; //the card is not contained in the player's deck
        else{ //the card is contained in the player's deck
            if(!playedCards.containsValue(cardNumber)) return false; //the card has not been played by other player in same turn
            else{ //the card is contained in the player's deck and has been played by other player in same turn
                for (int card : player.getDeck()){
                    if (card == cardNumber) continue;
                    if (!playedCards.containsValue(card)) return true; //there is a card in the deck, different from cardNumber,
                                                                       //that has not been played by other player in the same turn
                }
                return false;
            }
        }


    }

    /**
     * returns the number of towers owned by the player passed in as input
     * @param idPlayer id of the player on which the number of towers will be checked
     * @return number of towers already placed by the player
     */
    public int getNumOfTowers(UUID idPlayer){
        Player player;
        try {
            player = ConverterUtility.idToElement(idPlayer, players);
        } catch (NoSuchFieldException e) {
            throw new IllegalModelMoveException(e.getMessage());
        }
        int howManyIslands=0;
        for(IslandTile islandTile : islandManager.getIslands()){
            if (islandTile.getOwner()!=null) {
                if (islandTile.getOwner().equals(player)) {
                    howManyIslands += islandTile.getSize();
                }
            }
        }
        return howManyIslands;

    }

    /**
     * moves a student of the color passed into the entrance from the entrance of the considered player
     * to the chosen island
     * @param pawnColor color of the student to be transferred
     * @param idPlayer id of the player from which the student will be transferred
     * @param island island to which the student will be transferred
     */
    public void moveStudentToIsland(PawnColor pawnColor, UUID idPlayer, UUID island) throws NoSuchFieldException {
        int playerIndex=ConverterUtility.idToIndex(idPlayer,players);
        islandManager.moveStudentToIsland(pawnColor, island, players.get(playerIndex).getEntrance());
        eventManager.notify(new GameState(this));
    }

    /**
     * transfers a number of students, chosen on the basis of the number of players in the game,
     * from the bag to the clouds
     */
    public void fillAllClouds(){
        for(Cloud cloud : clouds){
            if(players.size()==2){
                cloud.fill(3);
            }else if(players.size()==3){
                cloud.fill(4);
            }
        }
        eventManager.notify(new GameState(this));
    }

    /**
     * this method counts the number of islands or groups of islands in the game
     * @return the number of islands
     */
    public int countIslands(){
        return islandManager.countIslands();
    }

    /**
     * this method creates a LinkedHashMap, used in the GameState, in which it associates the id of each island to its size
     * referring to the unification of several islands
     * @return the created map
     */
    public LinkedHashMap<UUID, Integer> islandsSize(){
        LinkedHashMap<UUID, Integer> map = new LinkedHashMap<>();

        for (int i=0; i<islandManager.getIslands().size(); i++){
            map.put(islandManager.getIsland(i).getId(), islandManager.getIsland(i).getSize());
        }

        return map;
    }

    /**
     * this method creates a LinkedHashMap, used in the GameState, in which it associates the id of each island
     * to a boolean referring to the presence of the ban card on the island itself
     * @return the created map
     */
    public LinkedHashMap<UUID, Boolean> banOnIslands(){
        LinkedHashMap<UUID, Boolean> map = new LinkedHashMap<>();
        for (int i=0; i<islandManager.getIslands().size(); i++){
            map.put(islandManager.getIsland(i).getId(), islandManager.getIsland(i).ban);
        }

        return map;
    }

    /**
     * counts the number of cards remaining in the player's deck that is passed into the entrance
     * @param player id of the player on whom the check of the number of cards left in his deck is made
     * @return returns the number of cards in the player's deck
     */
    public int getDeckSize(UUID player) throws NoSuchFieldException {
        int playerIndex=ConverterUtility.idToIndex(player, players);
        return players.get(playerIndex).getDeckSize();
    }

    /**
     * this method returns the deck associated to the player in input
     * @param player whose deck you want to know
     * @return an ArrayList with integers referring to the cards in the deck
     * @throws NoSuchFieldException
     */
    public ArrayList<Integer> getDeck(UUID player) throws NoSuchFieldException{
        return ConverterUtility.idToElement(player, players).getDeck();
    }

    /**
     * this method creates a HashMap in which it associates each player's ID with their deck
     * @return the created map
     */
    public HashMap<UUID, ArrayList<Integer>> getDecks() {
        HashMap<UUID, ArrayList<Integer>> map = new HashMap<>();
        for (Player p : players){
            map.put(p.getId(), p.getDeck());
        }
        return map;
    }

    /**
     * counts the number of students left on the bag
     * @return the number of remaining students
     */
    public int countStudentsInBag(){ return bag.count();}

    /**
     * player chooses to get a certain character to use its special effect and pays with its coins
     * @param player who wants to purchase a character effect
     * @param character chosen by the player
     * @return character from which you can .useEffect()
     * @throws IllegalArgumentException if the player doesn't have enough coins to pay for the character
     * @throws NoSuchFieldException if the player or character id don't exist among the players/characters in the match
     */
    public Character payAndGetCharacter(UUID player, AvailableCharacter character) throws IllegalArgumentException, NoSuchFieldException {
        int playerIndex = ConverterUtility.idToIndex(player, players);
        Player p = players.get(playerIndex);
        int characterIndex = -1;
        for (int i = 0; i<characters.size(); i++){
            Character c = characters.get(i);
            if (c.getValue() == character) characterIndex = i;
        }
        if (characterIndex == -1) throw new NoSuchFieldException();
        Character c = characters.get(characterIndex);

        if (isCharacterCardIllegal(player, character)) throw new IllegalModelMoveException("Player doesn't have enough coins to use character");
        p.payCoins(c.getCurrentCost());
        bank+=c.getCurrentCost();
        eventManager.notify(new GameState(this));
        return c;

    }

    /**
     * this method decides whether a player can choose a character to use its effect
     * @param player who wants to use a character's effect
     * @param character chosen by the player
     * @return a boolean that decides whether the player can use the character's effect
     * @throws NoSuchFieldException
     */
    public boolean isCharacterCardIllegal(UUID player, AvailableCharacter character) throws NoSuchFieldException{
        int playerIndex = ConverterUtility.idToIndex(player, players);
        Player p = players.get(playerIndex);
        int characterIndex = -1;
        for (int i = 0; i<characters.size(); i++){
            Character c = characters.get(i);
            if (c.getValue() == character) characterIndex = i;
        }
        if (characterIndex == -1) throw new IllegalModelMoveException();
        Character c = characters.get(characterIndex);

        return (p.getNumOfCoins()<c.getCurrentCost()) ;
    }

    /*
    public int getCurrentCharacterPrice(AvailableCharacter character) throws NoSuchFieldException{
        int characterIndex = -1;
        for (int i = 0; i<characters.size(); i++){
            Character c = characters.get(i);
            if (c.getValue() == character) characterIndex = i;
        }
        if (characterIndex == -1) throw new NoSuchFieldException();
        Character c = characters.get(characterIndex);
        return c.getCost();
    }*/

    /**
     * called only by cheese merchant card
     * @param player owninng the merchant
     * @throws NoSuchFieldException if the player does not exist
     */
    public void assertCheeseMerchantEffect(UUID player) throws NoSuchFieldException {
        cheeseMerchantEffectPlayer = ConverterUtility.idToElement(player, players);
    }

    /**
     * called when a player wants to move mother nature of a certain number of steps
     * @param steps number of steps of mother nature on islands
     * @param playerId player who moves mother nature
     * @throws NoSuchFieldException if the player hasn't played a card with enough steps
     */
    public void moveMotherNature(int steps, UUID playerId) throws NoSuchFieldException {
        if (isMNMoveIllegal(steps, playerId)) throw new IllegalModelMoveException("Not enough steps in the card played");
        Player player = ConverterUtility.idToElement(playerId,players);
        islandManager.moveMotherNature(steps);
        messengerEffect = 0;
        System.out.println("moved mother nature");
        eventManager.notify(new GameState(this));
    }

    /**
     * this method decides whether the number of steps of MotherNature chosen by a player is correct
     * @param steps that MotherNature should take
     * @param playerId who has decided the steps that MotherNature should take
     * @return a boolean which confirm or not MotherNature's steps
     * @throws NoSuchFieldException
     */
    public boolean isMNMoveIllegal(int steps, UUID playerId) throws NoSuchFieldException {
        Player player = ConverterUtility.idToElement(playerId,players);
        int cardValue = playedCards.get(player)/2;
        if(playedCards.get(player)%2 !=0) cardValue++;
        return (cardValue + messengerEffect < steps);
    }

    /**
     * this method resets the values of the playedCards map in which players and the cards played by them are associated
     */
    public void clearPlayedAssistantCards(){
        playedCards.clear();
        eventManager.notify(new GameState(this));
    }

    /**
     * this method creates a map in which the professors and the ids of the players who own them are associated
     * @return the created map
     */
    public EnumMap<PawnColor, UUID> getProfessorOwners(){
        EnumMap<PawnColor, UUID> map = new EnumMap<>(PawnColor.class);
        for (PawnColor c : PawnColor.values()){
            Player owner = professorManager.getProfessorOwner(c);
            UUID id = null;
            if (owner!=null) id = owner.getId();
            map.put(c, id);
        }
        return map;
    }

    /**
     * this method creates a LinkedHashMap in which the IDs of the clouds and the color of the students
     * contained in them are associated
     * @return the created map
     */
    public LinkedHashMap<UUID, ArrayList<PawnColor>> getClouds(){
        LinkedHashMap<UUID, ArrayList<PawnColor>> map = new LinkedHashMap<>();
        for (Cloud cloud: clouds){
            ArrayList<PawnColor> colors = new ArrayList<>();
            ArrayList<PawnColor> colorsInCloud = new ArrayList<>();
            for (PawnColor color: PawnColor.values()){
                if (cloud.count(color)>0) colorsInCloud.add(color);
            }
            for (PawnColor color: colorsInCloud){
                for (int i = 0; i < cloud.count(color); i++) {
                    colors.add(color);
                }
            }
            map.put(cloud.getId(),colors);

        }
        return map;
    }

    /**
     * this method creates a LinkedHashMap in which the IDs of the clouds and the color of the students
     * contained in them are associated
     * @return the created map
     */
    public LinkedHashMap<UUID, ArrayList<PawnColor>> getIslands(){
        LinkedHashMap<UUID, ArrayList<PawnColor>> map = new LinkedHashMap<>();
        for (IslandTile island: islandManager.getIslands()){
            ArrayList<PawnColor> colors = new ArrayList<>();
            ArrayList<PawnColor> colorsInIsland = new ArrayList<>();
            for (PawnColor color: PawnColor.values()){
                if (island.count(color)>0) colorsInIsland.add(color);
            }
            for (PawnColor color: colorsInIsland){
                for (int i = 0; i < island.count(color); i++) {
                    colors.add(color);
                }
            }
            map.put(island.getId(),colors);

        }
        return map;
    }

    /**
     * this method creates a LinkedHashMap in which the IDs of the islands and their sizes are associated
     * @return the created map
     */
    public LinkedHashMap<UUID, Integer> getIslandSizes(){
        LinkedHashMap<UUID, Integer> map = new LinkedHashMap<>();
        for (IslandTile island : islandManager.getIslands()){
            map.put(island.getId(), island.getSize());
        }
        return map;
    }

    /**
     * this method creates an ArrayList which contains the IDs of the islands that have a ban
     * @return the created ArrayList
     */
    public ArrayList<UUID> getWhichIslandsHaveBan(){
        ArrayList<UUID> islandsWithBan = new ArrayList<>();
        for (IslandTile island: islandManager.getIslands()){
            if (island.ban) islandsWithBan.add(island.getId());
        }
        return islandsWithBan;
    }

    /**
     * this method returns a HashMap in which the ids of the entrances (id of the players actually) and an EnumMap,
     * which in turn contains the number of students of the various colors contained in the entrance, are associated
     * @return the HashMap
     */
    public HashMap<UUID, EnumMap<PawnColor, Integer>> getEntrances(){
        return getStudentCounterMap(Entrance.class);
    }

    /**
     * this method returns a HashMap in which the ids of the diningRooms (id of the players actually) and an EnumMap,
     * which in turn contains the number of students of the various colors contained in the diningRooms, are associated
     * @return the HashMap
     */
    public HashMap<UUID, EnumMap<PawnColor, Integer>> getDiningRooms(){
        return getStudentCounterMap(DiningRoom.class);
    }

    /**
     * this method creates a HashMap in which the id of the object passed in input (it must be an instance of Entrance or DiningRoom class)
     * are associated and an EnumMap, which in turn contains the number of students of the various colors contained
     * in the object, are associated
     * @param c object of which you want to know the id and the number of students of the various colors
     * @param <T> indicates the type of object that must be passed in input and this must extend the studentCounter class
     * @return the created HashMap
     * @throws IllegalArgumentException
     */
    private <T extends StudentCounter> HashMap<UUID, EnumMap<PawnColor, Integer>> getStudentCounterMap(Class<T> c) throws IllegalArgumentException{
        if (Entrance.class != c && DiningRoom.class != c) throw new IllegalArgumentException("Class either has to be Entrance or DiningRoom");
        HashMap<UUID, EnumMap<PawnColor, Integer>> playersMap = new HashMap<>();
        for (Player p : players){
            EnumMap<PawnColor, Integer>  colorsMap = new EnumMap<>(PawnColor.class);
            StudentCounter studentCounter = (Entrance.class == c)? p.getEntrance() : p.getDiningRoom();
            for (PawnColor color: PawnColor.values()){
                colorsMap.put(color, studentCounter.count(color));
            }
            playersMap.put(p.getId(), colorsMap);
        }
        return playersMap;
    }

    /**
     * this method creates a map in which the IDs of the players and the coins they own are associated
     * @return the created map
     */
    public HashMap<UUID, Integer> getCoinsMap(){
        HashMap<UUID, Integer> map = new HashMap<>();
        for (Player p : players){
            map.put(p.getId(), p.getNumOfCoins());
        }
        return map;
    }

    /**
     * this method creates a HashMap in which the characterCards present in the game and a boolean,
     * which indicates if its cost has been increased (if it has already been chosen by a player actually) are associated
     * @return the created map
     */
    public HashMap<AvailableCharacter, Boolean> getAvailableCharacterCards(){
        if (characters==null) return null;
        HashMap<AvailableCharacter, Boolean> map = new HashMap<>();
        for (Character c: characters){
            map.put(c.getValue(), c.isCostIncreased());
        }
        return map;
    }

    /**
     * this method creates a HashMap in which the IDs of the players and the cards played by them in the current turn
     * are associated
     * @return the created HashMap
     */
    public HashMap<UUID, Integer> getPlayedAssistantCards(){
        HashMap<UUID, Integer> map = new HashMap<>();
        for (Player player : playedCards.keySet()){
            map.put(player.getId(),playedCards.get(player));
        }
        return map;
    }

    /**
     * this method returns the id of the island motherNature is on
     * @return the id of the island
     */
    public UUID getMotherNaturePosition(){
        return islandManager.getMotherNaturePosition();
    }

    /**
     * this method creates a LinkedHashMap in which the IDs of the islands and the IDs of the players who own them
     * are associated
     * @return the created LinkedHashMap
     */
    public LinkedHashMap<UUID, UUID> getIslandOwners(){
        LinkedHashMap<UUID, UUID> map = new LinkedHashMap<>();
        for (IslandTile island: islandManager.getIslands()){
            UUID id = null;
            if (island.getOwner() != null) {
                id = island.getOwner().getId();
            }
            map.put(island.getId(), id);
        }
        return map;
    }

    /**
     * the method creates a LinkedHashMap in which ids of the players and their nicknames are associated
     * @return the created LinkedHashMap
     */
    public LinkedHashMap<UUID, String> getPlayerNicknames(){
        LinkedHashMap<UUID, String> map = new LinkedHashMap<>();
        for (Player p : players){
            map.put(p.getId(), p.getNickname());
        }
        return map;
    }

    public String toString(){
        String string = "";
        string += "Players: ";
        for (Player p : players){
            string+= p.getNickname() + "; ";
        }
        string += "\n" + bag;
        string += "\n" + professorManager;
        string += "\nClouds:\n    ";
        int i = 1;
        for (Cloud c : clouds){
            string += "[" + i + "] ";
            string += c + "\n    ";
            i++;
        }
        string += "\n" + islandManager;
        for (Player p : players){
            string += "\n" + p;
        }


        return string;
    }

    /**
     * this method creates an ArrayList which contains the ids of the players
     * @return the created ArrayList
     */
    public ArrayList<UUID> getPlayerIds(){
        ArrayList<UUID> list = new ArrayList<>();
        for (Player p : players){
            list.add(p.getId());
        }

        return list;
    }

    public void notifyListeners(){
        eventManager.notify(new GameState(this));
    }
}
