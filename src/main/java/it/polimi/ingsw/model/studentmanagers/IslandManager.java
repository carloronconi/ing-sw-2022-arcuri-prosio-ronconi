package it.polimi.ingsw.model.studentmanagers;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ProfessorManager;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Class used to manage islands and the merging of islands in archipelagos
 */
public class IslandManager extends StudentCounter {
    private final ArrayList<IslandTile> islands;
    private UUID motherNaturePosition;
    private ProfessorManager professorManager;


    /**
     * Constructor draws 2 tiles for each color from the bag, then adds 12 islands and puts in the first 10
     * islands 1 random student pawns
     * @param bag needed to draw the 10 students to initialize the islands
     */
    public IslandManager(Bag bag) {
        super();

        for (PawnColor c: PawnColor.values()) {
            movePawnFrom(bag, c);
            movePawnFrom(bag, c);
        }
        islands = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            IslandTile it;

            if(i == 0 || i == 5){
                it = new IslandTile();
            } else {
                it = new IslandTile(this, 1);
            }

            islands.add(it);
        }

        motherNaturePosition=islands.get(0).getId();
    }

    /**
     * Allows mother nature to move by the number of steps given in input and, if the adjacent
     * islands (next and previous) are of the same player (the color of the towers is the same), it will transfer
     * the students to the island where mother nature is located and the others will eliminate them
     * @param steps are the steps that mother nature will have to take
     * @return the id of the island mother nature is on
     */
    public UUID moveMotherNature(int steps){

        int MNIslandNumber=idToIndex(motherNaturePosition);

        MNIslandNumber+=steps;
        int currentPositionMN;
        if(MNIslandNumber>12){
            currentPositionMN=MNIslandNumber-12;
        }else{
            currentPositionMN=MNIslandNumber;
        }

        boolean checkUnification=false;
        if(!islands.get(currentPositionMN).getOwner().equals(islands.get(currentPositionMN).checkNewOwner(professorManager))){
            islands.get(currentPositionMN).setOwner(islands.get(currentPositionMN).checkNewOwner(professorManager));
            checkUnification=true;
        }

        if(checkUnification){
            mergeIslands(currentPositionMN);
        }


        return indexToId(currentPositionMN);
    }


    /**
     * this method will handle the unification of the considered island with the next and the previous one
     * @param currentIsland is the island where mother nature is located
     */
    private void mergeIslands(int currentIsland){

        int nextIsland;
        int prevIsland;

        if(currentIsland==11){
            nextIsland=0;
        }else{
            nextIsland=currentIsland+1;
        }

        if(currentIsland==0){
            prevIsland=11;
        }else{
            prevIsland=currentIsland-1;
        }


        if(islands.get(currentIsland).getOwner().equals(islands.get(nextIsland).getOwner())){
            islands.get(currentIsland).moveAllPawnsFrom(islands.get(nextIsland));
            islands.remove(nextIsland);
        }

        if(islands.get(currentIsland).getOwner().equals(islands.get(prevIsland).getOwner())){
            islands.get(currentIsland).moveAllPawnsFrom(islands.get(prevIsland));
            islands.remove(prevIsland);
        }

    }


    /**
     * converts the id of an island to its corresponding index within the island ArrayList
     * @param id is the id of the island whose index you want to know within the ArrayList
     * @return the island index within the ArrayList or -1 if the island is not present
     */
    private int idToIndex(UUID id){

        for(int i=0; i<islands.size(); i++){
            if(id.equals(islands.get(i).getId())){
                return i;
            }
        }
        return -1;
    }

    /**
     * converts the index of an island present in the ArrayList to its corresponding id
     * @param positionIsland is the index of the island within the ArrayList whose id you want to know
     * @return the corresponding island id
     */
    private UUID indexToId(int positionIsland){

        return islands.get(positionIsland).getId();
    }







    public void moveStudentToIsland(PawnColor color, int islandIndex, Player player){
        IslandTile island = islands.get(islandIndex);
        island.movePawnFrom(player.getEntrance(), color);
    }


    public void changeIslandOwner(int islandIndex, Player newOwner, ProfessorManager professorManager) throws IllegalArgumentException {
        IslandTile island = islands.get(islandIndex);
        if (island.getOwner() == newOwner) throw new IllegalArgumentException();
        // check influence
        // change owner
        // if needed, mergeIslands
    }

    //TODO: testing
    //TODO: other methods


}
