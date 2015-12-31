package app.esiea.eatthat.Model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Category {

    private int id;
    private String name;
    private String icon_path;

    public Category(){
        name = "";
    }

    public Category(String name){
        this.setName(name);
    }

    public JSONObject toJSON() {
        JSONObject json = null;
        try{
            json = new JSONObject();
            json.put("id", this.id);
            json.put("name", this.name);
            json.put("icon", this.icon_path);
        } catch(Exception e){
            Log.e("JSON ERROR", e.getMessage());
        } finally {
            return json;
        }
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIconPath() {
        return icon_path;
    }

    public void setIconPath(String icon_path) {
        this.icon_path = icon_path;
    }
}
