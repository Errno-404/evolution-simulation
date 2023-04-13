package agh.ics.oop;

import agh.ics.oop.variants.behaviour.Behaviour;

import agh.ics.oop.variants.maps.AbstractWorldMap;
import agh.ics.oop.variants.maps.Globe;
import agh.ics.oop.variants.maps.PortalMap;
import agh.ics.oop.variants.mutations.Mutator;
import agh.ics.oop.variants.plantGrowth.Graveyard;
import agh.ics.oop.variants.plantGrowth.PlantGrowth;
import java.util.*;

// implements Runnable
// synchronized
public class SimulationEngine implements Runnable, IPositionChangeObserver {


    private final Config config;
    private final AbstractWorldMap map;

    private final String xlsPath; //dodac ustawianie pliku do konstruktora albo gdzies indziej

    public List<IPositionChangeObserver> observers = new LinkedList<>();

    public SimulationEngine(Config config){
        this.config = config;
        Mutator mutator = config.getMutatorFromConfig();
        Behaviour behaviour = config.getBehaviourFromConfig();
        PlantGrowth plantGrowth = config.getPlantGrowthFromConfig();

        this.map = switch (config.mapType()){
            case 0 -> new Globe(config, mutator, plantGrowth, behaviour);
            case 1 -> new PortalMap(config,mutator,plantGrowth,behaviour);
            default -> throw new IllegalStateException("Unexpected value: " + config.mapType());
        };

        if(config.saveToFile()==1){
            this.xlsPath = "src/main/CsvFiles/" + config + ".csv";
        }
        else{
            this.xlsPath = null;
        }

    }


    public AbstractWorldMap getMap(){
        return this.map;
    }

    @Override
    public synchronized void run(){

        if(this.xlsPath != null){
            CsvParser.createXlsFile(this.xlsPath);
        }


        // symulacja::
    int day = 0;
    for(int j = 0; j < 999; j ++){
        Collection<ArrayList<Animal>> mapAnimals = this.map.getMapAnimals().values();
        ArrayList<Animal> alive = new ArrayList<>();

        ArrayList<Animal> died = new ArrayList<>();

        double energysum = 0;
        for(ArrayList<Animal> animalList: mapAnimals){
            for(Animal a: animalList){
                if(a.getEnergy() <= 0){
                    died.add(a);
                }
                else{
                    alive.add(a);
                    energysum+=a.getEnergy();
                }
            }
        }

        this.map.stats.updateMenaEnergy(energysum/this.map.stats.getAliveAnimalCount());
        this.map.stats.updateTotalDaysLived(alive.size());

        for(Animal a1: died){
            a1.setDeathDate(day);
            this.map.reportDeath(a1);
        }
        if(this.map.getPlantGrowth() instanceof Graveyard){
            ((Graveyard) this.map.getPlantGrowth()).updateSet(this.map);
        }


        // poruszanie zwierzyną
        for(Animal animal: alive){
            animal.move();
            positionChanged(null, null);
        }
        // jedzenie
        Set<Vector2d> eaten = new HashSet<>();

        for(Vector2d vec : this.map.getMapGrass().keySet()){
            if(this.map.getMapAnimals().containsKey(vec)){
                this.map.getMapAnimals().get(vec).get(0).eat(this.map.getMapGrass().get(vec));
                eaten.add(vec);
            }
        }

        this.map.getMapGrass().keySet().removeAll(eaten);

        // rozmnażanie
        ArrayList<Animal> newBorns = new ArrayList<>();
        for(ArrayList<Animal> animals : this.map.getMapAnimals().values()){
            int n = animals.size();
            if(n > 1){

                int m = n / 2;

                for(int i = 0; i < m; i ++){
                    if(animals.get(i).readyToBread(animals.get(i + 1))){
                        newBorns.add(animals.get(i).breed(animals.get(i+1)));
                    }
                }
            }
        }

        for(Animal animal : newBorns){
            this.map.place(animal);
        }


        this.map.getPlantGrowth().growN(this.map, this.config.dailyPlantGrowthCount());


        this.map.stats.updateTodayDeaths(died.size());
        CsvParser.appendDailyStats(this.xlsPath,this.map.stats, day);
        this.map.stats.updateDay();
        day+=1;

    }
    }


    @Override
    public void positionChanged(Animal animal, Vector2d newPosition){
        this.observers.forEach( (obs) -> obs.positionChanged(animal, newPosition));
    }

    public void addObserver(IPositionChangeObserver observer){
        this.observers.add(observer);
    }

}
