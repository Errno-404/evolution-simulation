package agh.ics.oop.gui;

import agh.ics.oop.*;
import agh.ics.oop.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class GuiElementBox {
    VBox vbox = new VBox();
    Label label;

    public GuiElementBox(IMapElement el){
        try{
            Image img = new Image(new FileInputStream(el.getImage()));
            ImageView imgView = new ImageView(img);

            imgView.setFitHeight(20);
            imgView.setFitWidth(20);

            if (el instanceof Animal){
                this.label = new Label(el.toString());
            }
            else if(el instanceof Grass){
                this.label = new Label("Grass");
            }

            this.vbox.getChildren().addAll(imgView, label);
            this.vbox.setAlignment(Pos.CENTER);
        }
        catch (FileNotFoundException exc){
            System.out.println(exc);
        }

    }





}
