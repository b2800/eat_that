package app.esiea.eatthat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import app.esiea.eatthat.R;
import app.esiea.eatthat.Util.Database;
import app.esiea.eatthat.Util.Misc;

public class LanguageSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Misc.UpdateLanguage(this);
        setContentView(R.layout.activity_language_selection);
        Database.Init(this);
    }

    public void onButtonClickEnglish(View v){
        Misc.SetCurrentLocale("en_US");
        onButtonClickBack(v);
    }

    public void onButtonClickFrench(View v){
        Misc.SetCurrentLocale("fr_FR");
        onButtonClickBack(v);
    }

    public void onButtonClickBack(View v){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
