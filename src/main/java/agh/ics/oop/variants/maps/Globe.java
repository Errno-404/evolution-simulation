package agh.ics.oop.variants.maps;

import agh.ics.oop.Animal;
import agh.ics.oop.Config;
import agh.ics.oop.Gene;
import agh.ics.oop.Vector2d;
import agh.ics.oop.variants.behaviour.Behaviour;
import agh.ics.oop.variants.mutations.Mutator;
import agh.ics.oop.variants.plantGrowth.PlantGrowth;

public class Globe extends AbstractWorldMap {

    public Globe(Config config, Mutator mutator, PlantGrowth plantGrowth, Behaviour behaviour) {
        super(config, mutator, plantGrowth, behaviour);
    }

    public Globe(Config config, Mutator mutator, PlantGrowth plantGrowth, Behaviour behaviour, int initGrass) {
        super(config, mutator, plantGrowth, behaviour, initGrass);
    }

    @Override
    public void handleMovement(Animal a, Vector2d position) {

        if(position.getY() < this.lowerLeft.getY() || position.getY() > this.upperRight.getY()){
            // próba wyjścia poza biegun
            a.setOrientation(a.getOrientation().turn(Gene.intToGene(4)));
        }
        else if(position.getX() < this.lowerLeft.getX()){
            // teleportowanie zwierzęcia na prawą stronę zgodnie z jego kierunkeiem ruchu!
            Vector2d newPosition = position
                    .add(new Vector2d(this.mapConfig.mapWidth(), 0));
            a.setPosition(newPosition);

        }
        else{
            Vector2d newPosition = position
                    .subtract(new Vector2d(this.mapConfig.mapWidth(), 0));
            a.setPosition(newPosition);

        }

    }
}
