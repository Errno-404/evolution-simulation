package agh.ics.oop.variants.mutations;

import agh.ics.oop.Gene;
import agh.ics.oop.Genome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public abstract class Mutator { //TODO: Add min mutations
    final int minGenomeMutations;
    final int maxGenomeMutations;
    final int genomeLength;

    protected Mutator(int genomeLength, int minGenomeMutations,int maxGenomeMutations ) {
        this.minGenomeMutations = minGenomeMutations;
        this.maxGenomeMutations = maxGenomeMutations;
        this.genomeLength = genomeLength;
    }

    public int getGenomeLength(){
        return genomeLength;
    }
    public int getMaxGenomeMutations() {
        return maxGenomeMutations;
    }

    abstract Gene mutate(Gene g);

    public void mutateNewGenome(Genome g){
        Random r1 = new Random();

        int numOfMutations = r1.nextInt(this.minGenomeMutations,this.maxGenomeMutations+1);
        if(numOfMutations > g.getGenomeLength()){
            numOfMutations = g.getGenomeLength();
        }
        int genomeLength = g.getGenomeLength();

        ArrayList<Integer> indices = new ArrayList<Integer>();
        for(int i = 0;i<genomeLength;i++){
            indices.add(i);
        }
        Collections.shuffle(indices);
        for(int i = 0;i<numOfMutations;i++){
            g.genome[indices.get(i)] = this.mutate(g.genome[i]);
        }
    }
}
