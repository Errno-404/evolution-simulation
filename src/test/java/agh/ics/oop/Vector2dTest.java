package agh.ics.oop;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    @Test
    void testToString() {
        Vector2d v0 = new Vector2d(-1,-1);
        Vector2d v1 = new Vector2d(1,1);
        Vector2d v2 = new Vector2d(0,0);
        assertEquals(v0.toString(),"(-1,-1)");
        assertEquals(v1.toString(), "(1,1)");
        assertEquals(v2.toString(), "(0,0)");
    }

    @Test
    void precedes() {
        Vector2d v1 = new Vector2d(0,0);
        Vector2d v2 = new Vector2d(1,1);
        Vector2d v3 = new Vector2d(0,0);
        assertTrue(v1.precedes(v2));
        assertTrue(v1.precedes(v3));
        assertFalse(v2.precedes(v1));
    }

    @Test
    void follows() {
        Vector2d v1 = new Vector2d(0,0);
        Vector2d v2 = new Vector2d(1,1);
        Vector2d v3 = new Vector2d(0,0);

        assertTrue(v1.follows(v3));
        assertTrue(v2.follows(v1));
        assertFalse(v1.follows(v2));
    }

    @Test
    void add() {
        Vector2d v0 = new Vector2d(-1,-1);
        Vector2d v1 = new Vector2d(1,1);
        Vector2d v2 = new Vector2d(0,0);



        assertEquals(v0.add(v1),v2);
        assertEquals(v1.add(v0),v2);
        assertEquals(v1.add(v2), new Vector2d(1,1));
        assertEquals(v2.add(v1), new Vector2d(1,1));
        assertEquals(v0.add(v0), new Vector2d(-2,-2));

    }

    @Test
    void subtract() {
        Vector2d v0 = new Vector2d(-1,-1);
        Vector2d v1 = new Vector2d(1,1);
        Vector2d v2 = new Vector2d(0,0);

        assertEquals(v0.subtract(v1),new Vector2d(-2,-2));
        assertEquals(v1.subtract(v0),new Vector2d(2,2));
        assertEquals(v1.subtract(v2), new Vector2d(1,1));
        assertEquals(v2.subtract(v1), new Vector2d(-1,-1));
        assertEquals(v1.subtract(v1), new Vector2d(0,0));


    }

    @Test
    void upperRight() {
        Vector2d v0 = new Vector2d(-1,0);
        Vector2d v1 = new Vector2d(0,-1);
        Vector2d v2 = new Vector2d(1,0);
        Vector2d v3 = new Vector2d(0,1);
        Vector2d v4 = new Vector2d(1,1);
        Vector2d v5 = new Vector2d(-1,-1);

        assertEquals(v0.upperRight(v1),new Vector2d(0,0));
        assertEquals(v1.upperRight(v0),new Vector2d(0,0));
        assertEquals(v2.upperRight(v0), new Vector2d(1,0));
        assertEquals(v3.upperRight(v4), new Vector2d(1,1));
        assertEquals(v5.upperRight(v5), new Vector2d(-1,-1));
        assertEquals(v5.upperRight(v4), new Vector2d(1,1));
    }

    @Test
    void lowerLeft() {
        Vector2d v0 = new Vector2d(-1,0);
        Vector2d v1 = new Vector2d(0,-1);
        Vector2d v2 = new Vector2d(1,0);
        Vector2d v3 = new Vector2d(0,1);
        Vector2d v4 = new Vector2d(1,1);
        Vector2d v5 = new Vector2d(-1,-1);

        assertEquals(v0.lowerLeft(v1),new Vector2d(-1,-1));
        assertEquals(v1.lowerLeft(v0),new Vector2d(-1,-1));
        assertEquals(v2.lowerLeft(v0), new Vector2d(-1,0));
        assertEquals(v3.lowerLeft(v4), new Vector2d(0,1));
        assertEquals(v5.lowerLeft(v5), new Vector2d(-1,-1));
        assertEquals(v5.lowerLeft(v4), new Vector2d(-1,-1));
    }

    @Test
    void opposite() {
        Vector2d v0 = new Vector2d(0,0);
        Vector2d v1 = new Vector2d(-1,0);
        Vector2d v2 = new Vector2d(0,-1);
        Vector2d v3 = new Vector2d(-1,1);
        Vector2d v4 = new Vector2d(1,1);

        assertEquals(v0.opposite(), new Vector2d(0,0));
        assertEquals(v1.opposite(),new Vector2d(1,0));
        assertEquals(v2.opposite(), new Vector2d(0,1));
        assertEquals(v3.opposite(), new Vector2d(1,-1));
        assertEquals(v4.opposite(), new Vector2d(-1,-1));
    }

    @Test
    void testEquals() {
        Vector2d v = new Vector2d(1, 2);
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(999,1000);
        assertTrue(v.equals(v));
        assertTrue(v.equals(v1));
        assertFalse(v.equals(v2));

    }
}