package agh.ics.oop.variants.maps;

import agh.ics.oop.Animal;
import agh.ics.oop.IMapElement;
import agh.ics.oop.Vector2d;

import java.util.ArrayList;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author Unknown
 *
 */
public interface IWorldMap {
    /**
     * Indicate if any object can move to the given position.
     *
     * @param position
     *            The position checked for the movement possibility.
     * @return True if the object can move to that position.
     */
    boolean canMoveTo(Vector2d position);

    /**
     * Place any object on the map.
     *
     * @param element The element to place on the map.
     */
    void place(IMapElement element);

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position
     *            Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return an object at a given position.
     *
     * @param position
     *            The position of the object.
     * @return Object or null if the position is not occupied.
     */
    ArrayList<IMapElement> objectAt(Vector2d position);

    void handleMovement(Animal a, Vector2d position);
}
