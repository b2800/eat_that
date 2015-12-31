package app.esiea.eatthat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import app.esiea.eatthat.R;
import app.esiea.eatthat.Util.Database;
import app.esiea.eatthat.Util.EntityManager;
import app.esiea.eatthat.Util.Misc;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Misc.UpdateLanguage(this);
        setContentView(R.layout.activity_main);
        Database.Init(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    public void onButtonClickGenerateRecipe(View v){
        Intent intent = new Intent(this, GenerateRecipeActivity.class);
        startActivity(intent);
    }

    public void onButtonClickSettings(View v){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
