package agh.ics.oop;

import agh.ics.oop.variants.mutations.Mutator;

import java.util.Arrays;
import java.util.Random;

public class Genome {
    final int n; //TODO: usunac i zmienic odwolania do n na getGenomeLength()
    public Gene[] genome;
    private int currentGeneIndex;

    public final Mutator mutator;

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(Gene i: this.genome){
            s.append(i).append(" ");
        }
        return s.toString();
    }

    public Genome(Mutator mutator){
        this.n = mutator.getGenomeLength();
        this.genome = new Gene[n];
        Random r = new Random();
        for(int i = 0;i<n;i++){
            this.genome[i] = Gene.intToGene(r.nextInt(8));
        }
        this.currentGeneIndex = 0;
        this.mutator = mutator;

    }



    public Genome(int[] arr, Mutator mutator){
        this.n = mutator.getGenomeLength();
        this.mutator = mutator;
        this.genome = Gene.intArrToGene(arr,n);
        this.currentGeneIndex = 0;
    }

    public Genome(Gene[] arr, Mutator mutator){
        this.n = mutator.getGenomeLength();
        this.genome=arr;
        this.mutator = mutator;
        this.currentGeneIndex=0;
    }

    public int getGenomeLength(){
        return this.mutator.getGenomeLength();
    }
    public int[] asInt(){
        int[] newArr = new int[this.n];
        for(int i = 0;i<this.n;i++){
            newArr[i] = Gene.GeneToInt(genome[i]);
        }
        return newArr;
    }
    public Gene currentGene(){
        return this.genome[currentGeneIndex];
    }

    public void nextGene(){
        this.currentGeneIndex = Math.floorMod(this.currentGeneIndex+1,this.n);
    }

    public void nextRandomGene(){
        Random r = new Random();
        this.currentGeneIndex = r.nextInt(this.n);
    }

    public static Genome crossGenes(Animal a1, Animal a2){
        Genome g1 = a1.getGenome();
        Genome g2 = a2.getGenome();

        int n = g1.n;

        double g1percentage = (double)a1.getEnergy()/(a1.getEnergy() + a2.getEnergy());
        int g1len = (int) (n*g1percentage);
        int g2len = n - g1len;

        Gene[] newGenome = new Gene[n];
        if(Math.random() <= 0.5) {
                if(a1.getEnergy()>=a2.getEnergy()){
                    if (g1len >= 0) System.arraycopy(g1.genome, 0, newGenome, 0, g1len);
                    if (n - g1len >= 0) System.arraycopy(g2.genome, g1len, newGenome, g1len, n - g1len);
                }
                else{
                    if (g2len >= 0) System.arraycopy(g2.genome, 0, newGenome, 0, g2len);
                    if (n - g2len >= 0) System.arraycopy(g1.genome, g2len, newGenome, g2len, n - g2len);
                }
            }
            else {
                if(a1.getEnergy()>=a2.getEnergy()){
                    if (g2len >= 0) System.arraycopy(g2.genome, 0, newGenome, 0, g2len);
                    if (n - g2len >= 0) System.arraycopy(g1.genome, g2len, newGenome, g2len, n - g2len);
                }
                else{
                    if (g1len >= 0) System.arraycopy(g1.genome, 0, newGenome, 0, g1len);
                    if (n - g1len >= 0) System.arraycopy(g2.genome, g1len, newGenome, g1len, n - g1len);
                }

            }
        Genome ngenome = new Genome(newGenome, g1.mutator);

        g1.mutator.mutateNewGenome(ngenome);
        return ngenome;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other){
            return true;
        }

        if(!(other instanceof Genome)){
            return false;
        }

        Genome t = (Genome) other;

        return Arrays.equals(this.genome,t.genome);
    }

    @Override
    public int hashCode(){
        return Arrays.hashCode(this.genome);
    }



}
