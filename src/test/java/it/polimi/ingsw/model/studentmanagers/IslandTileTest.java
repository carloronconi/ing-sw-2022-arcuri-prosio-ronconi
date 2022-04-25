package it.polimi.ingsw.model.studentmanagers;

import it.polimi.ingsw.model.Identifiable;
import it.polimi.ingsw.model.PawnColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class IslandTileTest extends StudentCounter implements Identifiable {
    private Bag bag;
    private List<Cloud> clouds;
    Entrance entrance;
    IslandTile curr;
    IslandTile next;
    IslandTile prev;
    UUID tileId;
    int size =1;

    @BeforeEach
    void setUp() {
        bag = new Bag();
        clouds = new ArrayList<>();
        IntStream.range(0,3).forEach(i -> clouds.add(new Cloud(bag)));

        entrance = new Entrance(bag,clouds);
        curr = new IslandTile();
        next = new IslandTile();
        prev = new IslandTile();
    }

    //TODO: implement tests for all methods when IslandTile class completed

    @Test
    void  moveAllPawnsFrom(IslandTile next){
        size++;
        for (PawnColor c: PawnColor.values()) {
            for (int i = 0; i < next.count(c); i++) {
                movePawnFrom(next,c);
            }
        }

        assertEquals(2, size);

    }

    public UUID getId() {return tileId;}
}