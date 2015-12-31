package app.esiea.eatthat.Util;

import android.content.Context;

public class Database {

    private static ConcreteDatabase db = null;

    public Database(){}

    public static void Init(Context context){
        if(db != null){
            return;
        }
        db = new ConcreteDatabase(context);
    }

    public static ConcreteDatabase getInstance(){
        return db;
    }
}
