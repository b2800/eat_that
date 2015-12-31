package app.esiea.eatthat.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import app.esiea.eatthat.Model.Ingredient;
import app.esiea.eatthat.R;
import app.esiea.eatthat.Util.Database;
import app.esiea.eatthat.Util.EntityManager;
import app.esiea.eatthat.Util.Misc;

public class GenerateRecipeActivity extends AppCompatActivity {

    private RecipeElementAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Misc.UpdateLanguage(this);
        setContentView(R.layout.activity_generate_recipe);
        Database.Init(this);
        adapter = new RecipeElementAdapter();
        adapter.setContext(this);

        RecyclerView recycler_view = (RecyclerView)findViewById(R.id.elements_list);
        recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(adapter);

        for(int i = 0; i<3; i++){
            adapter.AddNewElement();
        }
    }

    public void onButtonClickAdd(View v){
        adapter.AddNewElement();
    }

    public void onButtonClickBack(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onButtonClickRandomize(View v){
        adapter.Randomize();
    }

    private class RecipeElementAdapter extends RecyclerView.Adapter<RecipeElementAdapter.RecipeElementHolder>{
        int item_count;
        Context context;

        public RecipeElementAdapter(){

        }

        public void Randomize(){
            int count = item_count;

            for(int i = 0; i < count; i++){
                DeleteElement(0);
            }
            for(int i = 0; i < count; i++){
                AddNewElement();
            }
        }

        public void AddNewElement(){
            try{
                item_count++;
                notifyItemInserted(item_count-1);
                notifyItemRangeChanged(item_count-1, item_count);
            }catch(Exception e){
                Log.e("INSERTED", e.getMessage());
            }
        }

        public void setContext(Context c){
            context = c;
        }

        public Context getContext(){
            return context;
        }

        @Override
        public RecipeElementHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = LayoutInflater.from(parent.getContext());
            View view = li.inflate(R.layout.adapter_recipe_element, parent, false);
            return new RecipeElementHolder(view, this);
        }

        @Override
        public void onBindViewHolder(RecipeElementHolder holder, int position) {
            holder.setCategory(position);
            holder.UpdateBackgroundColor(position);
        }

        @Override
        public int getItemCount() {
            return item_count;
        }

        public void DeleteElement(int position){
            try{
                item_count--;
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, item_count);
            }catch(Exception e){
                Log.e("DELETE", e.getMessage());
            }
        }

        public class RecipeElementHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            TextView ingredient_name;
            Spinner spinner;
            ImageButton randomize;
            ImageButton delete;

            RecipeElementAdapter adapter;

            public RecipeElementHolder(View itemView, RecipeElementAdapter adapter) {
                super(itemView);
                this.adapter = adapter;
                ingredient_name = (TextView)itemView.findViewById(R.id.ingredient_name);
                randomize = (ImageButton)itemView.findViewById(R.id.btn_random);
                delete = (ImageButton)itemView.findViewById(R.id.btn_delete);
                spinner = (Spinner)itemView.findViewById(R.id.spinner);

                // Spinner stuff
                ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(adapter.getContext(),
                        R.array.categories_array, android.R.layout.simple_spinner_item);
                spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinner_adapter);
                // --

                randomize.setOnClickListener(this);
                delete.setOnClickListener(this);
                itemView.setOnClickListener(this);
            }

            public void setCategory(int position){
                int count = spinner.getAdapter().getCount();

                if(position >= count){
                    position = count - 1;
                }
                spinner.setSelection(position);
                UpdateElements();
            }

            protected void UpdateElements(){
                String current_category = spinner.getSelectedItem().toString().toLowerCase();
                ArrayList<Ingredient> ingredients = Database.getInstance().GetIngredientsForCategory(current_category);

                if (ingredients.isEmpty()) {
                    ingredient_name.setText("No " + current_category + " found");
                    Toast.makeText(adapter.getContext(), R.string.warning_create_some_ingredients, Toast.LENGTH_LONG).show();

                } else {
                    int id = (int) (Math.random() * ingredients.size());
                    ingredient_name.setText(ingredients.get(id).getName());
                }
            }

            public void UpdateBackgroundColor(int position){
                if(position%2 != 0){
                    LinearLayout layout = (LinearLayout)itemView.findViewById(R.id.holder_layout);
                    layout.setBackgroundColor(Color.argb(192,255,255,255));
                }
            }

            @Override
            public void onClick(View v) {
                if(v.getId() == randomize.getId()){
                    UpdateElements();
                }else if(v.getId() == delete.getId()){
                    OnButtonClickDelete(v);
                }
            }


            public void OnButtonClickDelete(View v){
                adapter.DeleteElement(getAdapterPosition());
            }
        }
    }
}
