package agh.ics.oop.variants.behaviour;

import agh.ics.oop.Genome;

import java.util.Random;

public class RandomBehaviour implements Behaviour{
    @Override
    public void setNextGene(Genome g) {
        Random random = new Random();
        int r = random.nextInt(5) + 1;
        if(r > 1){
            g.nextGene();
        }
        else{
            g.nextRandomGene();
        }


    }
}


