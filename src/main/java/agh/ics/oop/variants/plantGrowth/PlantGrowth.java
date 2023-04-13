package agh.ics.oop.variants.plantGrowth;

import agh.ics.oop.Vector2d;
import agh.ics.oop.variants.maps.AbstractWorldMap;

import java.util.ArrayList;

public abstract class PlantGrowth {
    int mapWidth;
    int mapHeight;
    protected final float preferredPercentage;

    protected final float preferredChance;

    ArrayList<Vector2d> preferredFields;
    ArrayList<Vector2d> otherFields;

    ArrayList<Vector2d> preferredFieldsLeft;
    ArrayList<Vector2d> otherFieldsLeft;

    protected PlantGrowth(int mapWidth, int mapHeight,float preferredPercentage){
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.preferredPercentage = preferredPercentage;
        this.preferredChance = (float) 0.8;
    }

    protected PlantGrowth(int mapWidth, int mapHeight,float preferredPercentage, float preferredChance){
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.preferredPercentage = preferredPercentage;
        this.preferredChance = preferredChance;
    }

    abstract public boolean grow(AbstractWorldMap map);

    public int growN(AbstractWorldMap map, int n){
        int totalGrown = 0;
        for(int i = 0;i<n;i++){
            if(grow(map)){
                totalGrown+=1;
            }
        }

        return totalGrown;
    }

    public ArrayList<Vector2d> getPreferredFields(){
        return this.preferredFields;
    }

}
