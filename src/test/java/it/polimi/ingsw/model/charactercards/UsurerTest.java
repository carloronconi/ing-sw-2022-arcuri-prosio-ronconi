package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.studentmanagers.Bag;
import it.polimi.ingsw.model.studentmanagers.Cloud;
import it.polimi.ingsw.model.studentmanagers.DiningRoom;
import it.polimi.ingsw.model.studentmanagers.Entrance;
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

        for(Player player : players){
            for(PawnColor color : PawnColor.values()){
                if(player.getEntrance().count(color)>1) c = color;
                while(player.getEntrance().count(color)>0){
                    player.getDiningRoom().fill(color);
                }
            }

            assertEquals(0, player.getEntrance().count());
        }


        numStudents = new ArrayList<>();
        for(Player player : players) numStudents.add(player.getDiningRoom().count(c));


    }

    @Test
    public void useEffect(){

        usurer.useEffect(c);

        for(int i=0; i<numStudents.size(); i++){
            if(numStudents.get(i)>=3){
                assertEquals(numStudents.get(i)-3, players.get(i).getDiningRoom().count(c));
            }else{
                assertEquals(0, players.get(i).getDiningRoom().count(c));
            }
        }
    }






}