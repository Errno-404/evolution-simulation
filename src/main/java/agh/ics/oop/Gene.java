// taki tam szybki test pusha


package agh.ics.oop;

import java.util.Random;

public enum Gene {

    FORWARD,
    FORWARDRIGHT,
    RIGHT,
    BACKRGIHT,
    BACK,
    BACKLEFT,
    LEFT,
    FORWARDLEFT;

    @Override
    public String toString() {
        return super.toString();
    }

    public static Gene intToGene(int n){
        return switch (n) {
            case 0 -> FORWARD;
            case 1 -> FORWARDRIGHT;
            case 2 -> RIGHT;
            case 3 -> BACKRGIHT;
            case 4 -> BACK;
            case 5 -> BACKLEFT;
            case 6 -> LEFT;
            case 7 -> FORWARDLEFT;
            default -> throw new IllegalStateException("Unexpected value: " + n + ", expected number in range (0,7)");
        };
    }

    public static int GeneToInt(Gene g){
        return switch(g){
            case FORWARD -> 0;
            case FORWARDRIGHT -> 1;
            case RIGHT -> 2;
            case BACKRGIHT -> 3;
            case BACK -> 4;
            case BACKLEFT -> 5;
            case LEFT -> 6;
            case FORWARDLEFT -> 7;
        };
    }

    public static Gene[] intArrToGene(int[] arr, int n){
        Gene[] garr = new Gene[n];
        for(int i = 0;i<n;i++){
            garr[i] = intToGene(arr[i]);
        }
        return garr;
    }

    public Gene next(){
        int gnum = GeneToInt(this);
        gnum = Math.floorMod(gnum+1, 8);
        return intToGene(gnum);
    }

    public Gene prev(){
        int gnum = GeneToInt(this);
        gnum = Math.floorMod(gnum-1, 8);
        return intToGene(gnum);
    }

    public Gene mutateRandom(Gene g){
        Random rng = new Random();
        int notAllowed = GeneToInt(g);
        int randRes = rng.nextInt(8);
        while(randRes==notAllowed){ //TODO: może zmienić losowanie
            randRes = rng.nextInt(8);
        }

        return intToGene(randRes);
    }


}
