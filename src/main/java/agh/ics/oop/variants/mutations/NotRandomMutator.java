package agh.ics.oop.variants.mutations;

import agh.ics.oop.Gene;

import java.util.Random;

public class NotRandomMutator extends Mutator {

    public NotRandomMutator(int genomeLength, int minGenomeMutations,int maxGenomeMutations ) {
        super(genomeLength, minGenomeMutations, maxGenomeMutations);
    }

    @Override
    public Gene mutate(Gene g) {
        Random r1 = new Random();
        int c = r1.nextInt(2);
        if(c==0){
            g = g.next();
        }
        else{
            g= g.prev();
        }
        return g;
    }
}
