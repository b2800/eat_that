class CreateRecipes < ActiveRecord::Migration
  def change
    create_table :recipes do |t|
      t.text :name
      t.text :description

      t.timestamps
    end
  end
end
