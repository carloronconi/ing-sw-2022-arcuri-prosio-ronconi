package it.polimi.ingsw.model.studentmanagers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class IslandTileTest extends StudentCounter {
    private Bag bag;
    private List<Cloud> clouds;
    private Entrance entrance;
    private IslandTile curr;
    private IslandTile next;
    private IslandTile prev;

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

    //TODO: implement tests when IslandTile class completed
    @Test
    void link() {
    }

    @Test
    void connectNext() {
    }

    @Test
    void connectPrev() {
    }

    @Test
    void isConnectNext() {
    }

    @Test
    void isConnectPrev() {
    }

    @Test
    void fill() {
    }
}