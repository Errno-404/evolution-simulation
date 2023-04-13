package agh.ics.oop;

import agh.ics.oop.variants.maps.AbstractWorldMap;

import java.util.*;



public class MapStatistics {

    private final AbstractWorldMap map;

    private final HashMap<Genome, Integer> genomeCount = new HashMap<>();

    private Animal trackedAnimal = null;
    private Integer deathAccumulator = 0;

    private Integer aliveAnimalCount = 0;
    private Integer totalAnimalCount = 0;

    private double meanEnergy = 0; //value calculated and updated while iterating over animals in simulationEngine

    private double totalDaysLived = 0;

    private Integer todayDied = 0;

    private Integer day = 0;

    ArrayList<Genome> genomesSortedByCount = new ArrayList<>();

    public MapStatistics(AbstractWorldMap map){
        this.map = map;
    }

    public void updateGenomeCount(Genome g, Integer n){ //n=1 if new animal is placed, n=-1 if animal died
        Integer oldVal;
        if(this.genomeCount.containsKey(g)){
            oldVal = this.genomeCount.get(g);
        }
        else{
            oldVal = 0;
            this.genomesSortedByCount.add(g);
            if(n<0){
                throw new IllegalStateException("Unexpected value: " + n + ", updating genome with count 0 by negative value");
            }
        }

        genomeCount.merge(g, n, Integer::sum);
        genomesSortedByCount.sort((g1,g2) -> {
            return genomeCount.get(g2) - genomeCount.get(g1);
        });

    }

    public Integer getDay(){
        return this.day;
    }

    public void updateDay(){
        this.day+=1;
    }

    public void updateMenaEnergy(double val){
        this.meanEnergy = val;
    }

    public double getMeanEnergy(){
        return this.meanEnergy;
    }

    public void updateTotalDaysLived(double val){
        this.totalDaysLived+=val;
    }

    public void updateTrackedAnimal(Animal a){
        this.trackedAnimal = a;
    }

    public void updateAliveAnimalCount(Integer n){
        this.aliveAnimalCount +=n;
    }

    public void updateTotalAnimalCount(Integer n){
        this.totalAnimalCount+=n;
    }

    public void updateTodayDeaths(Integer val){
        this.todayDied = val;
    }

    public Integer getDeathsToday(){
        return this.todayDied;
    }

    public Animal getTrackedAnimal(){
        return this.trackedAnimal;

    }

    public Integer getGrassCount(){
        return this.map.getMapGrass().size();
    }

    public Integer getAliveAnimalCount(){
        return this.aliveAnimalCount;
    }

    public Genome mostPopularGenome(){
        return this.genomesSortedByCount.get(0);
    }

    public double getMeanLifeLength(){
        return this.totalDaysLived/this.totalAnimalCount;
    }


    public Integer getDeathCount(){
        return this.deathAccumulator;
    }
    public Integer getEmptyFieldCount(){
        //HashSet<Vector2d> animalSet = (HashSet<Vector2d>) this.map.getMapAnimals().keySet();
        //HashSet<Vector2d> grassSet = (HashSet<Vector2d>) this.map.getMapGrass().keySet();

        //HashSet<Vector2d> totalSet = new HashSet<>();
        //totalSet.addAll(animalSet);
        //totalSet.addAll(grassSet);

        return 0;
    }

    public void averageEnergy(){
        int s = 0;
        int n = 0;


    }

    public void updateDeath(int n){
        this.deathAccumulator+=n;
    }
}
