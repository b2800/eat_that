package app.esiea.eatthat.Model;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;


/* Describe in a generic way how an ingredient should be prepared */
/* Should have had a recycler view and a creation form similar to ingredient but was never
 * implemented due to a bad time management.
 */

public class PreparationType {

    private int id;
    private String name;
    private ArrayList<String> steps;
    private int time;    // In minutes
    private int difficulty;
    private String json;
    private boolean changed;

    public PreparationType(){
        name = "";
        time = 0;
        difficulty = 0;
        json = "";
        changed = false;
    }

    public PreparationType(String json) throws JSONException {
        this.DefineFromJSON(json);
    }

    public void DefineFromJSON(String json) throws JSONException {
        JSONObject object = new JSONObject(json);

        String name = (String)object.get("name");
        int time = (int)object.get("time");
        int difficulty = (int)object.get("difficulty");
        String steps[] = (String[])object.get("steps");

        setName(name);
        setSteps(steps);
        setTime(time);
        setDifficulty(difficulty);
    }

    public String toJSON(){
        if(this.changed){
            RegenerateJSON();
        }
        return this.json;
    }

    private void RegenerateJSON(){
        JSONObject object = new JSONObject();
        String[] steps_ = this.steps.toArray(new String[this.steps.size()]);

        try {
            object.put("name", name);
            object.put("time", time);
            object.put("difficulty", difficulty);
            object.put("steps", steps_);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.json = object.toString();
            this.changed = false;
        }
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.changed = true;
    }

    public ArrayList<String> getSteps(){
        return this.steps;
    }

    public void setSteps(String[] steps){
        this.steps = new ArrayList<String>(Arrays.asList(steps));
    }

    public void addStep(String single_step){
        this.steps.add(single_step);
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
        this.changed = true;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
        this.changed = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
