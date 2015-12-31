package app.esiea.eatthat.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import app.esiea.eatthat.Model.Category;
import app.esiea.eatthat.Model.Ingredient;
import app.esiea.eatthat.Model.PreparationType;
import app.esiea.eatthat.Model.Recipe;

// #####################

// -- Abandon All Hope, Ye Who Enter Here - (Especially if you are a teacher) --
// -- There is a lot of horrible stuff here no one wants to get into --
// Do not use this class directly, Use Database class instead

// #####################


public class ConcreteDatabase extends SQLiteOpenHelper {

    /*
     ***** Public API ****
     */

    public void InsertNewIngredient(Ingredient i){
        InsertNewIngredient(i, true);
    }
    public void InsertNewIngredient(Ingredient i, boolean close){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(INGREDIENT_NAME, i.getName());
        values.put(INGREDIENT_CALORIES, i.getCalories());

        for(Category cat : i.getCategories()){
            GetOrCreateCategoryByName(cat.getName(), false);
        }

        db.insert(INGREDIENTS_TABLE, null, values);

        Ingredient inserted_ingredient = GetIngredientByName(i.getName(), false);
        i.setId(inserted_ingredient.getId());

        for(Category cat : i.getCategories()){
            InsertIngredientCategoryAssociation(i.getId(), cat.getId(), false);
        }
        if(close)
            db.close();
    }

    private void InsertIngredientCategoryAssociation(int ingredient_id, int category_id) {
        InsertIngredientCategoryAssociation(ingredient_id, category_id, true);
    }

    private void InsertIngredientCategoryAssociation(int ingredient_id, int category_id, boolean close) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(INGREDIENT_ID, String.valueOf(ingredient_id));
        values.put(CATEGORY_ID, String.valueOf(category_id));

        db.insert(INGREDIENT_CATEGORY_JOIN_TABLE, null, values);
        if(close)
            db.close();
    }

    public Ingredient GetIngredientByName(String name){
        return GetIngredientByName(name, true);
    }
    public Ingredient GetIngredientByName(String name, boolean close){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(INGREDIENTS_TABLE,
                new String[] {  INGREDIENT_ID,
                        INGREDIENT_NAME,
                        INGREDIENT_CALORIES },
                INGREDIENT_NAME + "=?", new String[] { name },
                null, null, null, null);


        if (cursor == null){
            if(close)
                db.close();
            return null;
        }

        cursor.moveToFirst();
        Ingredient i = new Ingredient();
        i.setId(Integer.parseInt(cursor.getString(0)));
        i.setName(cursor.getString(1));
        i.setCalories(Integer.parseInt(cursor.getString(2)));

        if(close)
            db.close();

        return i;
    }

    public ArrayList<Ingredient> GetAllIngredients(){
        ArrayList<Ingredient> result = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + INGREDIENTS_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(Integer.parseInt(cursor.getString(0)));
                ingredient.setName(cursor.getString(1));
                ingredient.setCalories(Integer.parseInt(cursor.getString(2)));
                ingredient.setCategories(this.GetCategoriesForIngredient(ingredient.getId(), false));
                result.add(ingredient);
            } while (cursor.moveToNext());
        }

        return result;
    }

    public ArrayList<Ingredient> GetIngredientsForCategory(String category_name){

        Category category = GetCategoryByName(category_name);
        ArrayList<Ingredient> result = new ArrayList<>();

        if(category == null){
            return result;
        }

        String selectQuery = "SELECT * FROM " + INGREDIENTS_TABLE + " I, " + INGREDIENT_CATEGORY_JOIN_TABLE + " J "
                            + " WHERE J." + INGREDIENT_ID + " = I." + INGREDIENT_ID
                            + " AND J." + CATEGORY_ID + " = " + String.valueOf(category.getId()) + ";";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(Integer.parseInt(cursor.getString(0)));
                ingredient.setName(cursor.getString(1));
                ingredient.setCalories(Integer.parseInt(cursor.getString(2)));
                ingredient.setCategories(this.GetCategoriesForIngredient(ingredient.getId(), false));
                result.add(ingredient);
            } while (cursor.moveToNext());
        }

        return result;
    }

    public void UpdateIngredient(Ingredient i){

    }

    public void DeleteIngredientById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(STOCK_TABLE, INGREDIENT_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.delete(INGREDIENT_CATEGORY_JOIN_TABLE, INGREDIENT_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.delete(INGREDIENTS_TABLE, INGREDIENT_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void DeleteIngredient(Ingredient i){
        DeleteIngredientById(i.getId());
    }

    public void InsertNewRecipe(Recipe r){

    }

    public void DeleteRecipe(Recipe r){

    }

    public Category GetOrCreateCategoryByName(String name){
        return GetOrCreateCategoryByName(name, true);
    }

    public Category GetOrCreateCategoryByName(String name, boolean close){
        Category c = GetCategoryByName(name, close);

        if(c == null) {
            c = new Category(name);
            InsertNewCategory(c, close);
        }
        return c;
    }

    public Category GetCategoryByName(String name){
        return GetCategoryByName(name, true);
    }
    public Category GetCategoryByName(String name, boolean close){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(CATEGORY_TABLE,
                new String[]{CATEGORY_ID,
                        CATEGORY_NAME,
                        CATEGORY_IMAGE},
                CATEGORY_NAME + "=?", new String[]{name},
                null, null, null, null);

        if (cursor == null){
            if(close)
                db.close();
            return null;
        }

        Category cat = null;

        if(cursor.moveToFirst()) {
            cat = new Category();
            cat.setId(Integer.parseInt(cursor.getString(0)));
            cat.setName(cursor.getString(1));
            cat.setIconPath(cursor.getString(2));
        }
        if(close)
            db.close();
        return cat;
    }

    ArrayList<Category> GetCategoriesForIngredient(int ingredient_id){
        return GetCategoriesForIngredient(ingredient_id, true);
    }
    ArrayList<Category> GetCategoriesForIngredient(int ingredient_id, boolean close){
        ArrayList<Category> result = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(INGREDIENT_CATEGORY_JOIN_TABLE,
                new String[] {  CATEGORY_ID,
                                INGREDIENT_ID },
                INGREDIENT_ID + "=?", new String[] { String.valueOf(ingredient_id) },
                null, null, null, null);

        if (cursor == null){
            if(close)
                db.close();
            return null;
        }

        if (cursor.moveToFirst()) {
            do {
                Category category = this.GetCategoryByID(Integer.parseInt(cursor.getString(0)));
                result.add(category);
            } while (cursor.moveToNext());
        }
        if(close)
            db.close();
        return result;
    }

    public Category GetCategoryByID(int cat_id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(CATEGORY_TABLE,
                new String[] {  CATEGORY_ID,
                        CATEGORY_NAME,
                        CATEGORY_IMAGE },
                CATEGORY_ID + "=?", new String[] { String.valueOf(cat_id) },
                null, null, null, null);
        if (cursor == null){
            db.close();
            return null;
        }

        cursor.moveToFirst();
        Category cat = new Category();
        cat.setId(Integer.parseInt(cursor.getString(0)));
        cat.setName(cursor.getString(1));
        cat.setIconPath(cursor.getString(2));

        db.close();
        return cat;
    }

    public void DeleteCategory(Category c){

    }

    public void InsertNewPreparationType(PreparationType p){

    }

    public void DeletePreparationType(PreparationType p){

    }


    /*
     * Private utility methods
     */

    private void InsertNewCategory(Category c){
        InsertNewCategory(c, true);
    }
    private void InsertNewCategory(Category c, boolean close){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CATEGORY_NAME, c.getName());
        values.put(CATEGORY_IMAGE, c.getIconPath());

        db.insert(CATEGORY_TABLE, null, values);

        Category inserted_cat = GetCategoryByName(c.getName(), false);
        c.setId(inserted_cat.getId());
        if(close)
            db.close();
    }

    /*
     ***** Inner working ****
     */

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "eatthat";

    // Tables
    private String INGREDIENTS_TABLE = "ingredient";
    private String CATEGORY_TABLE = "category";
    private String INGREDIENT_CATEGORY_JOIN_TABLE = "join_ingredient_category";
    private String RECIPE_TABLE = "recipe";
    private String RECIPE_JOIN_TABLE = "recipe_join";
    private String PREPARATION_TYPE_TABLE = "preparation_type";
    private String STOCK_TABLE = "stock";

    // Ingredients
    private String INGREDIENT_ID = "ig_id";
    private String INGREDIENT_NAME = "ig_name";
    private String INGREDIENT_CALORIES  = "ig_calories";

    // Categories
    private String CATEGORY_ID = "cat_id";
    private String CATEGORY_NAME = "cat_name";
    private String CATEGORY_IMAGE = "cat_image";

    // Recipes
    private String RECIPE_ID = "rc_id";

    // Recipes Association
    private String INGREDIENT_QUANTITY = "ig_qty";

    // Preparation type
    private String PREPARATION_TYPE_ID = "pt_id";
    private String PREPARATION_TYPE_NAME = "pt_name";
    private String PREPARATION_TYPE_STEPS = "pt_steps";
    private String PREPARATION_TYPE_TIME = "pt_times";
    private String PREPARATION_TYPE_DIFFICULTY = "pt_difficulty";

    public ConcreteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " + INGREDIENTS_TABLE + "(" +
                INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                INGREDIENT_NAME + " VARCHAR(100), " +
                INGREDIENT_CALORIES + " INTEGER );");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + CATEGORY_TABLE + "(" +
                CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                CATEGORY_NAME + " VARCHAR(100), " +
                CATEGORY_IMAGE + " VARCHAR(100));" );

        db.execSQL("CREATE TABLE IF NOT EXISTS " + INGREDIENT_CATEGORY_JOIN_TABLE + "(" +
                INGREDIENT_ID + " INTEGER, " +
                CATEGORY_ID + " INTEGER, " +
                "PRIMARY KEY(" + INGREDIENT_ID + ", " + CATEGORY_ID + "));");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + PREPARATION_TYPE_TABLE + "(" +
                PREPARATION_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                PREPARATION_TYPE_NAME + " VARCHAR(100), " +
                PREPARATION_TYPE_STEPS + " VARCHAR(500), " +
                PREPARATION_TYPE_TIME + " INTEGER, " +
                PREPARATION_TYPE_DIFFICULTY + " INTEGER);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + RECIPE_TABLE + "(" +
                RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                INGREDIENT_ID + " INTEGER, " +
                PREPARATION_TYPE_ID + " INTEGER, " +
                INGREDIENT_QUANTITY + " INTEGER);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + STOCK_TABLE + "(" +
                INGREDIENT_ID + " INTEGER PRIMARY KEY, " +
                INGREDIENT_QUANTITY + " INTEGER);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + RECIPE_JOIN_TABLE + "(" +
                RECIPE_ID + " INTEGER, " +
                INGREDIENT_ID + " INTEGER, " +
                PREPARATION_TYPE_ID + " INTEGER, " +
                INGREDIENT_QUANTITY + " INTEGER, " +
                "PRIMARY KEY(" + RECIPE_ID + ", " + INGREDIENT_ID + ", " + PREPARATION_TYPE_ID + "));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("DB", "Upgrading database. Existing contents will be lost. ["
                + oldVersion + "] -> [" + newVersion + "]");

        db.execSQL("DROP TABLE IF EXISTS " + STOCK_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + INGREDIENT_CATEGORY_JOIN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + INGREDIENTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PREPARATION_TYPE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RECIPE_TABLE);

        // Create after dropping
        onCreate(db);
    }
}
