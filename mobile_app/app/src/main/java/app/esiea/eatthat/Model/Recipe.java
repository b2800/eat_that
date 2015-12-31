package app.esiea.eatthat.Model;

import android.util.Pair;

import java.util.ArrayList;

public class Recipe {
    private int id;

    private ArrayList<Pair<Ingredient, PreparationType>> elements;

    public Recipe(){

    }

    public ArrayList<Pair<Ingredient, PreparationType>> getElements() {
        return elements;
    }

    public void setElements(ArrayList<Pair<Ingredient, PreparationType>> elements) {
        this.elements = elements;
    }

    public void addElement(Ingredient i, PreparationType p){
        this.elements.add(new Pair<Ingredient, PreparationType>(i,p));
    }
}
