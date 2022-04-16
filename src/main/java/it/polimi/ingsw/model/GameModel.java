package it.polimi.ingsw.model;

import it.polimi.ingsw.model.studentmanagers.Bag;
import it.polimi.ingsw.model.studentmanagers.Cloud;
import it.polimi.ingsw.model.studentmanagers.IslandManager;

import java.util.ArrayList;
import java.util.UUID;

public class GameModel {
    private ArrayList<Player> players;
    private final IslandManager islandManager;
    private final ProfessorManager professorManager;
    private final Bag bag;
    private ArrayList<Cloud> clouds;
    private ArrayList<Integer> playedCards;
    private int bank;

    //creare altri costruttori in base alle differenti modalità del gioco oppure modificare quello esistente

    public GameModel(){
        players=new ArrayList<>();
        bag=new Bag();
        islandManager=new IslandManager(bag);
        professorManager=new ProfessorManager();
        clouds=new ArrayList<>();
    }

    //TODO when all players are added create method to inizialize clouds



    /**
     * converts a player's id to its corresponding index within the players ArrayList
     * @param id is the id of the player whose index you want to know within the ArrayList
     * @return the player's index within the ArrayList or -1 if the player is not present
     */
    private int playerIdToIndex(UUID id){

        for(int i=0; i<players.size(); i++){
            if(id.equals(players.get(i).getId())){
                return i;
            }
        }

        throw new IllegalArgumentException("ID does not correspond to any player");
    }

    /**
     * converts the index of a player contained in the ArrayList to its corresponding ID
     * @param positionPlayer is the index of the player within the ArrayList whose id you want to know
     * @return the corresponding player id
     */
    private UUID playerIndexToId(int positionPlayer){

        if(positionPlayer<0 || positionPlayer>players.size()) throw new IllegalArgumentException("position does not correspond to any ID");

        return players.get(positionPlayer).getId();
    }


    /**
     * transerts a student of a certain color from a player's entrance to the corresponding diningRoom.
     * If the bank has a number of coins greater than 0 and the number of students of that color is multiples of 3,
     * then one coin from the bank is transferred to the player. Subsequently, the correspondence between professors
     * and players in professorManager is updated
     * @param pawnColor color of the student that will be transferred from the entrance to the dining room
     * @param playerId player from whom the student will be transferred
     */
    public void moveStudentToDining(PawnColor pawnColor, UUID playerId){

        int playerIndex=playerIdToIndex(playerId);

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

            /*
              this is the case where the value associated with a color has a value that is defined by a player.
              This player is taken as a support player for the comparison so as to be replaced in case there is
              another player with a greater number of students of the same color or remain in case he is the one
              with the greater number
             */
            if(professorManager.getProfessorOwner(pawnColor)!=null){
                supportPlayer=professorManager.getProfessorOwner(pawnColor);

                for(Player player : players){
                    if(player.getDiningRoom().count(pawnColor)>supportPlayer.getDiningRoom().count(pawnColor)){
                        supportPlayer=player;
                    }
                }

                professorManager.setProfessorOwner(pawnColor, supportPlayer);
            }else{

                /*
                  this is the case when there is no player associated with the color under consideration.
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

    }


    public void moveCloudToEntrance(UUID whichCloud, UUID idPlayer){

        int playerIndex=playerIdToIndex(idPlayer);

        players.get(playerIndex).getEntrance().fill(whichCloud);

    }


    public void playAssistantCard(UUID idPlayer, int cardNumber) throws IllegalArgumentException {


        for (Integer playedCard : playedCards) {
            if (playedCard.equals(cardNumber)) {
                throw new IllegalArgumentException();
            }
        }

        int playerIndex=playerIdToIndex(idPlayer);

        players.get(playerIndex).playAssistantCard(cardNumber);

        playedCards.add(cardNumber);

    }


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


    public void moveStudentToIsland(PawnColor pawnColor, UUID idPlayer, UUID island) {

        int playerIndex=playerIdToIndex(idPlayer);

        islandManager.moveStudentToIsland(pawnColor, island, players.get(playerIndex));

    }



    public void fillAllClouds(){

        for(Cloud cloud : clouds){
            if(players.size()==2){
                cloud.fill(3);
            }else if(players.size()==3){
                cloud.fill(4);
            }
        }

    }

    public int countIslands(){
        return islandManager.countIslands();
    }


    public int getDeckSize(UUID player){

        int playerIndex=playerIdToIndex(player);

        return players.get(playerIndex).getDeckSize();
    }

    public int countStudentsInBag(){

        return bag.count();
    }




}
