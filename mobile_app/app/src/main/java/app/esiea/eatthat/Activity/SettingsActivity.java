package app.esiea.eatthat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import app.esiea.eatthat.R;
import app.esiea.eatthat.Util.Database;
import app.esiea.eatthat.Util.Misc;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Misc.UpdateLanguage(this);
        setContentView(R.layout.activity_settings);
        Database.Init(this);
    }

    public void onButtonClickBack(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onButtonClickHistory(View v){
        Intent intent = new Intent(this, NotImplementedActivity.class);
        startActivity(intent);
    }

    public void onButtonClickManageIngredient(View v){
        Intent intent = new Intent(this, ManageIngredientsActivity.class);
        startActivity(intent);
    }

    public void onButtonClickManageStock(View v){
        Intent intent = new Intent(this, NotImplementedActivity.class);
        startActivity(intent);
    }

    public void onButtonClickSelectLanguage(View v){
        Intent intent = new Intent(this, LanguageSelectionActivity.class);
        startActivity(intent);
    }
}
