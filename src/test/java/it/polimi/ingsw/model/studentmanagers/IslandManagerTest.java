package it.polimi.ingsw.model.studentmanagers;

import java.util.ArrayList;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ProfessorManager;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;


public class IslandManagerTest extends StudentCounter{
    private IslandManager islandManager;
    private ProfessorManager professorManager;
    private Bag bag;


    @Before
    public void setUp(){
        bag = new Bag();
        professorManager = new ProfessorManager();

        islandManager = new IslandManager(bag, professorManager);

        assertEquals(12, islandManager.countIslands());
        assertEquals(islandManager.getMotherNaturePosition(), islandManager.getIsland(0).getId());
    }

    /**
     * this method verifies that the moveMotherNature method works correctly
     */
    @Test
    public void moveMotherNature(){

        try {
            islandManager.moveMotherNature(5);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        assertEquals(islandManager.getMotherNaturePosition(), islandManager.getIsland(5).getId());

    }

    /**
     * this method verifies that island unification works correctly
     */
    @Test
    public void mergeIslands(){
        ArrayList<Cloud> clouds = new ArrayList<>();
        Entrance entrance = new Entrance(bag, clouds, 7);
        DiningRoom diningRoom = new DiningRoom(entrance);
        Player player = new Player(entrance, diningRoom, "pippo");
        for (PawnColor color : PawnColor.values()){
            professorManager.setProfessorOwner(color, player);
        }

        try {
            islandManager.moveMotherNature(2);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        try {
            islandManager.moveMotherNature(2);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        try {
            islandManager.moveMotherNature(11);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        assertEquals(10, islandManager.countIslands());
        assertEquals(islandManager.getMotherNaturePosition(), islandManager.getIsland(2).getId());
        assertEquals(3, islandManager.getIsland(2).count());


    }

    /**
     * this method verifies that printing is working correctly
     */
    @Test
    public void print(){
        String s = islandManager.toString();

        assertEquals(s, islandManager.toString());

    }



}
