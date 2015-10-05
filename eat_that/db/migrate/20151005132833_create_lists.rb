class CreateLists < ActiveRecord::Migration
  def change
    create_table :lists do |t|
      t.integer :id_recipe
      t.integer :id_ingredient
      t.integer :quantity
      t.text :unit

      t.timestamps
    end
  end
end
