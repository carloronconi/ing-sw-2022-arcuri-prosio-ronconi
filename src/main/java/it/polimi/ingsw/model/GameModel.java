package it.polimi.ingsw.model;

import it.polimi.ingsw.model.charactercards.Character;
import it.polimi.ingsw.model.charactercards.CharacterFactory;
import it.polimi.ingsw.model.charactercards.Messenger;
import it.polimi.ingsw.model.studentmanagers.Bag;
import it.polimi.ingsw.model.studentmanagers.Cloud;
import it.polimi.ingsw.model.studentmanagers.IslandManager;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class GameModel {
    private final ArrayList<Player> players;
    private final IslandManager islandManager;
    private final ProfessorManager professorManager;
    private final Bag bag;
    private final ArrayList<Cloud> clouds;
    private Map<Player, Integer> playedCards;
    private int bank;
    private final ArrayList<Character> characters;
    private Player cheeseMerchantEffectPlayer;
    private int messengerEffect;

    public GameModel(boolean expertMode){
        players=new ArrayList<>();
        bag=new Bag();
        islandManager=new IslandManager(bag);
        professorManager=new ProfessorManager();
        clouds=new ArrayList<>();
        messengerEffect = 0;
        bank = expertMode? 20 : 0;
        CharacterFactory factory = new CharacterFactory(bag, islandManager, this, players);
        characters = new ArrayList<>();
        for (int i = 0; i<3; i++){
            characters.add(factory.createUninstantiatedCharacter());
        }
    }

    //TODO when all players are added create method to initialize clouds and initialize playedCards map

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
    }

    /**
     * Updates the correspondence between teachers and players in professorManager
     */
    private void updateProfessorManager(){

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
    }

    /**
     * this method allows the player to play the card identified by the number passed in entry.
     * It is verified that the chosen card has not already been played and therefore placed in the discard pile
     * @param idPlayer id of the player who will have to play the considered card
     * @param cardNumber number of the card to be played
     * @throws IllegalArgumentException if card already played by someone else in current turn
     */
    public void playAssistantCard(UUID idPlayer, int cardNumber) throws IllegalArgumentException, NoSuchFieldException {
        for (Integer playedCard : playedCards.values()) {
            if (playedCard.equals(cardNumber)) {
                throw new IllegalArgumentException();
            }
        }

        Player player = ConverterUtility.idToElement(idPlayer, players);
        player.playAssistantCard(cardNumber);
        playedCards.put(player, cardNumber);
    }

    /**
     * returns the number of towers owned by the player passed in as input
     * @param idPlayer id of the player on which the number of towers will be checked
     * @return number of towers remaining
     */
    public int getNumOfTowers(UUID idPlayer){
        int howManyPlayers=0;
        for(Player player : players){
            if(player.getId().equals(idPlayer)){
                howManyPlayers++;
            }
        }
        if(players.size()==3){
            return 6-howManyPlayers;
        }else
            return 8-howManyPlayers;


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

    }

    /**
     * this method counts the number of islands or groups of islands in the game
     * @return the number of islands
     */
    public int countIslands(){
        return islandManager.countIslands();
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
    public Character payAndGetCharacter(UUID player, UUID character) throws IllegalArgumentException, NoSuchFieldException {
        int playerIndex = ConverterUtility.idToIndex(player, players);
        Player p = players.get(playerIndex);
        int characterIndex = ConverterUtility.idToIndex(character, characters);
        Character c = characters.get(characterIndex);

        if (p.getNumOfCoins()<c.getCost()) throw new IllegalArgumentException("Player doesn't have enough coins to use character");
        p.payCoins(c.getCost());
        bank+=c.getCost();

        return c;
    }

    public void assertCheeseMerchantEffect(UUID player) throws NoSuchFieldException {
        cheeseMerchantEffectPlayer = ConverterUtility.idToElement(player, players);
    }

    public void moveMotherNature(int steps, UUID playerId) throws NoSuchFieldException {
        Player player = ConverterUtility.idToElement(playerId,players);
        if (playedCards.get(player) + messengerEffect < steps) throw new IllegalArgumentException("Not enough steps in the card played");
        islandManager.moveMotherNature(steps);
        messengerEffect = 0;
    }
}
