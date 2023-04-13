package agh.ics.oop;

import agh.ics.oop.variants.maps.AbstractWorldMap;
import agh.ics.oop.variants.plantGrowth.Equator;
import agh.ics.oop.variants.plantGrowth.PlantGrowth;

import java.util.ArrayList;
import java.util.Random;



public class Animal implements IMapElement {

    // ########################################### Cechy zwierzaka ####################################################

    private MapDirection orientation;
    private Vector2d position;
    private int energy;
    private Genome genome;
    private AbstractWorldMap map;

    private int age;

    private int childrenCount;

    private int grassEaten = 0;

    private ArrayList<IPositionChangeObserver> observers = new ArrayList<>();

    private Integer deathDate = null;


    // ####################################### Konstruktory ###########################################################

    public Animal(AbstractWorldMap map, Vector2d initialPosition, int energy) {
        this.map = map;
        this.position = initialPosition;
        this.energy = energy;
        this.age = 0;
        this.childrenCount = 0;


        // Set up initial Orientation
        Random random = new Random();
        int randomOrientationInt = random.nextInt(8);

        this.orientation = switch (randomOrientationInt) {
            case 0 -> MapDirection.NORTH;
            case 1 -> MapDirection.NORTHEAST;
            case 2 -> MapDirection.EAST;
            case 3 -> MapDirection.SOUTHEAST;
            case 4 -> MapDirection.SOUTH;
            case 5 -> MapDirection.SOUTHWEST;
            case 6 -> MapDirection.WEST;
            case 7 -> MapDirection.NORTHWEST;
            default -> throw new IllegalStateException("Unexpected value: "
                    + randomOrientationInt
                    + ", expected number in range (0,7)");
        };


        // TODO check
        this.genome = new Genome(this.map.getMutator());
    }


    // Do testów
    public Animal(int energy, Genome gen) {
        this.energy = energy;
        this.genome = gen;
    }

    // ############################################ Gettery ###########################################################

    public Genome getGenome() {
        return this.genome;
    }

    public int getEnergy() {
        return this.energy;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public MapDirection getOrientation(){
        return this.orientation;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public int getAge() {
        return age;
    }

    public int getGrassEaten(){
        return this.grassEaten;
    }

    public Integer getDeathDate(){
        return this.deathDate;
    }

    // ############################################ Settery ###########################################################

    public void setEnergy(int energy){
        this.energy = energy;
    }

    public void setOrientation(MapDirection orientation){
        this.orientation = orientation;
    }

    public void setPosition(Vector2d position){
        this.reportPositionChanged(this, position);
        this.position = position;
    }

    public void setDeathDate(Integer day){
        this.deathDate = day;
    }


    // ########################################## Rozmnażanie #########################################################

    public boolean readyToBread(Animal other) {
        return (this.energy > this.map.mapConfig.readyToBreedEnergy()
                && other.energy > this.map.mapConfig.readyToBreedEnergy());
    }


    public Animal breed(Animal other) {
        Animal child = new Animal(this.map, this.position, 0);
        int breedEnergy = this.map.mapConfig.energyLoss();

        this.energy -= breedEnergy;
        other.energy -= breedEnergy;

        this.childrenCount++;
        other.childrenCount++;

        child.energy = breedEnergy * 2;
        child.genome = Genome.crossGenes(this, other);

        return child;
    }

    // ############################################ Jedzenie ##########################################################

    public void eat(Grass grass) {
        this.energy += this.map.mapConfig.plantEnergy();
        grass.setEaten();
        PlantGrowth pg = this.map.getPlantGrowth();
        if(pg instanceof Equator){
            ((Equator) pg).reportGrassEaten(grass.getPosition());
        }
        this.grassEaten+=1;
    }

    // ########################################## Poruszanie się ######################################################

    public void move() {
        Gene i = genome.currentGene();
        this.orientation = this.orientation.turn(i);

        Vector2d destination = this.position.add(this.orientation.toUnitVector());
        if(map.canMoveTo(destination)){
            this.setPosition(this.position.add(this.orientation.toUnitVector()));
        }
        else{
            this.map.handleMovement(this, destination);
        }

        this.age++;
        this.map.getBehaviour().setNextGene(this.genome);
        this.energy--;
    }


    public void moveTo(){
        Vector2d destination = this.position.add(this.orientation.toUnitVector());
        if(map.canMoveTo(destination)){
            this.setPosition(this.position.add(this.orientation.toUnitVector()));

        }
        else{
            this.map.handleMovement(this, destination);
        }
    }

    // ############################################## Obserwatory #####################################################


    public void addObserver(IPositionChangeObserver o) {
        this.observers.add(o);
    }

    void removeObserver(IPositionChangeObserver o) {
        this.observers.remove(o);
    }

    void reportPositionChanged(Animal animal, Vector2d newPosition) {
        for (IPositionChangeObserver o : this.observers) {
            o.positionChanged(animal, newPosition);
        }
    }





    // ########################################## Wyświetlanie na mapie ###############################################
    public String toString() {
        return orientation.toString() + position.toString() + "_"+energy + "_" +age +"_"+ childrenCount;
//        return orientation.toString();
    }

    public boolean isAt(Vector2d pos) {
        return this.position.equals(pos);
    }

    public boolean isFacing(MapDirection m) {
        return this.orientation.equals(m);
    }

    // GUI
    @Override
    public String getImage() {
        switch (this.orientation) {
            case NORTH -> {
                return "src/main/resources/up.png";
            }
            case SOUTH -> {
                return "src/main/resources/down.png";
            }
            case WEST -> {
                return "src/main/resources/left.png";
            }
            case EAST -> {
                return "src/main/resources/right.png";
            }
            default -> {
                return null;
            }

        }
    }


    // do testów!
    public Animal(AbstractWorldMap map, Vector2d initialPosition, int energy, int age, int childrenCount) {
        this.map = map;
        this.position = initialPosition;
        this.energy = energy;
        this.age = age;
        this.childrenCount = childrenCount;


        // Set up initial Orientation
        Random random = new Random();
        int randomOrientationInt = random.nextInt(8);

        this.orientation = switch (randomOrientationInt) {
            case 0 -> MapDirection.NORTH;
            case 1 -> MapDirection.NORTHEAST;
            case 2 -> MapDirection.EAST;
            case 3 -> MapDirection.SOUTHEAST;
            case 4 -> MapDirection.SOUTH;
            case 5 -> MapDirection.SOUTHWEST;
            case 6 -> MapDirection.WEST;
            case 7 -> MapDirection.NORTHWEST;
            default -> throw new IllegalStateException("Unexpected value: "
                    + randomOrientationInt
                    + ", expected number in range (0,7)");
        };


        // TODO check
        this.genome = new Genome(this.map.getMutator());
    }


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public boolean compare(Animal other){
        if (this.getEnergy() == other.getEnergy()) {
            if (this.getAge() == other.getAge()) {
                return this.getChildrenCount() <= other.getChildrenCount();
            } else return this.getAge() < other.getAge();
        } else return this.getEnergy() < other.getEnergy();
    }

}
