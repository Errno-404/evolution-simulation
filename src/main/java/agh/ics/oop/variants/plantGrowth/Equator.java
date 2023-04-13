package agh.ics.oop.variants.plantGrowth;


import agh.ics.oop.Grass;
import agh.ics.oop.Vector2d;
import agh.ics.oop.variants.maps.AbstractWorldMap;
import java.util.ArrayList;


public class Equator extends PlantGrowth {


    public Equator(int mapWidth, int mapHeight, float preferredPercentage, float chance) {
        super(mapWidth, mapHeight, preferredPercentage, chance);

        float excludeSize = mapHeight * (1 - preferredPercentage)/2;
        int wholeRows = (int) excludeSize;
        float partOfRow = excludeSize - wholeRows;

        this.preferredFields = new ArrayList<>();
        this.otherFields = new ArrayList<>();
        for (int i = wholeRows; i < mapHeight - wholeRows; i++) {
            for (int j = 0; j < mapWidth; j++) {
                this.preferredFields.add(new Vector2d(j, i));
            }
        }

        for (int i = 0; i <= wholeRows; i++) {
            for (int j = 0; j < mapWidth; j++) {
                this.otherFields.add(new Vector2d(j, i));
                this.otherFields.add(new Vector2d(j, mapHeight - i));
            }
        }

        /*if (partOfRow > 0) {
            int a = (int) (mapWidth * (1 - partOfRow) / 2);

            for (int x = a; x < mapWidth - a; x++) {
                this.preferredFields.add(new Vector2d(x, wholeRows));
                this.preferredFields.add(new Vector2d(x, mapHeight - wholeRows));
            }

            for (int x = 0; x <= a; x++) {
                this.otherFields.add(new Vector2d(x, wholeRows));
                this.otherFields.add(new Vector2d(mapWidth - x, wholeRows));
                this.otherFields.add(new Vector2d(x, mapHeight - wholeRows));
                this.otherFields.add(new Vector2d(mapWidth - x, mapHeight - wholeRows));
            }
        }*/

        this.preferredFieldsLeft = new ArrayList<>(preferredFields);
        this.otherFieldsLeft = new ArrayList<>(otherFields);

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

    public void reportGrassEaten(Vector2d pos){
        if(this.preferredFields.contains(pos)){
            this.preferredFieldsLeft.add(pos);
        }
        else{
            this.otherFieldsLeft.add(pos);
        }
    }
}
