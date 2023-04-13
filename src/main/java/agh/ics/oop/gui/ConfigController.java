package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Platform;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import java.util.ResourceBundle;

public class ConfigController implements Initializable, IPositionChangeObserver {


    public TextField mapHeightTextField;
    public TextField mapWidthTextField;
    public TextField initialGrassCountTextField;
    public TextField dailyGrassTextField;
    public TextField percentageTextField;
    public TextField chanceTextField;
    public TextField happyAnimalEnergyTextField;
    public TextField energyLossTextField;
    public TextField genomeLengthTextField;
    // These are not used, but there were some warnings because of that
    @FXML
    ToggleGroup mapType;
    @FXML
    ToggleGroup plantType;
    @FXML
    ToggleGroup behaviour;
    @FXML
    ToggleGroup mutation;


    @FXML
    private GridPane myGridPane;
    @FXML
    private VBox wraper;
    @FXML
    private ChoiceBox<String> myChoiceBox;
    @FXML
    private Slider mapWidthSlider;
    @FXML
    private Slider mapHeightSlider;
    @FXML
    private RadioButton globe;
    @FXML
    private RadioButton portal;
    @FXML
    private CheckBox saveToFile;
    @FXML
    private Slider initGrassSlider;
    @FXML
    private Slider dailyGrassSlider;
    @FXML
    private Slider percentageGrassSlider;
    @FXML
    private Slider betterGrassSlider;
    @FXML
    private Spinner<Integer> grassEnergySpinner;
    @FXML
    private RadioButton equator;
    @FXML
    private RadioButton graveyard;
    @FXML
    private Spinner<Integer> initAnimalSpinner;
    @FXML
    private Spinner<Integer> animalEnergySpinner;
    @FXML
    private Slider happyAnimalSlider;
    @FXML
    private Slider energyLossSlider;
    @FXML
    private RadioButton randomBehaviour;
    @FXML
    private RadioButton notRandomBehaviour;
    @FXML
    private Slider genomeSlider;
    @FXML
    private Spinner<Integer> maxMutSpinner;
    @FXML
    private Spinner<Integer> minMutSpinner;
    @FXML
    private RadioButton randomMutation;
    @FXML
    private RadioButton notRandomMutation;
    @FXML
    private Button startButton;


    private final String[] names = {"RandomPortal", "NotRandomGlobe"};
    private Config c;
    private SimulationEngine e;

    private final ArrayList<SimulationEngine> engines = new ArrayList<>();
    private final ArrayList<SimulationController> controllers = new ArrayList<>();
    private SpinnerValueFactory<Integer> valueFactory;
    private SpinnerValueFactory<Integer> animalValueFactory;
    private SpinnerValueFactory<Integer> animalEnergyFactory;
    private SpinnerValueFactory<Integer> maxMutationFactory;
    private SpinnerValueFactory<Integer> minMutationFactory;


    private int mapHeightCfg;
    private int mapWidthCfg;
    private int initialGrassCountCfg;
    private int mapTypeCfg;

    private int plantEnergyCfg;
    private int dailyPlantGrowthCountCfg;
    private int plantGrowthTypeCfg;
    private int preferredPercentageCfg;
    private int preferredChanceCfg;

    private int initialAnimalCountCfg;
    private int initialAnimalEnergyCfg;
    private int readyToBreedEnergyCfg;
    private int energyLossCfg;
    private int genomeLengthCfg;

    private int minMutationsCfg;
    private int maxMutationsCfg;
    private int mutatorTypeCfg;

    private int behaviourTypeCfg;
    private int saveToFileCfg;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        // ############################################ Choose configuration file ##################################
        myChoiceBox.getItems().addAll(names);
        myChoiceBox.setValue(names[0]);
        myChoiceBox.setOnAction(this::getConfig);

        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000);
        grassEnergySpinner.setValueFactory(valueFactory);


        animalValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 100);
        initAnimalSpinner.setValueFactory(animalValueFactory);

        animalEnergyFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000);
        animalEnergySpinner.setValueFactory(animalEnergyFactory);


        maxMutationFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000);
        maxMutSpinner.setValueFactory(maxMutationFactory);

        minMutationFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000);
        minMutSpinner.setValueFactory(minMutationFactory);


        mapWidthSlider.setMax(50.0);
        mapWidthSlider.setMin(2.0);
        mapHeightSlider.setMax(50.0);
        mapHeightSlider.setMin(2.0);

        genomeSlider.setMax(1000.0);
        energyLossSlider.setMax(1000.0);
        initGrassSlider.setMax(100.0);
        happyAnimalSlider.setMax(1000.0);
        dailyGrassSlider.setMax(50.0);
        dailyGrassSlider.setMin(30.0);
        percentageGrassSlider.setMax(99.0);
        betterGrassSlider.setMax(99.0);


        // init sliders

        mapHeightSlider.valueProperty().addListener((observable, oldValue, newValue) -> {mapHeightCfg = (int) mapHeightSlider.getValue(); displayConfig();});


        mapWidthSlider.valueProperty().addListener((observable, oldValue, newValue) -> {mapWidthCfg = (int) mapWidthSlider.getValue(); displayConfig();});

        initGrassSlider.valueProperty().addListener((observable, oldValue, newValue) -> {initialGrassCountCfg = (int) initGrassSlider.getValue(); displayConfig();});

        dailyGrassSlider.valueProperty().addListener((observable, oldValue, newValue) -> {dailyPlantGrowthCountCfg = (int) dailyGrassSlider.getValue(); displayConfig();});

        percentageGrassSlider.valueProperty().addListener((observable, oldValue, newValue) -> {preferredPercentageCfg = (int) percentageGrassSlider.getValue(); displayConfig();});

        betterGrassSlider.valueProperty().addListener((observable, oldValue, newValue) -> {preferredChanceCfg = (int) betterGrassSlider.getValue();displayConfig();});

        happyAnimalSlider.valueProperty().addListener((observable, oldValue, newValue) -> {readyToBreedEnergyCfg = (int) happyAnimalSlider.getValue(); displayConfig();});

        energyLossSlider.valueProperty().addListener((observable, oldValue, newValue) -> {energyLossCfg = (int) energyLossSlider.getValue(); displayConfig();});

        genomeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {genomeLengthCfg = (int) genomeSlider.getValue(); displayConfig();});


        // Spinnery
        grassEnergySpinner.valueProperty().addListener((observable, oldValue, newValue) -> plantEnergyCfg = grassEnergySpinner.getValue());

        initAnimalSpinner.valueProperty().addListener((observable, oldValue, newValue) -> initialAnimalCountCfg = initAnimalSpinner.getValue());

        animalEnergySpinner.valueProperty().addListener((observable, oldValue, newValue) -> initialAnimalEnergyCfg = animalEnergySpinner.getValue());

        maxMutSpinner.valueProperty().addListener((observable, oldValue, newValue) -> maxMutationsCfg = maxMutSpinner.getValue());

        minMutSpinner.valueProperty().addListener((observable, oldValue, newValue) -> minMutationsCfg = minMutSpinner.getValue());


        this.mapWidthTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                String tmp = mapWidthTextField.getText();
                if (tmp.length() > 0) {
                    mapWidthCfg = Integer.parseInt(tmp);
                    displayConfig();
                }


            }
        });

        this.mapHeightTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                String tmp = mapHeightTextField.getText();
                if (tmp.length() > 0) {
                    mapHeightCfg = Integer.parseInt(tmp);
                    displayConfig();
                }

            }
        });

        this.chanceTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String tmp = chanceTextField.getText();
                if (tmp.length() > 0) {
                    preferredChanceCfg = Integer.parseInt(tmp);
                    displayConfig();
                }
            }

        });

        this.dailyGrassTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String tmp = dailyGrassTextField.getText();
                if (tmp.length() > 0) {
                    dailyPlantGrowthCountCfg = Integer.parseInt(tmp);
                    displayConfig();
                }

            }
        });

        this.energyLossTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String tmp = energyLossTextField.getText();
                if (tmp.length() > 0) {
                    energyLossCfg = Integer.parseInt(tmp);
                    displayConfig();
                }

            }
        });

        this.genomeLengthTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String tmp = genomeLengthTextField.getText();
                if (tmp.length() > 0) {
                    genomeLengthCfg = Integer.parseInt(tmp);
                    displayConfig();
                }

            }
        });

        this.happyAnimalEnergyTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                String tmp = happyAnimalEnergyTextField.getText();
                if (tmp.length() > 0) {
                    readyToBreedEnergyCfg = Integer.parseInt(tmp);
                    displayConfig();
                }

            }
        });


        this.initialGrassCountTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String tmp = initialGrassCountTextField.getText();
                if (tmp.length() > 0) {
                    initialGrassCountCfg = Integer.parseInt(tmp);
                    displayConfig();
                }

            }
        });

        this.percentageTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String tmp = percentageTextField.getText();
                if (tmp.length() > 0) {
                    preferredPercentageCfg = Integer.parseInt(tmp);
                    displayConfig();
                }


            }
        });

        this.readConfig();

    }


    // Button that starts new simulation
    public void startSimulation() {
        Parent root;

        c = new Config(mapHeightCfg,
                mapWidthCfg,
                initialGrassCountCfg,
                mapTypeCfg,
                plantEnergyCfg,
                dailyPlantGrowthCountCfg,
                plantGrowthTypeCfg,
                preferredPercentageCfg,
                preferredChanceCfg,


                initialAnimalCountCfg,
                initialAnimalEnergyCfg,
                readyToBreedEnergyCfg,
                energyLossCfg,
                genomeLengthCfg,

                minMutationsCfg,
                maxMutationsCfg,
                mutatorTypeCfg,

                behaviourTypeCfg,
                saveToFileCfg);

        observerhelper(c);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("simulation.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Nowa symulacja");
            stage.setScene(new Scene(root, 800, 800));


            SimulationController simulationController = loader.getController();
            simulationController.setData(c);


            Thread simulation = new Thread(e);
            simulationController.setEngine(e, simulation);


            stage.setOnCloseRequest(event1 -> simulation.stop());
            simulation.start();


            // Draws the initial map
            simulationController.draw(c, e);
            controllers.add(simulationController);
            engines.add(e);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void readConfig() {

        String fpath = "src/main/presetConfigs/" + myChoiceBox.getValue() + ".txt";

        Config tmp = CsvParser.getConfigFromFile(fpath);
        mapHeightCfg = tmp.mapHeight();
        mapWidthCfg = tmp.mapWidth();
        initialGrassCountCfg = tmp.initialGrassCount();
        mapTypeCfg = tmp.mapType();
        plantEnergyCfg = tmp.plantEnergy();
        dailyPlantGrowthCountCfg = tmp.dailyPlantGrowthCount();
        plantGrowthTypeCfg = tmp.plantGrowthType();
        preferredPercentageCfg = tmp.preferredPercentage();
        preferredChanceCfg = tmp.preferredChance();


        initialAnimalCountCfg = tmp.initialAnimalCount();
        initialAnimalEnergyCfg = tmp.initialAnimalEnergy();
        readyToBreedEnergyCfg = tmp.readyToBreedEnergy();
        energyLossCfg = tmp.energyLoss();
        genomeLengthCfg = tmp.genomeLength();

        minMutationsCfg = tmp.minMutations();
        maxMutationsCfg = tmp.maxMutations();
        mutatorTypeCfg = tmp.mutatorType();

        behaviourTypeCfg = tmp.behaviourType();
        saveToFileCfg = tmp.saveToFile();


        displayConfig();
    }

    public void getConfig(ActionEvent event) {
        this.readConfig();
    }

    private void observerhelper(Config c) {
        this.c = c;
        this.e = new SimulationEngine(c);
        this.e.addObserver(this);
    }


    @Override
    public void positionChanged(Animal animal, Vector2d newPosition) {


        try {

            Thread.sleep(10);

        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }

        for (int i = 0; i < controllers.size(); i++) {
            int finalI = i;
            Platform.runLater(() -> {
                SimulationController simc = controllers.get(finalI);
                SimulationEngine eng = engines.get(finalI);
                if (!simc.isPaused) {
                    simc.draw(simc.config, eng);
                }
            });

        }
    }


    private void displayConfig() {
        this.mapHeightSlider.adjustValue(this.mapHeightCfg);
        this.mapWidthSlider.adjustValue(this.mapWidthCfg);
        if (mapTypeCfg == 0) {
            this.globe.setSelected(true);
        } else {
            this.portal.setSelected(true);
        }
        this.saveToFile.setSelected(saveToFileCfg == 1);
        this.initGrassSlider.adjustValue(initialGrassCountCfg);
        this.dailyGrassSlider.adjustValue(dailyPlantGrowthCountCfg);
        this.percentageGrassSlider.adjustValue(preferredPercentageCfg);
        this.betterGrassSlider.adjustValue(preferredChanceCfg);
        if (plantGrowthTypeCfg == 0) {
            this.equator.setSelected(true);
        } else {
            this.graveyard.setSelected(true);
        }
        valueFactory.setValue(plantEnergyCfg);
        animalValueFactory.setValue(initialAnimalCountCfg);
        animalEnergyFactory.setValue(initialAnimalEnergyCfg);
        happyAnimalSlider.adjustValue(readyToBreedEnergyCfg);
        energyLossSlider.adjustValue(energyLossCfg);
        if (behaviourTypeCfg == 1) {
            randomBehaviour.setSelected(true);
        } else {
            notRandomBehaviour.setSelected(true);
        }
        genomeSlider.adjustValue(genomeLengthCfg);
        maxMutationFactory.setValue(maxMutationsCfg);
        minMutationFactory.setValue(minMutationsCfg);
        if (mutatorTypeCfg == 0) {
            notRandomMutation.setSelected(true);
        } else {
            randomMutation.setSelected(true);
        }


        this.percentageTextField.setText(Integer.toString(preferredPercentageCfg));
        this.initialGrassCountTextField.setText(Integer.toString(initialGrassCountCfg));
        this.happyAnimalEnergyTextField.setText(Integer.toString(readyToBreedEnergyCfg));
        this.genomeLengthTextField.setText(Integer.toString(genomeLengthCfg));
        this.energyLossTextField.setText(Integer.toString(energyLossCfg));
        this.dailyGrassTextField.setText(Integer.toString(dailyPlantGrowthCountCfg));
        this.chanceTextField.setText(Integer.toString(preferredChanceCfg));
        this.mapHeightTextField.setText(Integer.toString(mapHeightCfg));
        this.mapWidthTextField.setText(Integer.toString(mapWidthCfg));

    }


    // GUI methods


    public void getMapType() {
        if (globe.isSelected()) {
            mapTypeCfg = 0;
        } else {
            mapTypeCfg = 1;
        }
    }

    public void getStatsSave() {
        if (saveToFile.isSelected()) {
            saveToFileCfg = 1;
        } else {
            saveToFileCfg = 0;
        }
    }

    public void getPlantType() {
        if (equator.isSelected()) {
            System.out.println("equator");
            plantGrowthTypeCfg = 0;
        } else {
            plantGrowthTypeCfg = 1;
        }
    }

    public void getAnimalBehaviour() {
        if (randomBehaviour.isSelected()) {
            behaviourTypeCfg = 1;
        } else {
            behaviourTypeCfg = 0;
        }
    }

    public void getMutationType() {
        if (randomMutation.isSelected()) {
            mutatorTypeCfg = 1;
        } else {
            mutatorTypeCfg = 0;
        }
    }
}
