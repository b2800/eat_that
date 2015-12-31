package app.esiea.eatthat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import app.esiea.eatthat.R;
import app.esiea.eatthat.Util.Misc;

public class NotImplementedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Misc.UpdateLanguage(this);
        setContentView(R.layout.activity_not_implemented_yet);
    }

    public void onButtonClickBack(View v){
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }
}
