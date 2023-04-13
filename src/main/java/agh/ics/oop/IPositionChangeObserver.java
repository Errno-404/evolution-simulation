package agh.ics.oop;

public interface IPositionChangeObserver {
    void positionChanged(Animal animal, Vector2d newPosition);
}
