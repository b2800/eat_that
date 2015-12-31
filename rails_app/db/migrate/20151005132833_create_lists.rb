class CreateLists < ActiveRecord::Migration
  def change
    create_join_table :ingredients, :recipes do |t|
      t.index :recipe_id
      t.index :ingredient_id
      t.integer :quantity
      t.text :unit

      t.timestamps
    end
  end
end
