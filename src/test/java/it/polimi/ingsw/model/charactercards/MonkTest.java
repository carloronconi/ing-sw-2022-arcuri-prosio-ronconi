package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.ProfessorManager;
import it.polimi.ingsw.model.studentmanagers.Bag;
import it.polimi.ingsw.model.studentmanagers.IslandManager;
import it.polimi.ingsw.model.studentmanagers.IslandTile;
import org.junit.Before;
import org.junit.Test;


import static org.junit.jupiter.api.Assertions.*;

public class MonkTest {
    private Bag bag;
    private IslandManager islandManager;
    private Monk monk;


    @Before
    public void setUp(){
        bag = new Bag();
        ProfessorManager professorManager = new ProfessorManager();
        islandManager = new IslandManager(bag, professorManager);
        monk = new Monk(bag, islandManager);

        assertEquals(1, monk.getCost());
        assertFalse(monk.isCostIncreased());
        assertEquals(116, bag.count());
    }

    @Test
    public void useEffect() throws NoSuchFieldException {

        IslandTile islandTile = islandManager.getIsland(1);

        for(PawnColor color : PawnColor.values()){
            if(monk.isColorContained(color)){
                int numStudents=islandTile.count(color);
                monk.useEffect(color, islandTile.getId());
                assertEquals(115, bag.count());
                assertEquals(numStudents+1, islandTile.count(color));
                break;
            }
        }

    }








}
