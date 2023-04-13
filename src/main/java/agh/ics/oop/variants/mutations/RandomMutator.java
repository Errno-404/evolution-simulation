package agh.ics.oop.variants.mutations;

import agh.ics.oop.Gene;

public class RandomMutator extends Mutator {
    public RandomMutator(int genomeLength, int minGenomeMutations,int maxGenomeMutations ) {
        super(genomeLength, minGenomeMutations, maxGenomeMutations);
    }

    @Override
    public Gene mutate(Gene g) {
        g=g.mutateRandom(g);
        return g;
    }
}
