package it.polimi.ingsw.model.studentmanagers;

import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static junit.framework.TestCase.assertEquals;


public class IslandManagerTest extends StudentCounter{
    private ArrayList<IslandTile> islands;
    UUID motherNaturePosition;
    int MNIslandNumber;
    int currentPositionMN;
    int fixedSteps = 5;
    @BeforeEach
    void setup() {
        islands = new ArrayList<>();

        motherNaturePosition = islands.get(0).getId();
        MNIslandNumber = 0;
    }

    @Test
    UUID moveMotherNature(){

        MNIslandNumber+=fixedSteps;
        assertEquals(5, MNIslandNumber);


        if(MNIslandNumber>islands.size()){
            currentPositionMN=MNIslandNumber-islands.size();
        }else{
            currentPositionMN=MNIslandNumber;
        }
        assertEquals(5, MNIslandNumber);

        return motherNaturePosition;

    }



}
