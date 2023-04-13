package agh.ics.oop.variants.maps;

import agh.ics.oop.*;
import agh.ics.oop.variants.behaviour.Behaviour;
import agh.ics.oop.variants.mutations.Mutator;
import agh.ics.oop.variants.plantGrowth.PlantGrowth;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

    // ------------------------------- Map parameters -------------------------------

    public Config mapConfig;

    public Vector2d lowerLeft;
    public Vector2d upperRight;

    private final Mutator mutator;
    private final PlantGrowth plantGrowth;
    private final Behaviour behaviour;

    HashMap<Vector2d, ArrayList<Animal>> mapAnimals;


    HashMap<Vector2d, Grass> mapGrass;
    HashMap<Vector2d, Integer> deathCounter;

    public MapStatistics stats = new MapStatistics(this);




    protected AbstractWorldMap(Config config, Mutator mutator, PlantGrowth plantGrowth, Behaviour behaviour){
        this.mapConfig = config;
        this.mutator = mutator;
        this.plantGrowth = plantGrowth;
        this.behaviour = behaviour;

        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(0, 0).add(new Vector2d(this.mapConfig.mapWidth() - 1, this.mapConfig.mapHeight() - 1));

        this.mapAnimals = new HashMap<>();
        this.mapGrass = new HashMap<>();
        this.deathCounter = new HashMap<>();

        this.randomiseAnimals();

        this.plantGrowth.growN(this,config.initialGrassCount());
    }

    protected AbstractWorldMap(Config config, Mutator mutator, PlantGrowth plantGrowth, Behaviour behaviour, int initGrass){
        this.mapConfig = config;
        this.mutator = mutator;
        this.plantGrowth = plantGrowth;
        this.behaviour = behaviour;

        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(0, 0).add(new Vector2d(this.mapConfig.mapWidth() - 1, this.mapConfig.mapHeight() - 1));

        this.mapAnimals = new HashMap<>();
        this.mapGrass = new HashMap<>();
        this.deathCounter = new HashMap<>();

        plantGrowth.growN(this, initGrass);
    }






    // ------------------------------- XXXXXXXXXXXXXXXXXX ----------------------------



    public void reportDeath(Animal animal){
        Vector2d position = animal.getPosition();
        this.stats.updateDeath(1);

        deathCounter.merge(position, 1, Integer::sum);
        this.stats.updateGenomeCount(animal.getGenome(),-1);
        this.stats.updateAliveAnimalCount(-1);

        this.removeFromList(animal);
    }

    // ######################################### Parameters Getters ###################################################


    public HashMap<Vector2d, ArrayList<Animal>> getMapAnimals(){
        return this.mapAnimals;
    }
    public HashMap<Vector2d, Integer> getDeathCounter(){
        return this.deathCounter;
    }

    public Mutator getMutator() {
        return mutator;
    }

    public PlantGrowth getPlantGrowth() {
        return plantGrowth;
    }

    public Behaviour getBehaviour() {
        return behaviour;
    }


    public HashMap<Vector2d, Grass> getMapGrass() {
        return mapGrass;
    }

    // ##################################### IWorldMap ###########################################

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(this.lowerLeft) && position.precedes(this.upperRight);
    }

    @Override
    public void place(IMapElement element){
        if(element instanceof Animal newAnimal){
            this.putInList(newAnimal, newAnimal.getPosition());
            newAnimal.addObserver(this);

            this.stats.updateGenomeCount(((Animal) element).getGenome(),1);
            this.stats.updateAliveAnimalCount(1);
            this.stats.updateTotalAnimalCount(1);
        } else if (element instanceof Grass newGrass) {
            this.mapGrass.put(newGrass.getPosition(), newGrass);
        }
    }

    @Override
    public boolean isOccupied(Vector2d position){
        return this.mapGrass.containsKey(position) || (this.mapAnimals.containsKey(position));
    }

    @Override
    public ArrayList<IMapElement> objectAt(Vector2d position){

        ArrayList<IMapElement> entitiesOnPosition = new ArrayList<>();


        if(this.mapAnimals.get(position) != null){
            ArrayList<Animal> tmpAnimal = this.mapAnimals.get(position);
            entitiesOnPosition.addAll(tmpAnimal);
            return entitiesOnPosition;
        }
        if(this.mapGrass.containsKey(position)){
            entitiesOnPosition.add(this.mapGrass.get(position));
            return entitiesOnPosition;
        }
        return  entitiesOnPosition;
    }

    @Override
    public abstract void handleMovement(Animal a, Vector2d position);

    private void putInList(Animal a, Vector2d newPos){
        if (this.mapAnimals.containsKey(newPos)){
            int index = Collections.binarySearch(this.mapAnimals.get(newPos),a,(first, other) -> {
                if (first.getEnergy() == other.getEnergy()) {
                    if (first.getAge() == other.getAge()) {
                        return first.getChildrenCount() - other.getChildrenCount();
                    } else return first.getAge() - other.getAge();
                } else return first.getEnergy() - other.getEnergy();
            });
            if(index < 0){

                this.mapAnimals.get(newPos).add(-1*index-1, a);
            }
            else{
                this.mapAnimals.get(newPos).add(index, a);
            }
        }
        else{
            ArrayList<Animal> temp = new ArrayList<>();
            temp.add(a);
            this.mapAnimals.put(newPos, temp);
        }
    }


    private void removeFromList(Animal a){
        if(this.mapAnimals.containsKey(a.getPosition())){
            this.mapAnimals.get(a.getPosition()).remove(a);
            if(this.mapAnimals.get(a.getPosition()).size()==0){
                this.mapAnimals.remove(a.getPosition());
            }
        }
    }

    public void positionChanged(Animal animal, Vector2d newPosition) {
        this.removeFromList(animal);
        this.putInList(animal, newPosition);
    }


    @Override
    public String toString(){
        MapVisualizer visualizer = new MapVisualizer(this);
        return visualizer.draw(this.lowerLeft, this.upperRight);
    }

    public void randomiseAnimals(){
        ArrayList<Vector2d> initPositions = new ArrayList<>();

        for(int x = 0;x<this.mapConfig.mapWidth();x++){
            for(int y = 0;y<this.mapConfig.mapHeight();y++){
                initPositions.add(new Vector2d(x,y));
            }
        }

        Collections.shuffle(initPositions);

        for(int i = 0;i<this.mapConfig.initialAnimalCount();i++){
            this.place(new Animal(this, initPositions.get(i),this.mapConfig.initialAnimalEnergy()));
        }

    }
}

