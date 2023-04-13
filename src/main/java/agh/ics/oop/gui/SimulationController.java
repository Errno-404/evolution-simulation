package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.fxml.FXML;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;



import java.util.ArrayList;


public class SimulationController {

    @FXML
    AnchorPane myBoarderPane;


    GridPane myGridPane = new GridPane();



    Config config;

    BorderPane bPane = new BorderPane();
    int prefSizex = 500;
    int prefSizey = 500;

    boolean isPaused = false;

    SimulationEngine engine;

    Thread sengine;




    //AbstractWorldMap map;
    //SimulationEngine engine = new SimulationEngine(config);


    private  int boxSizex;
    private  int boxSizey;

    public void setEngine(SimulationEngine engine,Thread enginethread){
        this.engine = engine;
        this.sengine = enginethread;
    }

    private void addGridEvent(){
        SimulationEngine fengine = this.engine;
        SimulationController fcontrol = this;
        Config fconfig = this.config;


        myGridPane.getChildren().forEach(item -> item.setOnMouseClicked(event -> {
            if(event.getEventType() == MouseEvent.MOUSE_CLICKED){
                Vector2d clickPosition = new Vector2d(GridPane.getColumnIndex(item), GridPane.getRowIndex(item));
                ArrayList<IMapElement> elements = fengine.getMap().objectAt(clickPosition);

                Animal temp = null;

                if(elements.get(0) instanceof Grass && elements.size() > 1){
                    temp = (Animal) elements.get(1);
                }
                else if(elements.get(0) instanceof Animal){
                    temp = (Animal) elements.get(0);
                }

                if(temp != null){
                    fengine.getMap().stats.updateTrackedAnimal(temp);
                    fcontrol.draw(fconfig, fengine);
                    addGridEvent();
                }

            }
        }));
    }

    private void removeGridEvent(){
        myGridPane.getChildren().forEach(item -> item.setOnMouseClicked(event -> {
        }));
    }

    public void initialize() {
        // TODO

        this.bPane = new BorderPane();



        AnchorPane.setTopAnchor(bPane, 0.0);
        AnchorPane.setBottomAnchor(bPane, 0.0);
        AnchorPane.setLeftAnchor(bPane, 0.0);
        AnchorPane.setBottomAnchor(bPane, 0.0);




        // BOTTOM HBox
        HBox bottom = new HBox();
        bottom.setPadding(new Insets(20));
        bottom.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // Buttons
        Button pause = new Button("Pause");
        bottom.getChildren().add(pause);
        pause.setOnAction(t->{
            if(!this.isPaused){

                this.sengine.suspend();
                this.addGridEvent();
                this.isPaused = true;
            }
            else{
                this.removeGridEvent();
                this.sengine.resume();
                this.isPaused = false;
            }

        });

//        Button showBest = new Button("Najlepszy genotyp");
//        bottom.getChildren().add(showBest);
        bPane.setBottom(bottom);


        // Center - board




        // left panel
        VBox left = new VBox();
        left.setAlignment(Pos.TOP_CENTER);
        left.setPadding(new Insets(20));
        left.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // Title
        HBox TotalAlive = new HBox();
        TotalAlive.getChildren().add(new Label("Toal Alive: "));

        Label stats = new Label("Statystyki:");
        left.getChildren().add(stats);

        HBox daybox = new HBox(2);
        daybox.getChildren().addAll(new Label("day:  "), new Label("0"));

        HBox hboxalive = new HBox(2);
        hboxalive.getChildren().addAll(TotalAlive, new Label("0"));

        HBox hboxDiedThatDay = new HBox(2);
        hboxDiedThatDay.getChildren().addAll(new Label("Died today: "), new Label("0"));

        HBox totalGrass = new HBox(2);
        totalGrass.getChildren().addAll(new Label("Grass Count: "), new Label("0"));

        HBox meanEnergy = new HBox(2);
        meanEnergy.getChildren().addAll(new Label("Mean animal energy: "), new Label("0"));

        HBox meanLifeLength = new HBox(2);
        meanLifeLength.getChildren().addAll(new Label("Mean animal life length: "), new Label("0"));

        left.getChildren().addAll(daybox,hboxalive, hboxDiedThatDay, totalGrass, meanEnergy, meanLifeLength);

        //Tracked stats
        Animal tracked = null;

        Label trackedlabel = new Label("Tracked Animal: ");
        left.getChildren().add(trackedlabel);



        HBox genomeString = new HBox(2);

        HBox genomeActive = new HBox(2);

        HBox energy = new HBox(2);

        HBox grassEaten = new HBox(2);

        HBox childrenCount = new HBox(2);

        HBox diedAt = new HBox(2);


            genomeString.getChildren().addAll(new Label("Genome:  "), new Label("null"));


            genomeActive.getChildren().addAll(new Label("Active Gene:  "), new Label("null"));


            energy.getChildren().addAll(new Label("Energy:  "), new Label("null"));

            grassEaten.getChildren().addAll(new Label("Grass eaten:  "), new Label("null"));


            childrenCount.getChildren().addAll(new Label("Children Count:  "), new Label("null"));

            diedAt.getChildren().addAll(new Label("died:  "), new Label("null"));

        left.getChildren().addAll(genomeString,genomeActive,energy,grassEaten,childrenCount,diedAt);

        bPane.setLeft(left);



//
//        Label right = new Label("left");
//        right.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//        bPane.setRight(right);


        myBoarderPane.getChildren().add(bPane);
    }

    public void updateStats(SimulationEngine engine){
        VBox left = new VBox();
        left.setAlignment(Pos.TOP_CENTER);
        left.setPadding(new Insets(20));
        left.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // Title
        HBox TotalAlive = new HBox();
        TotalAlive.getChildren().add(new Label("Toal Alive: "));

        Label stats = new Label("Statystyki:");
        left.getChildren().add(stats);
        HBox hboxalive = new HBox(2);
        hboxalive.getChildren().addAll(TotalAlive, new Label(engine.getMap().stats.getAliveAnimalCount().toString()));


        HBox daybox = new HBox(2);
        daybox.getChildren().addAll(new Label("day:  "), new Label(engine.getMap().stats.getDay().toString()));

        HBox hboxDiedThatDay = new HBox(2);
        hboxDiedThatDay.getChildren().addAll(new Label("Died today: "), new Label(engine.getMap().stats.getDeathsToday().toString()));

        HBox totalGrass = new HBox(2);
        totalGrass.getChildren().addAll(new Label("Grass Count: "), new Label(engine.getMap().stats.getGrassCount().toString()));

        HBox meanEnergy = new HBox(2);
        meanEnergy.getChildren().addAll(new Label("Mean animal energy: "), new Label(Double.toString(Math.round(engine.getMap().stats.getMeanEnergy()))));

        HBox meanLifeLength = new HBox(2);
        meanLifeLength.getChildren().addAll(new Label("Mean animal life length: "), new Label(Double.toString(Math.round(engine.getMap().stats.getMeanLifeLength()))));

        left.getChildren().addAll(daybox, hboxalive, hboxDiedThatDay, totalGrass, meanEnergy, meanLifeLength);

        //Tracked stats
        Animal tracked = this.engine.getMap().stats.getTrackedAnimal();

        Label trackedlabel = new Label("Tracked Animal: ");
        left.getChildren().add(trackedlabel);



        HBox genomeString = new HBox(2);

        HBox genomeActive = new HBox(2);

        HBox energy = new HBox(2);

        HBox grassEaten = new HBox(2);

        HBox childrenCount = new HBox(2);

        HBox diedAt = new HBox(2);

        HBox orientation = new HBox(2);


        if(tracked != null){

            genomeString.getChildren().addAll(new Label("Genome:  "), new Label(tracked.getGenome().toString()));


            genomeActive.getChildren().addAll(new Label("Active Gene:  "), new Label(tracked.getGenome().currentGene().toString()));

            orientation.getChildren().addAll(new Label("Orientation: "), new Label(tracked.getOrientation().toString()));


            energy.getChildren().addAll(new Label("Energy:  "), new Label(Integer.toString(tracked.getEnergy())));


            grassEaten.getChildren().addAll(new Label("Grass eaten:  "), new Label(Integer.toString(tracked.getGrassEaten())));


            childrenCount.getChildren().addAll(new Label("Children Count:  "), new Label(Integer.toString(tracked.getChildrenCount())));

            Integer day = tracked.getDeathDate();
            if(day!= null){
                diedAt.getChildren().addAll(new Label("died:  "), new Label(day.toString()));
            }
            else{
                diedAt.getChildren().addAll(new Label("died:  "), new Label("Alive"));
            }

        }
        else{

            genomeString.getChildren().addAll(new Label("Genome:  "), new Label("null"));


            genomeActive.getChildren().addAll(new Label("Active Gene:  "), new Label("null"));

            orientation.getChildren().addAll(new Label("Orientation: "), new Label("null"));


            energy.getChildren().addAll(new Label("Energy:  "), new Label("null"));

            grassEaten.getChildren().addAll(new Label("Grass eaten:  "), new Label("null"));


            childrenCount.getChildren().addAll(new Label("Children Count:  "), new Label("null"));


            diedAt.getChildren().addAll(new Label("died:  "), new Label("null"));
        }

        left.getChildren().addAll(genomeString,genomeActive,orientation,energy,grassEaten,childrenCount,diedAt);

        bPane.setLeft(left);

    }

    public void draw(Config config, SimulationEngine engine) /*throws InterruptedException*/ {
        updateStats(engine);
        myGridPane.setGridLinesVisible(false);
        this.myGridPane.getChildren().clear();
        for (int i = 0; i < config.mapWidth(); i++) {
            for (int j = 0; j < config.mapHeight(); j++) {
                ArrayList<IMapElement> elements = engine.getMap().objectAt(new Vector2d(i, j));
                if(engine.getMap().getPlantGrowth().getPreferredFields().contains(new Vector2d(i,j))){
                        myGridPane.add(new Rectangle(boxSizex, boxSizey, Color.TEAL), i, j);
                }
                if (elements != null && elements.size() > 0) {
                    if (elements.get(0) instanceof Grass) {
                        myGridPane.add(new Rectangle(boxSizex, boxSizey, Color.GREEN), i, j);

                    }

                    if ((elements.size() > 1 && elements.get(0) instanceof Grass) || (elements.size() >= 1 && elements.get(0) instanceof Animal)) {
                        boolean added = false;
                        for(IMapElement k: elements) {
                            if (k instanceof Animal && k.equals(this.engine.getMap().stats.getTrackedAnimal())) {
                                myGridPane.add(new Circle(Math.min(boxSizey / 2, boxSizex / 2), Color.ORANGE), i, j);
                                added = true;
                                break;
                            }
                        }
                        if(!added){
                            for(IMapElement k: elements){
                            if(k instanceof Animal && ((Animal) k).getGenome().equals(engine.getMap().stats.mostPopularGenome())){
                                myGridPane.add(new Circle(Math.min(boxSizey / 2, boxSizex / 2), Color.BLUE), i, j);
                                added = true;
                                break;
                            }
                        }
                        }

                        if(!added){
                            myGridPane.add(new Circle(Math.min(boxSizey / 2, boxSizex / 2), Color.RED), i, j);
                        }

                    }
                }


            }
        }
        myGridPane.setGridLinesVisible(true);



    }

public void setData(Config c){
        this.config = c;

    boxSizex = prefSizex / config.mapWidth();
    boxSizey = prefSizey / config.mapHeight();

    for (int i = 0; i < config.mapHeight(); i++) {
        myGridPane.addRow(i);
        myGridPane.getRowConstraints().add(new RowConstraints((int) (prefSizex / config.mapHeight())));
    }
    for (int j = 0; j < config.mapWidth(); j++) {
        myGridPane.addColumn(j);
        myGridPane.getColumnConstraints().add(new ColumnConstraints((int) (prefSizey / config.mapWidth())));
    }
    myGridPane.setAlignment(Pos.CENTER);


    GridPane center = myGridPane;
    center.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    bPane.setCenter(center);
}

}
