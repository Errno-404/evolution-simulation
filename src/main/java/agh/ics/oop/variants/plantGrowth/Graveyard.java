package agh.ics.oop.variants.plantGrowth;

import agh.ics.oop.Grass;
import agh.ics.oop.Vector2d;
import agh.ics.oop.variants.maps.AbstractWorldMap;


import java.util.*;

public class Graveyard extends PlantGrowth {


    HashMap<Vector2d, Integer> deathCounter;

    ArrayList<Vector2d> fieldsSortedByDeath = new ArrayList<>();

    /*ArrayList<Vector2d> preferredFieldsLeft = new ArrayList<>();
    ArrayList<Vector2d> otherFieldsLeft = new ArrayList<>();*/

    public Graveyard(int mapWidth, int mapHeight,float percentage, float chance) {
        super(mapWidth, mapHeight, percentage, chance);

        this.preferredFields = new ArrayList<>();
        this.otherFields = new ArrayList<>();

         this.preferredFieldsLeft = new ArrayList<>();
         this.otherFieldsLeft = new ArrayList<>();

        for(int x = 0;x<mapWidth;x++){
            for(int y = 0;y<mapHeight;y++){
                fieldsSortedByDeath.add(new Vector2d(x,y));
            }
        }
        int borderIndex = (int)(this.fieldsSortedByDeath.size()*this.preferredPercentage);
        this.preferredFieldsLeft.clear();
        for(int i = 0;i<=borderIndex;i++){
            preferredFieldsLeft.add(this.fieldsSortedByDeath.get(i));
            preferredFields.add(this.fieldsSortedByDeath.get(i));
        }

        this.otherFieldsLeft.clear();
        for(int i = borderIndex+1;i<fieldsSortedByDeath.size();i++){
            otherFieldsLeft.add(this.fieldsSortedByDeath.get(i));
            otherFields.add(this.fieldsSortedByDeath.get(i));
        }


    }

    public void updateSet(AbstractWorldMap map) {
        this.deathCounter = map.getDeathCounter();

        this.fieldsSortedByDeath.sort((v1, v2) -> {
            int v1val;
            int v2val;
            v1val = this.deathCounter.getOrDefault(v1, 0);
            v2val = this.deathCounter.getOrDefault(v2,0);

            return v1val-v2val;
        });


        int borderIndex = (int) (this.fieldsSortedByDeath.size()*this.preferredPercentage);

        this.preferredFields.clear();
        this.preferredFieldsLeft.clear();
        for(int i = 0;i<=borderIndex;i++){
            preferredFieldsLeft.add(this.fieldsSortedByDeath.get(i));
            preferredFields.add(this.fieldsSortedByDeath.get(i));
        }

        this.otherFieldsLeft.clear();
        this.otherFields.clear();
        for(int i = borderIndex+1;i<fieldsSortedByDeath.size();i++){
            otherFields.add(this.fieldsSortedByDeath.get(i));
            otherFieldsLeft.add(this.fieldsSortedByDeath.get(i));
        }

        for(int i = 0;i<this.fieldsSortedByDeath.size();i++){
            Vector2d el = this.fieldsSortedByDeath.get(i);
            if(i<=borderIndex){
                if(map.getMapGrass().containsKey(el)){
                    preferredFieldsLeft.remove(el);
                }
            }
             else{
                    if(map.getMapGrass().containsKey(el)){
                        otherFieldsLeft.remove(el);
                }
            }
        }

    }

    @Override
    public boolean grow(AbstractWorldMap map) {
        if(this.preferredFieldsLeft.size() == 0 && this.otherFieldsLeft.size() == 0){
            return false;
        }

        int elIndex;
        Vector2d v2;
        double randres = Math.random();

        if(randres <= this.preferredChance && this.preferredFieldsLeft.size() == 0){
            randres = this.preferredChance + 0.0001;
        }
        else if(randres > this.preferredChance && this.otherFieldsLeft.size()==0){
            randres = 0;
        }

        if (randres <= this.preferredChance) {

            elIndex = (int) (Math.random() * this.preferredFieldsLeft.size());

            v2 = this.preferredFieldsLeft.get(elIndex);
        } else {
            if(this.otherFieldsLeft.size() == 0){
                return false;
            }
            elIndex = (int) (Math.random() * this.otherFieldsLeft.size());
            v2 = this.otherFieldsLeft.get(elIndex);
        }

        if(randres <= this.preferredChance){
            this.preferredFieldsLeft.remove(v2);
        }
        else{
            this.otherFieldsLeft.remove(v2);
        }

        map.place(new Grass(v2));
        return true;
    }
}
