package agh.ics.oop;

import agh.ics.oop.variants.mutations.Mutator;
import agh.ics.oop.variants.mutations.NotRandomMutator;
import agh.ics.oop.variants.mutations.RandomMutator;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GenomeTest {

    Genome crossWithoutMutationLeft(Animal a1, Animal a2){
        Genome g1 = a1.getGenome();
        Genome g2 = a2.getGenome();

        int n = g1.n;


        double g1percentage = (double)a1.getEnergy()/(a1.getEnergy() + a2.getEnergy());
        int g1len = (int) (n*g1percentage);
        int g2len = n - g1len;


        Random rand = new Random();

        int sideChoice = rand.nextInt(2); //0 - left, 1-right (of the animal with more energy)

        Gene[] newGenome = new Gene[n];
        sideChoice = 0;
        switch (sideChoice) {
            case 0 -> {
                if(a1.getEnergy()>=a2.getEnergy()){
                    for(int i = 0;i<g1len;i++){
                        newGenome[i] = g1.genome[i];
                    }
                    for(int i = g1len;i<n;i++){
                        newGenome[i] = g2.genome[i];
                    }
                }
                else{
                    for(int i = 0;i<g2len;i++){
                        newGenome[i] = g2.genome[i];
                    }
                    for(int i = g2len;i<n;i++){
                        newGenome[i] = g1.genome[i];
                    }
                }
            }
            case 1 -> {
                if(a1.getEnergy()>=a2.getEnergy()){
                    for(int i = 0;i<g2len;i++){
                        newGenome[i] = g2.genome[i];
                    }
                    for(int i = g2len;i<n;i++){
                        newGenome[i] = g1.genome[i];
                    }
                }
                else{
                    for(int i = 0;i<g1len;i++){
                        newGenome[i] = g1.genome[i];
                    }
                    for(int i = g1len;i<n;i++){
                        newGenome[i] = g2.genome[i];
                    }
                }

            }
        }



        Genome ngenome = new Genome(newGenome, g1.mutator);
        return ngenome;
    }

    Genome crossWithoutMutationRight(Animal a1, Animal a2){
        Genome g1 = a1.getGenome();
        Genome g2 = a2.getGenome();

        int n = g1.n;


        double g1percentage = (double)a1.getEnergy()/(a1.getEnergy() + a2.getEnergy());
        double g2percentage = 1 - g1percentage;
        int g1len = (int) (n*g1percentage);
        int g2len = n - g1len;


        Random rand = new Random();

        int sideChoice = rand.nextInt(2); //0 - left, 1-right (of the animal with more energy)

        Gene[] newGenome = new Gene[n];
        sideChoice = 1;
        switch (sideChoice) {
            case 0 -> {
                if(a1.getEnergy()>=a2.getEnergy()){
                    for(int i = 0;i<g1len;i++){
                        newGenome[i] = g1.genome[i];
                    }
                    for(int i = g1len;i<n;i++){
                        newGenome[i] = g2.genome[i];
                    }
                }
                else{
                    for(int i = 0;i<g2len;i++){
                        newGenome[i] = g2.genome[i];
                    }
                    for(int i = g2len;i<n;i++){
                        newGenome[i] = g1.genome[i];
                    }
                }
            }
            case 1 -> {
                if(a1.getEnergy()>=a2.getEnergy()){
                    for(int i = 0;i<g2len;i++){
                        newGenome[i] = g2.genome[i];
                    }
                    for(int i = g2len;i<n;i++){
                        newGenome[i] = g1.genome[i];
                    }
                }
                else{
                    for(int i = 0;i<g1len;i++){
                        newGenome[i] = g1.genome[i];
                    }
                    for(int i = g1len;i<n;i++){
                        newGenome[i] = g2.genome[i];
                    }
                }

            }
        }
        return new Genome(newGenome, g1.mutator);
    }

    boolean isPossibleMutation(Animal a1, Animal a2, Mutator m, Genome res){ //TODO: dziala dobrze tylko trzeba porownywac zmiany genow do genow animali a nie wynikowych ;p

        Genome leftRes = crossWithoutMutationLeft(a1,a2);
        Genome rightRes = crossWithoutMutationRight(a1,a2);

        int max = m.getMaxGenomeMutations();
        int n = res.n;

        if (res.equals(leftRes) || res.equals(rightRes)){
            return true;
        }

        int num = m.getMaxGenomeMutations();
        if(m instanceof NotRandomMutator){
            int mutationCounterleft = 0;
            int mutationCounterright = 0;

            int correctMutationLeft = 0;
            int correctMutationRight = 0;

            System.out.println(a1.getGenome() + "  " + a1.getEnergy());
            System.out.println(a2.getGenome() + "  " + a2.getEnergy());
            System.out.println("\n");
            System.out.println(res);
            System.out.println(leftRes);
            System.out.println(rightRes);
            System.out.println("\n");

            for(int i = 0;i<n;i++){
                if(res.genome[i] != leftRes.genome[i]){
                    mutationCounterleft+=1;
                    Gene possibleRes1 = a1.getGenome().genome[i].prev();
                    Gene possibleRes2 = a1.getGenome().genome[i].next();

                    if(res.genome[i] == possibleRes1 || res.genome[i] == possibleRes2){
                        correctMutationLeft +=1;
                    }
                }
            }

            for(int i = 0;i<n;i++){
                if(res.genome[i] != rightRes.genome[i]){
                    mutationCounterright+=1;

                    Gene possibleRes1 = a1.getGenome().genome[i].prev();
                    Gene possibleRes2 = a1.getGenome().genome[i].next();
                    if(res.genome[i] == possibleRes1 || res.genome[i] == possibleRes2){
                        correctMutationRight +=1;
                    }
                }
            }
            System.out.println(mutationCounterleft + " " + correctMutationLeft +"    " + mutationCounterright + " "+correctMutationRight);
            return (mutationCounterleft <= max && correctMutationLeft==mutationCounterleft) ||
                    (mutationCounterright <= max && correctMutationRight == mutationCounterright);
        } else if (m instanceof RandomMutator) {

            int mutationCounterLeft = 0;
            int mutationCounterRight = 0;

            for(int i = 0;i<n;i++){
                if(res.genome[i]!=leftRes.genome[i]){
                    mutationCounterLeft+=1;
                }

                if(res.genome[i] != rightRes.genome[i]){
                    mutationCounterRight+=1;
                }
            }

            return mutationCounterLeft <= max || mutationCounterRight <= max;


        }
        else{
            throw new RuntimeException();
        }
    }
    @Test
    void crossGenes() {
        int mutparam = 3;
        Mutator random = new RandomMutator(10,0, mutparam);
        Mutator notrandom = new NotRandomMutator(10,0, mutparam);

        Animal[] testA = {
                new Animal(10, new Genome(random)),
                new Animal(20, new Genome(random)),
                new Animal(30, new Genome(random)),
                new Animal(40,new Genome(random)),
                new Animal(50, new Genome(random)),
                new Animal(10, new Genome(notrandom)),
                new Animal(20, new Genome(notrandom)),
                new Animal(30, new Genome(notrandom)),
                new Animal(40,new Genome(notrandom)),
                new Animal(50, new Genome(notrandom))
        };

        Animal[] testB = {
                new Animal(90, new Genome(random)),
                new Animal(80, new Genome(random)),
                new Animal(70, new Genome(random)),
                new Animal(60,new Genome(random)),
                new Animal(50, new Genome(random)),
                new Animal(90, new Genome(notrandom)),
                new Animal(80, new Genome(notrandom)),
                new Animal(70, new Genome(notrandom)),
                new Animal(60,new Genome(notrandom)),
                new Animal(50, new Genome(notrandom))
        };

        for(int i = 0;i< testA.length;i++){
            Genome res = Genome.crossGenes(testA[i],testB[i]);
            assertTrue(isPossibleMutation(testA[i],testB[i],testA[i].getGenome().mutator, res));
        }
    }
}