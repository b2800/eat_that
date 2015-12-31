package app.esiea.eatthat.Activity;

import android.content.Context;
import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import app.esiea.eatthat.Model.Category;
import app.esiea.eatthat.Model.Ingredient;
import app.esiea.eatthat.R;
import app.esiea.eatthat.Util.Database;
import app.esiea.eatthat.Util.Misc;

import static app.esiea.eatthat.Util.Misc.getIDName;

public class CreateIngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Misc.UpdateLanguage(this);
        setContentView(R.layout.activity_create_ingredient);
        Database.Init(this);
    }

    // Code that hides the keyboard when we click outside the EditText zone
    public void onClick(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void onButtonClickBack(View v){
        Intent intent = new Intent(this, ManageIngredientsActivity.class);
        startActivity(intent);
    }

    public void onButtonClickCreate(View v){
        EditText name_field = (EditText) findViewById(R.id.ingredient_name);

        Ingredient i = new Ingredient();
        String name = name_field.getText().toString();
        if(name.isEmpty()){
            Toast.makeText(this, R.string.error_no_name_entered, Toast.LENGTH_LONG).show();
            return;
        }
        i.setName(name);

        // /!\ This will change later when it will be possible to create your own categories
        // Display will have to be dynamic, thus no more hard coded categories here.
        // But for the sake of simplicity and to complete the app before the deadline, it wont be done
        // anytime soon

        ArrayList<CheckBox> checkboxes = new ArrayList<CheckBox>();

        CheckBox cat_base = (CheckBox) findViewById(R.id.cat_base);
        CheckBox cat_meat = (CheckBox) findViewById(R.id.cat_meat);
        CheckBox cat_salad = (CheckBox) findViewById(R.id.cat_salad);
        CheckBox cat_dessert = (CheckBox) findViewById(R.id.cat_dessert);

        checkboxes.add(cat_base);
        checkboxes.add(cat_meat);
        checkboxes.add(cat_salad);
        checkboxes.add(cat_dessert);

        boolean at_leat_one_checkbox_ticked = false;

        // Loop through every Checkboxes and add the relevant categories to the ingredient,
        // Category is based on id. ID is like cat_something, so we have to remove the cat_ part.
        for(CheckBox c : checkboxes){
            if( ! c.isChecked() ){
                continue;
            }
            at_leat_one_checkbox_ticked = true;

            try{
                String category_name = getIDName(c).substring(4);
                i.addCategory( Database.getInstance().GetOrCreateCategoryByName(category_name) );
            } catch (Exception e){
                Log.e("ERROR in checkboxes ", e.getMessage());
                e.printStackTrace();
            }
        }

        if(!at_leat_one_checkbox_ticked){
            Toast.makeText(this, R.string.error_no_category_selected, Toast.LENGTH_LONG).show();
            return;
        }

        Database.getInstance().InsertNewIngredient(i);

        Intent intent = new Intent(this, ManageIngredientsActivity.class);
        startActivity(intent);
    }
}
