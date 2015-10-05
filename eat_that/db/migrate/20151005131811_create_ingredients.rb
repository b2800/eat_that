class CreateIngredients < ActiveRecord::Migration
  def change
    create_table :ingredients do |t|
      t.text :name
      t.number :amount_in_stock
      t.text :unit

      t.timestamps
    end
  end
end
