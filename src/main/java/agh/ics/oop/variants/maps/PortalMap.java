package agh.ics.oop.variants.maps;

import agh.ics.oop.Animal;
import agh.ics.oop.Config;
import agh.ics.oop.Vector2d;
import agh.ics.oop.variants.behaviour.Behaviour;
import agh.ics.oop.variants.mutations.Mutator;
import agh.ics.oop.variants.plantGrowth.PlantGrowth;

import java.util.Random;

public class PortalMap extends AbstractWorldMap {

    public PortalMap(Config config, Mutator mutator, PlantGrowth plantGrowth, Behaviour behaviour){
        super(config, mutator, plantGrowth, behaviour);

    }

    public PortalMap(Config config, Mutator mutator, PlantGrowth plantGrowth, Behaviour behaviour, int initGrass) {
        super(config, mutator, plantGrowth, behaviour, initGrass);
    }



    @Override
    public void handleMovement(Animal a, Vector2d position) {
        Random random = new Random();
        int x = random.nextInt(this.mapConfig.mapWidth());
        int y = random.nextInt(this.mapConfig.mapHeight());
        a.setEnergy(a.getEnergy() - this.mapConfig.energyLoss());
        a.setPosition(new Vector2d(x, y));
    }
}
