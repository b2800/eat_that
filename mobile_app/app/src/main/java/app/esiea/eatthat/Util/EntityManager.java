package app.esiea.eatthat.Util;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;

import app.esiea.eatthat.Model.Ingredient;

public class EntityManager {

    public static JSONArray GetAllIngredients(){
        JSONArray ingredients = new JSONArray();

        ArrayList<Ingredient> all_ingredients = Database.getInstance().GetAllIngredients();

        for(Ingredient i : all_ingredients){
            ingredients.put(i.toJSON());
        }
        return ingredients;
    }
}
