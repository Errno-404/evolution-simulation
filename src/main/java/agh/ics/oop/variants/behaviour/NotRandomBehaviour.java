package agh.ics.oop.variants.behaviour;

import agh.ics.oop.Genome;

public class NotRandomBehaviour implements Behaviour{
    @Override
    public void setNextGene(Genome g) {
        g.nextGene();
    }
}
