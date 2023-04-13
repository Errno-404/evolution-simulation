package agh.ics.oop;

public class Grass implements IMapElement{


    private boolean eaten = false;

    public void setEaten(){
        this.eaten = true;
    }
    public Vector2d position;
    private final String fpath = "src/main/resources/grass.png";
    public Grass(Vector2d pos) {
        this.position = pos;
    }

    public String toString() {
        return "*";
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    public String getImage(){
        return this.fpath;
    }


}
