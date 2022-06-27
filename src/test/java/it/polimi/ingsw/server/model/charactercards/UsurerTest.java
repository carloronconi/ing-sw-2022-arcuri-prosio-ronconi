package it.polimi.ingsw.server.model.charactercards;

import it.polimi.ingsw.server.model.PawnColor;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.server.model.charactercards.Usurer;
import it.polimi.ingsw.server.model.studentmanagers.Bag;
import it.polimi.ingsw.server.model.studentmanagers.Cloud;
import it.polimi.ingsw.server.model.studentmanagers.DiningRoom;
import it.polimi.ingsw.server.model.studentmanagers.Entrance;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UsurerTest {
    private Usurer usurer;
    private List<Player> players;
    private PawnColor c;
    List<Cloud> clouds = new ArrayList<>();
    Entrance entrance1;
    DiningRoom diningRoom1;
    Entrance entrance2;
    DiningRoom diningRoom2;
    List<Integer> numStudents;


    @Before
    public void setUp(){
        players = new ArrayList<>();
        Bag bag = new Bag();


        entrance1 = new Entrance(bag, clouds, 7);
        diningRoom1 = new DiningRoom(entrance1);
        Player player1 = new Player(entrance1, diningRoom1, "testname");
        players.add(player1);

        entrance2 = new Entrance(bag, clouds, 7);
        diningRoom2 = new DiningRoom(entrance2);
        Player player2 = new Player(entrance2, diningRoom2, "testname2");
        players.add(player2);

        usurer = new Usurer(players, bag);


        assertEquals(116, bag.count());
        assertEquals(2, players.size());
        assertEquals(3, usurer.getCurrentCost());
        assertFalse(usurer.isCostIncreased());
        assertEquals(AvailableCharacter.USURER, usurer.getValue());

        /*
        this piece of code moves all students from the entrance to the diningRoom for all players.
        Moreover, it saves in a support variable the color of the students to be put back in the bag
         */
        for(Player player : players){
            for(PawnColor color : PawnColor.values()){
                if(player.getEntrance().count(color)>1) c = color;
                while(player.getEntrance().count(color)>0){
                    player.getDiningRoom().fill(color);
                }
            }

            assertEquals(0, player.getEntrance().count());
        }


        /*
        the number of students of the chosen color present in the diningRoom of various players is saved in a list
         */
        numStudents = new ArrayList<>();
        for(Player player : players) numStudents.add(player.getDiningRoom().count(c));


    }


    /**
     * this method tests that the usurer effect works correctly
     */
    @Test
    public void useEffect(){

        usurer.setEffectColor(c);
        usurer.useEffect();

        for(int i=0; i<numStudents.size(); i++){
            if(numStudents.get(i)>=3){
                assertEquals(numStudents.get(i)-3, players.get(i).getDiningRoom().count(c));
            }else{
                assertEquals(0, players.get(i).getDiningRoom().count(c));
            }
        }

        assertTrue(usurer.isCostIncreased());
    }






}