package it.polimi.ingsw.model;

import it.polimi.ingsw.model.studentmanagers.Bag;
import it.polimi.ingsw.model.studentmanagers.Cloud;
import it.polimi.ingsw.model.studentmanagers.DiningRoom;
import it.polimi.ingsw.model.studentmanagers.Entrance;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class ProfessorManagerTest {
    private ArrayList<PawnColor> pippoPawnColors;
    private ArrayList<PawnColor> plutoPawnColors;
    private ProfessorManager professorManager;
    private Player pippo;
    private Player pluto;

    @Before
    public void setUp(){
        Bag bag = new Bag();
        ArrayList<Cloud> clouds = new ArrayList<>();
        Entrance entrance1 = new Entrance(bag, clouds, 7);
        DiningRoom diningRoom1 = new DiningRoom(entrance1);
        pippo = new Player(entrance1, diningRoom1, "pippo");

        Entrance entrance2 = new Entrance(bag, clouds, 7);
        DiningRoom diningRoom2 = new DiningRoom(entrance2);
        pluto = new Player(entrance2, diningRoom2, "pluto");

        professorManager = new ProfessorManager();
        pippoPawnColors = new ArrayList<>();
        plutoPawnColors = new ArrayList<>();

        professorManager.setProfessorOwner(PawnColor.GREEN, pippo);
        pippoPawnColors.add(PawnColor.GREEN);
        professorManager.setProfessorOwner(PawnColor.PURPLE, pippo);
        pippoPawnColors.add(PawnColor.PURPLE);
        assertEquals(pippo, professorManager.getProfessorOwner(PawnColor.GREEN));
        assertEquals(pippo, professorManager.getProfessorOwner(PawnColor.PURPLE));

        professorManager.setProfessorOwner(PawnColor.RED, pluto);
        plutoPawnColors.add(PawnColor.RED);
        professorManager.setProfessorOwner(PawnColor.BLUE, pluto);
        plutoPawnColors.add(PawnColor.BLUE);
        assertEquals(pluto, professorManager.getProfessorOwner(PawnColor.RED));
        assertEquals(pluto, professorManager.getProfessorOwner(PawnColor.BLUE));
    }

    @Test
    public void colorsAssociateToPlayer(){
        assertEquals(pippoPawnColors, professorManager.colorsAssociateToPlayer(pippo));

        assertEquals(plutoPawnColors, professorManager.colorsAssociateToPlayer(pluto));

    }

    @Test
    public void playersContained(){
        ArrayList<Player> players = new ArrayList<>();
        players.add(pluto);
        players.add(pippo);

        assertEquals(players, professorManager.playersContained());
    }

    @Test
    public void print(){
        String s = professorManager.toString();

        assertEquals(s, professorManager.toString());

    }



}