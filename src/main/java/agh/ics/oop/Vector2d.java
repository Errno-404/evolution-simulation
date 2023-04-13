package agh.ics.oop;

import java.util.Objects;

public class Vector2d {
    final public int x;
    final public int y;

    public Vector2d(int x, int y)
    {
        this.x = x;
        this.y = y;

    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public String toString()
    {
        return "("+this.x+","+this.y+")";
    }

    public boolean precedes(Vector2d other)
    {
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other)
    {
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d add(Vector2d other)
    {

        return new Vector2d(this.x + other.x, this.y + other.y);

    }

    public Vector2d subtract(Vector2d other)
    {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public Vector2d upperRight(Vector2d other)
    {
        return new Vector2d(Math.max(this.x, other.x), Math.max(this.y, other.y));
    }

    public Vector2d lowerLeft(Vector2d other)
    {
        return new Vector2d(Math.min(this.x, other.x), Math.min(this.y, other.y));
    }

    public Vector2d opposite()
    {
        return new Vector2d(-1*this.x, -1*this.y);
    }

    public int hashCode()
    {
        return Objects.hash(this.x, this.y);
    }

    public boolean equals(Object other)
    {
        if(this == other) return true;

        if(!(other instanceof Vector2d)) return false;

        Vector2d t = (Vector2d) other;

        return this.x == t.x && this.y == t.y;
    }

    public Vector2d multiply(Vector2d v){
        return new Vector2d(this.x*v.x, this.y*v.y);
    }

}
