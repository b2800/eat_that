package app.esiea.eatthat.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.esiea.eatthat.R;
import app.esiea.eatthat.Util.Database;
import app.esiea.eatthat.Util.Misc;

public class ManageStockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Misc.UpdateLanguage(this);
        setContentView(R.layout.activity_manage_stock);
        Database.Init(this);
    }
}
