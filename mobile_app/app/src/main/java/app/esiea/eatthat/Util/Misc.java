package app.esiea.eatthat.Util;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.util.Locale;

import app.esiea.eatthat.R;

public class Misc {

    private static String locale = "en_US";

    public static String getIDName(View view) throws Exception {

        Integer id = view.getId();
        Field[] ids = R.id.class.getFields();
        for (int i = 0; i < ids.length; i++) {
            Object val = ids[i].get(null);
            if (val != null && val instanceof Integer
                    && ((Integer) val).intValue() == id.intValue()) {
                return ids[i].getName();
            }
        }
        return "";
    }

    public static void UpdateLanguage(Context context){
        Configuration cfg = new Configuration();
        cfg.locale = new Locale(locale);
        context.getResources().updateConfiguration(cfg, null);
    }

    public static void SetCurrentLocale(String _locale){
        locale = _locale;
    }
}
