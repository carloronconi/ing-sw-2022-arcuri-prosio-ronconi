package it.polimi.ingsw.server.model.studentmanagers;

import it.polimi.ingsw.server.model.ConverterUtility;
import it.polimi.ingsw.server.model.PawnColor;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.ProfessorManager;
import it.polimi.ingsw.server.model.charactercards.Witch;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Class used to manage islands and the merging of islands in archipelagos
 */
public class IslandManager extends StudentCounter {
    private final ArrayList<IslandTile> islands;
    private UUID motherNaturePosition;
    private final ProfessorManager professorManager;
    private Witch witch;
    private boolean centaurEffect;
    private Player knightEffectPlayer = null;
    private PawnColor mushroomMerchantEffect = null;

    /**
     * Constructor draws 2 tiles for each color from the bag, then adds 12 islands and puts in the first 10
     * islands 1 random student pawn
     * @param bag needed to draw the 10 students to initialize the islands
     */
    public IslandManager(Bag bag, ProfessorManager professorManager) {
        super();
        this.professorManager = professorManager;
        centaurEffect = false;
        for (PawnColor c: PawnColor.values()) {
            movePawnFrom(bag, c);
            movePawnFrom(bag, c);
        }

        islands = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            IslandTile it;

            if(i == 0 || i == 6){
                it = new IslandTile();
            } else {
                it = new IslandTile(this, 1);
            }
            islands.add(it);
        }

        motherNaturePosition=islands.get(0).getId();
    }

    /**
     * this method returns a single island that is contained within the ArrayList and
     * that is the one at the position passed as a parameter
     * @param islandPosition island index contained within the ArrayList
     * @return the island contained in the ArrayList Islands to the position passed as a parameter
     * @throws IllegalArgumentException if the index is negative or greater than the number of islands contained
     *                                  in the ArrayList
     */
    public IslandTile getIsland(int islandPosition) throws IllegalArgumentException {
        if(islandPosition<0 || islandPosition>countIslands()) throw new IllegalArgumentException();
        return islands.get(islandPosition);
    }


    /**
     * Allows mother nature to move by the number of steps given in input and, if the adjacent
     * islands (next and previous) are of the same player (the color of the towers is the same), it will transfer
     * the students to the island where mother nature is located and the others will eliminate them
     * @param steps are the steps that mother nature will have to take
     */
    public void moveMotherNature(int steps) throws NoSuchFieldException {

        //update mother nature position (id) using the index
        int motherNatureIndex = ConverterUtility.idToIndex(motherNaturePosition, islands);
        if (motherNatureIndex + steps >= islands.size()){
            motherNatureIndex = motherNatureIndex + steps - islands.size();
        } else {
            motherNatureIndex += steps;
        }
        motherNaturePosition = ConverterUtility.indexToId(motherNatureIndex, islands);

        IslandTile island = ConverterUtility.idToElement(motherNaturePosition, islands);

        //check if island has a ban from witch
        if(!island.ban){
            //check if owner has changed
            Player previousOwner=island.getOwner();
            Player currentOwner=island.checkNewOwner(professorManager, centaurEffect, knightEffectPlayer, mushroomMerchantEffect);
            centaurEffect = false;
            //if owner has changed try to merge the islands
            if(previousOwner!=currentOwner){
                mergeIslands(motherNaturePosition);
            }
        } else {
            island.ban = false;
            witch.increaseAvailableBans(this);
        }
        knightEffectPlayer = null;
        mushroomMerchantEffect = null;
    }


    /**
     * this method will handle the unification of the considered island with the next and the previous one
     * @param currentIslandId is the island where mother nature is located
     */
    private void mergeIslands(UUID currentIslandId) throws NoSuchFieldException {

        int currentIsland = ConverterUtility.idToIndex(currentIslandId, islands);
        int nextIsland;
        int prevIsland;

        if(currentIsland==islands.size()-1){
            nextIsland=0;
        }else{
            nextIsland=currentIsland+1;
        }

        if(islands.get(currentIsland).getOwner()==islands.get(nextIsland).getOwner()){
            islands.get(currentIsland).moveAllPawnsFrom(islands.get(nextIsland));
            islands.remove(nextIsland);
        }

        currentIsland = ConverterUtility.idToIndex(currentIslandId, islands);
        if(currentIsland==0){
            prevIsland=islands.size()-1;
        }else{
            prevIsland=currentIsland-1;
        }

        if(islands.get(currentIsland).getOwner()==islands.get(prevIsland).getOwner()){
            islands.get(currentIsland).moveAllPawnsFrom(islands.get(prevIsland));
            islands.remove(prevIsland);
        }

    }

    /**
     * this method moves a student from the entrance of a student's board to an island
     * @param color indicates the color of the piece to be moved
     * @param island indicates the island on which to move the piece
     * @param studentCounter it is passed as an input so as to have an entry reference of the studentCounter
     *               from which to take the piece
     */
    public void moveStudentToIsland(PawnColor color, UUID island, StudentCounter studentCounter) throws NoSuchFieldException {
        int islandIndex=ConverterUtility.idToIndex(island, islands);
        islands.get(islandIndex).movePawnFrom(studentCounter, color);
    }


    /**
     * checks the number of islands (or groups of islands) present in the ArrayList islands
     * so as to be able to end the game when it reaches 3
     * @return he number of islands contained within the ArrayList islands
     */
    public int countIslands(){
        return islands.size();
    }

    public UUID getMotherNaturePosition() {
        return motherNaturePosition;
    }

    public void useFlagBearerEffect(UUID island) throws NoSuchFieldException {
        IslandTile islandTile = ConverterUtility.idToElement(island, islands);
        Player previousOwner = islandTile.getOwner();
        Player newOwner = islandTile.checkNewOwner(professorManager, centaurEffect, knightEffectPlayer, mushroomMerchantEffect);
        if(newOwner!=previousOwner) mergeIslands(island);

    }

    public void useWitchEffect(UUID island, Witch witch) throws NoSuchFieldException {
        IslandTile islandToBan = ConverterUtility.idToElement(island,islands);
        if(islandToBan.ban) throw new IllegalStateException("island already has an active ban");

        this.witch = witch;
        islandToBan.ban = true;
    }

    public void assertCentaurEffect(){
        centaurEffect = true;
    }

    public void assertKnightEffect(Player player) { knightEffectPlayer = player; }

    public void setMushroomMerchantEffect(PawnColor mushroomMerchantEffect) {
        this.mushroomMerchantEffect = mushroomMerchantEffect;
    }

    @Override
    public String toString() {
        String s = "Islands:\n    ";
        int i = 0;
        for (IslandTile island : islands){
            int j = i+1;
            s += "[" + j + "] "+ island;
            try {
                if (i==ConverterUtility.idToIndex(motherNaturePosition, islands)) s+= "<- Mother Nature";
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            s+= "\n    ";
            i++;
        }
        return s;
    }

    public ArrayList<IslandTile> getIslands() {
        return new ArrayList<>(islands);
    }
}