package app.esiea.eatthat.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import app.esiea.eatthat.Model.Ingredient;
import app.esiea.eatthat.R;
import app.esiea.eatthat.Util.Database;
import app.esiea.eatthat.Util.EntityManager;
import app.esiea.eatthat.Util.Misc;

public class ManageIngredientsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Misc.UpdateLanguage(this);
        setContentView(R.layout.activity_manage_ingredients);

        RecyclerView recycler_view = (RecyclerView)findViewById(R.id.ingredient_list);
        recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recycler_view.setAdapter(new IngredientsAdapter(EntityManager.GetAllIngredients()));
    }

    public void onButtonClickCreateIngredient(View v){
        Intent i = new Intent(this, CreateIngredientActivity.class);
        startActivity(i);
    }

    public void onButtonClickBack(View v){
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    public void onButtonClickSync(View v){
        new AlertDialog.Builder(this)
                .setTitle(R.string.sync_dialog_title)
                .setMessage(R.string.sync_dialog_text)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do some async task ninja stuff
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientHolder>{
        JSONArray ingredients;

        public IngredientsAdapter(JSONArray json){
            this.ingredients = json;
        }

        @Override
        public IngredientsAdapter.IngredientHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = LayoutInflater.from(parent.getContext());
            View view = li.inflate(R.layout.adapter_ingredient, parent, false);
            return new IngredientHolder(view, this);
        }

        @Override
        public void onBindViewHolder(IngredientsAdapter.IngredientHolder holder, int position) {
            try{
                JSONObject i = ingredients.getJSONObject(position);
                holder.setName(i.getString("name"));
                holder.UpdateBackgroundColor(position);

            }catch(Exception e){
                Log.e("DEBUG", "Error occured when extracting json object from ingredients");
                Log.e("MSG", e.getMessage());
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return ingredients.length();
        }

        public void DeleteElement(int position){
            try{
                JSONObject i = ingredients.getJSONObject(position);
                Database.getInstance().DeleteIngredientById(i.getInt("id"));
                ingredients.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, ingredients.length());
            }catch(Exception e){
                Log.e("DELETE", e.getMessage());
            }
        }

        public class IngredientHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            TextView ingredient_name;
            ImageButton edit;
            ImageButton delete;

            IngredientsAdapter adapter;

            public IngredientHolder(View itemView, IngredientsAdapter adapter) {
                super(itemView);
                ingredient_name = (TextView)itemView.findViewById(R.id.ingredient_name);
                edit = (ImageButton)itemView.findViewById(R.id.btn_edit);
                delete = (ImageButton)itemView.findViewById(R.id.btn_delete);
                this.adapter = adapter;

                edit.setOnClickListener(this);
                delete.setOnClickListener(this);
                itemView.setOnClickListener(this);


            }

            public void setName(String name){
                ingredient_name.setText(name);
            }

            public void UpdateBackgroundColor(int position){
                if(position%2 != 0){
                    LinearLayout layout = (LinearLayout)itemView.findViewById(R.id.holder_layout);
                    layout.setBackgroundColor(Color.argb(192,255,255,255));
                }
            }
            @Override
            public void onClick(View v) {
                if(v.getId() == edit.getId()){
                    OnButtonClickEdit(v);
                }else if(v.getId() == delete.getId()){
                    OnButtonClickDelete(v);
                }
            }

            public void OnButtonClickEdit(View v){

            }

            public void OnButtonClickDelete(View v){
                adapter.DeleteElement(getAdapterPosition());
            }
        }
    }
}
