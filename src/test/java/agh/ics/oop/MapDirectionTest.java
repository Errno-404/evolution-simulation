package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {

    @Test
    void next() {
    }

    @Test
    void turnTest() {
        Gene[] turnInput = {
                Gene.FORWARD,
                Gene.FORWARDRIGHT,
                Gene.RIGHT,
                Gene.BACKRGIHT,
                Gene.BACK,
                Gene.BACKLEFT,
                Gene.LEFT,
                Gene.FORWARDLEFT
        };

        MapDirection[] initOrient = {
                MapDirection.NORTH,
                MapDirection.NORTH,
                MapDirection.NORTH,
                MapDirection.NORTH,
                MapDirection.NORTH,
                MapDirection.NORTH,
                MapDirection.NORTH,
                MapDirection.NORTH
        };

        MapDirection[] expectedResult = {
                MapDirection.NORTH,
                MapDirection.NORTHEAST,
                MapDirection.EAST,
                MapDirection.SOUTHEAST,
                MapDirection.SOUTH,
                MapDirection.SOUTHWEST,
                MapDirection.WEST,
                MapDirection.NORTHWEST
        };

        for(int i = 0;i<8;i++){
            assertEquals(initOrient[i].turn(turnInput[i]), expectedResult[i]);
        }
    }
}