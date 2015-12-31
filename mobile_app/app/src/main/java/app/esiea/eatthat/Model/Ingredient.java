package app.esiea.eatthat.Model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Ingredient {

    private int id;
    private String name;
    private ArrayList<Category> categories;
    private int calories;
    private String json;
    private boolean changed;


    public Ingredient(){
        name = "";
        calories = 0;
        categories = new ArrayList<Category>();
        changed = true;
        json = "";
    }

    public Ingredient(String json) throws JSONException {
        DefineFromJSON(json);
    }

    public void DefineFromJSON(String hash) throws JSONException {
        JSONObject object = new JSONObject(hash);
        String name = (String)object.get("name");
        String categories[] = (String[])object.get("categories");

        setName(name);

        for(int i = 0; i < categories.length; i++){
            Category c = new Category(categories[i]);
            this.addCategory(c);
        }
    }

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();

        try {
            json.put("id", this.id);
            json.put("name", this.name);
            json.put("calories", this.calories);

            JSONArray category_list = new JSONArray();
            for(int i = 0; i<this.categories.size(); i++){
                JSONObject c = this.categories.get(i).toJSON();
                category_list.put(c);
            }
            json.put("categories", category_list);
        } catch(JSONException e) {
            e.printStackTrace();
        } finally {
            return json;
        }
    }

    //Getter and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.changed = true;
    }

    public ArrayList<Category> getCategories(){
        return this.categories;
    }

    public void setCategories(ArrayList<Category> categories){
        this.categories = categories;
        this.changed = true;
    }

    public void addCategory(Category single_category){
        Log.e("INFO", "Adding a category");
        Log.e("CURRENT Cateroy_size", String.valueOf(categories.size()));
        this.categories.add(single_category);
        this.changed = true;
        Log.e("NEW Cateroy_size", String.valueOf(categories.size()));

    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
        this.changed = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        this.changed = true;
    }
}
