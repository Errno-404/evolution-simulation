package agh.ics.oop;


import agh.ics.oop.variants.behaviour.Behaviour;
import agh.ics.oop.variants.behaviour.NotRandomBehaviour;
import agh.ics.oop.variants.behaviour.RandomBehaviour;
import agh.ics.oop.variants.mutations.Mutator;
import agh.ics.oop.variants.mutations.NotRandomMutator;
import agh.ics.oop.variants.mutations.RandomMutator;
import agh.ics.oop.variants.plantGrowth.Equator;
import agh.ics.oop.variants.plantGrowth.Graveyard;
import agh.ics.oop.variants.plantGrowth.PlantGrowth;


public record Config(int mapHeight,
                     int mapWidth,
                     int initialGrassCount,
                     int mapType,

                     int plantEnergy,
                     int dailyPlantGrowthCount,
                     int plantGrowthType,
                     int preferredPercentage,
                     int preferredChance,


                     int initialAnimalCount,
                     int initialAnimalEnergy,
                     int readyToBreedEnergy,
                     int energyLoss,
                     int genomeLength,

                     int minMutations,
                     int maxMutations,
                     int mutatorType,

                     int behaviourType,
                     int saveToFile
                     ){

    public Config(int[] arr) { //musi sie dac to lepiej zrobic >>___>>
        this(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13],
                arr[14], arr[15], arr[16], arr[17], arr[18]);
    }

    public PlantGrowth getPlantGrowthFromConfig(){
        switch(this.plantGrowthType){
            case 0: {
                return new Equator(mapWidth, mapHeight, (float)preferredPercentage/100, (float)preferredChance/100);
            }
            case 1:{
                return new Graveyard(mapWidth, mapHeight, (float)preferredPercentage/100, (float)preferredChance/100);
            }
            default:{
                throw new IllegalStateException("Unexpected PlantGrowth (" +this.plantGrowthType + ") type config parameter (should be either 0 or 1)");
            }
        }
    }

    public Behaviour getBehaviourFromConfig(){
        switch(this.behaviourType){
            case 0:{
                return new NotRandomBehaviour();
            }
            case 1:{
                return new RandomBehaviour();
            }
            default:{
                throw new IllegalStateException("Unexpected Behaviour (" +this.behaviourType + ")  type config parameter (should be either 0 or 1)");
            }

        }
    }

    public Mutator getMutatorFromConfig(){
        switch(this.mutatorType){
            case 0:{
                return new NotRandomMutator(genomeLength, minMutations, maxMutations);
            }
            case 1:{
                return new RandomMutator(genomeLength, minMutations, maxMutations);
            }
            default:{
                throw new IllegalStateException("Unexpected Mutator  (" +this.mutatorType + ") type config parameter (should be either 0 or 1)");
            }
        }
    }

    @Override
    public String toString() {
        String mparams = String.valueOf(mapHeight);
        mparams += mapWidth;
        mparams+=initialGrassCount;
        mparams+=mapType;

        String pparams = String.valueOf(plantEnergy);
        pparams+=dailyPlantGrowthCount;
        pparams+=plantGrowthType;
        pparams+=preferredPercentage;
        pparams+=preferredChance;

        String aparams = String.valueOf(initialAnimalCount);
        aparams+=initialAnimalEnergy;
        aparams+=readyToBreedEnergy;
        aparams+=energyLoss;
        aparams+=genomeLength;

        String muparams = String.valueOf(maxMutations);
        muparams+=minMutations;
        muparams+=mutatorType;

        String beparams = String.valueOf(behaviourType);

        return mparams+pparams+aparams+muparams+beparams;
    }

    /*int mapHeight;
    int mapWidth;
    int initialGrassCount;
    int mapType; //0-Globe, 1-Portal

    int plantEnergy;
    int dailyPlantGrowthCount;
    int plantGrowthType; //0-Equator, 1-Graveyard
    int preferredPercentage;

    int initialAnimalCount;
    int initialAnimalEnergy;
    int readyToBreedEnergy;
    int energyLoss;
    int genomeLength;

    int maxMutations;
    int minMutations;
    int mutatorType; //0-notrandom, 1-random

    int behaviourType; //0-notrandom, 1-random*/

}






